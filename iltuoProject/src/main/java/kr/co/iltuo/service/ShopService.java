package kr.co.iltuo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.iltuo.dto.ShopDTO;
import kr.co.iltuo.mapper.ShopMapper;
import kr.co.iltuo.util.PageMaker;
import kr.co.iltuo.util.ShopCriteria;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShopService {
	@Autowired
	ShopMapper shopMapper;

	private final String encoding = "UTF-8";

	public void getMainCategoryList(Model model) {
		try {
			List<ShopDTO> shop = shopMapper.getMainCategoryList();
			model.addAttribute("shop", shop);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public String productList(int mainCategoryID, ShopCriteria criteria, Model model) {
		String view = null;
		final String SURL = "shop/productList";
		final String FURL = "redirect:/";
		try {
			criteria.setMainCategoryID(mainCategoryID);
			ShopDTO mainCategory = shopMapper.getMainCategoryID(mainCategoryID);
			List<ShopCriteria> subCategory = shopMapper.getSubCategoryList(mainCategoryID);
			List<ShopDTO> productList = shopMapper.productList(criteria);
			int total = shopMapper.getTotalCountProduct(criteria);
			PageMaker pageMaker = new PageMaker(criteria, total);
			model.addAttribute("mainCategory", mainCategory);
			model.addAttribute("subCategory", subCategory);
			model.addAttribute("productList", productList);
			model.addAttribute("criteria", criteria);
			model.addAttribute("pageMaker", pageMaker);
			view = SURL;
		} catch (Exception e) {
			e.printStackTrace();
			view = FURL;
		}
		return view;
	}
	
	public void getRecommenedProductListGrouped(Model model) throws Exception {
		List<ShopDTO> recommenedProductList = shopMapper.recommenedProductList();
		log.info("Recommended Product List Size: {}", recommenedProductList.size());
		List<List<ShopDTO>> groupRecommenedProductList = groupRecommenedProductList(recommenedProductList, 4);
		log.info("Grouped Recommended Product List Size: {}", groupRecommenedProductList.size());
		model.addAttribute("groupRecommenedProductList", groupRecommenedProductList);
	}
	
	private List<List<ShopDTO>> groupRecommenedProductList(List<ShopDTO> recommenedProductList, int groupSize) {
		List<List<ShopDTO>> groupRecommenedProductList = new ArrayList<>();
		 for (int i = 0; i < recommenedProductList.size(); i += groupSize) {
			 groupRecommenedProductList.add(recommenedProductList.subList(i, Math.min(i + groupSize, recommenedProductList.size())));
		 }
		 return groupRecommenedProductList;
	}

	@Transactional
	public String productDetail(int productID, int mainCategoryID, Model model) {
		String view = null;
		final String SURL = "shop/productDetail";
		final String FURL = "redirect:/";
		try {
			ShopDTO product = shopMapper.productDetail(productID);
			if (!product.getOptionGrade().equals("OT0")) {
				ShopDTO optionList = shopMapper.getOptionList(mainCategoryID);
				List<ShopDTO> majorOptionList = shopMapper.getMajorOptionList(productID);
				product.updateMajorOptionFields();
				optionList.updateMajorOptionFields();
				for (ShopDTO dto : majorOptionList) {
					dto.updateMajorOptionFields();
				}
				model.addAttribute("optionList", optionList);
				model.addAttribute("majorOptionList", majorOptionList);
			}
			model.addAttribute("product", product);
			view = SURL;
		} catch (Exception e) {
			e.printStackTrace();
			view = FURL;
		}
		return view;
	}

	public String getMajorOptionPrice(int productID, int majorOptionID) {
		String result = "";
		ShopDTO majorOptionPrice = shopMapper.getMajorOptionPrice(productID, majorOptionID);
		if (majorOptionID != 0) {
			majorOptionPrice.updateMajorOptionFields();
			result = String.valueOf(majorOptionPrice.getMajorOptionPrice());
		}
		return result;
	}

	@Transactional
	public List<ShopDTO> getSubOptionList(int productID, int majorOptionPrice) throws Exception {
		List<ShopDTO> subOptionList = new ArrayList<>();
		try {
			subOptionList = shopMapper.getSubOptionList(productID);
			for (ShopDTO dto : subOptionList) {
				dto.setMajorOptionPrice(majorOptionPrice);
				dto.updateSubOptionFields();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return subOptionList;
	}

	public String getSubOptionPrice(int subOptionID, int majorOptionPrice) {
		String result = "";
		try {
			ShopDTO subOption = shopMapper.getSubOptionOne(subOptionID);
			if (subOptionID != 0) {
				subOption.setMajorOptionPrice(majorOptionPrice);
				subOption.updateSubOptionFields();
				result = String.valueOf(subOption.getSubOptionPrice());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Transactional
	public List<ShopDTO> getMinerOptionList(int productID, int subOptionPrice) throws Exception {
		List<ShopDTO> minerOptionList = new ArrayList<>();
		try {
			minerOptionList = shopMapper.getMinerOptionList(productID);
			for (ShopDTO dto : minerOptionList) {
				dto.setSubOptionPrice(subOptionPrice);
				dto.updateMinerOptionFields();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return minerOptionList;
	}

	public String getMinerOptionPrice(int minerOptionID, int subOptionPrice) {
		String result = "";
		try {
			ShopDTO minerOption = shopMapper.getMinerOptionOne(minerOptionID);
			if (minerOptionID != 0) {
				minerOption.setSubOptionPrice(subOptionPrice);
				minerOption.updateMinerOptionFields();
				result = String.valueOf(minerOption.getMinerOptionPrice());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int cart(ShopDTO shop, HttpServletRequest request) {
		int msg = 0;
		try {
			request.setCharacterEncoding(encoding);
			int result = shopMapper.insertCart(shop);
			if (result == 1) {
				msg = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}

	public String cart(String userID, Model model) {
		final String SURL = "shop/cart";
		final String FURL = "redirect:/";
		String view = FURL;
		try {
			List<ShopDTO> cartList = getCartList(userID);
			model.addAttribute("cartList", cartList);
			view = SURL;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	public List<ShopDTO> getCartList(String userID) throws Exception {
		List<ShopDTO> cartList = shopMapper.getCartList(userID);
		for (ShopDTO dto : cartList) {
			if (!dto.getOptionGrade().equals("OT0")) {
				dto.updateMajorOptionFields();
				if (!dto.getOptionGrade().equals("OT1")) {
					dto.updateSubOptionFields();
					if (!dto.getOptionGrade().equals("OT2")) {
						dto.updateMinerOptionFields();
					}
				}
			}
			dto.calculateTotalPrice();
		}
		return cartList;
	}

	public int deleteCartOne(int cartID) {
		int msg = 0;
		try {
			int result = shopMapper.deleteCartOne(cartID);
			if (result == 1) {
				msg = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}
	
}
