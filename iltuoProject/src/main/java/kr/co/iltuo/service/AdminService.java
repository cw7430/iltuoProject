package kr.co.iltuo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.iltuo.dto.ShopDTO;
import kr.co.iltuo.mapper.AdminMapper;
import kr.co.iltuo.mapper.ShopMapper;
import kr.co.iltuo.util.PageMaker;
import kr.co.iltuo.util.ShopCriteria;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdminService {
	@Autowired
	AdminMapper adminMapper;
	@Autowired
	ShopMapper shopMapper;

	private final String encoding = "UTF-8";

	@Transactional
	public String categoryManagement(Model model) {
		final String FURL = "redirect:/admain";
		final String SURL = "admin/categoryManagement";
		String view = FURL;
		try {
			List<ShopDTO> validList = shopMapper.getMainCategoryList();
			List<ShopDTO> invalidList = adminMapper.mainCategoryInvalidList();
			model.addAttribute("validList", validList);
			model.addAttribute("invalidList", invalidList);
			view = SURL;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	public String updateMainCategoryValid(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = "redirect:/categoryManagement";
		String msg = "처리 중 오류가 발생하였습니다.";
		try {
			request.setCharacterEncoding(encoding);
			int result = adminMapper.updateMainCategoryValid(shop);
			if (shop.getViewOrder().equals("b")) {
				view = "redirect:/mainCategoryDetail?mainCategoryID=" + shop.getMainCategoryID();
			}
			if (result > 0) {
				if (shop.getOptionValidID().equals("OPV1")) {
					msg = shop.getMainCategoryName() + "을(를) 성공적으로 비활성화 하였습니다.";
				} else if (shop.getOptionValidID().equals("OPV0")) {
					msg = shop.getMainCategoryName() + "을(를) 성공적으로 활성화 하였습니다.";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}
	
	public String updateMainCategoryName(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = "redirect:/categoryManagement";
		String msg = "처리 중 오류가 발생하였습니다.";
		try {
			request.setCharacterEncoding(encoding);
			int result = adminMapper.updateMainCategoryName(shop);
			if (result > 0) {
				msg = "성공적으로 수정하였습니다.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}
	
	public String insertMainCategory(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = "redirect:/categoryManagement";
		String msg = "처리 중 오류가 발생하였습니다.";
		try {
			request.setCharacterEncoding(encoding);
			int result = adminMapper.insertMainCategory(shop);
			if (result > 0) {
				msg = "성공적으로 등록하였습니다.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}

	@Transactional
	public String mainCategoryDetail(int mainCategoryID, Model model) {
		final String FURL = "redirect:/categoryManagement";
		final String SURL = "admin/mainCategoryDetail";
		String view = FURL;
		try {
			ShopDTO mainCatrgory = adminMapper.mainCategoryDetail(mainCategoryID);
			List<ShopCriteria> validSubCategoryList = shopMapper.getSubCategoryList(mainCategoryID);
			List<ShopDTO> invalidSubCategoryList = adminMapper.subCategoryInvalidList(mainCategoryID);
			ShopDTO optionList = shopMapper.getOptionList(mainCategoryID);
			model.addAttribute("mainCatrgory", mainCatrgory);
			model.addAttribute("validSubCategoryList", validSubCategoryList);
			model.addAttribute("invalidSubCategoryList", invalidSubCategoryList);
			model.addAttribute("optionList", optionList);
			view = SURL;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
	
	public String insertSubCategory(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = "redirect:/mainCategoryDetail?mainCategoryID=" + shop.getMainCategoryID();
		String msg = "처리 중 오류가 발생하였습니다.";
		try {
			request.setCharacterEncoding(encoding);
			int result = adminMapper.insertSubCategory(shop);
			if (result > 0) {
				msg = "성공적으로 등록하였습니다.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}

	public String updateSubCategoryValid(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = "redirect:/mainCategoryDetail?mainCategoryID=" + shop.getMainCategoryID();
		String msg = "처리 중 오류가 발생하였습니다.";
		try {
			request.setCharacterEncoding(encoding);
			int result = adminMapper.updateSubCategoryValid(shop);
			if (result > 0) {
				if (shop.getOptionValidID().equals("OPV1")) {
					msg = shop.getSubCategoryName() + "을(를) 성공적으로 비활성화 하였습니다.";
				} else if (shop.getOptionValidID().equals("OPV0")) {
					msg = shop.getSubCategoryName() + "을(를) 성공적으로 활성화 하였습니다.";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}
	
	public String updateSubCategoryName(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = "redirect:/mainCategoryDetail?mainCategoryID=" + shop.getMainCategoryID();
		String msg = "처리 중 오류가 발생하였습니다.";
		try {
			request.setCharacterEncoding(encoding);
			int result = adminMapper.updateSubCategoryName(shop);
			if (result > 0) {
				msg = "성공적으로 수정하였습니다.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}
	
	public String updateMajorOptionCategory(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = "redirect:/mainCategoryDetail?mainCategoryID=" + shop.getMainCategoryID();
		String msg = "처리 중 오류가 발생하였습니다.";
		try {
			request.setCharacterEncoding(encoding);
			int result = adminMapper.updateMajorOptionCategory(shop);
			if (result > 0) {
				msg = "성공적으로 수정하였습니다.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}
	
	public String updateSubOptionCategory(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = "redirect:/mainCategoryDetail?mainCategoryID=" + shop.getMainCategoryID();
		String msg = "처리 중 오류가 발생하였습니다.";
		try {
			request.setCharacterEncoding(encoding);
			int result = adminMapper.updateSubOptionCategory(shop);
			if (result > 0) {
				msg = "성공적으로 수정하였습니다.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}
	
	public String updateMinerOptionCategory(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = "redirect:/mainCategoryDetail?mainCategoryID=" + shop.getMainCategoryID();
		String msg = "처리 중 오류가 발생하였습니다.";
		try {
			request.setCharacterEncoding(encoding);
			int result = adminMapper.updateMinerOptionCategory(shop);
			if (result > 0) {
				msg = "성공적으로 수정하였습니다.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}
	
	@Transactional
	public String deleteMajorOption(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = "redirect:/mainCategoryDetail?mainCategoryID=" + shop.getMainCategoryID();
		String msg = "처리 중 오류가 발생하였습니다.";
		try {
			request.setCharacterEncoding(encoding);
			if(shop.getOptionGradeValidate().equals("OT3") || shop.getOptionGradeValidate().equals("OT2")) {
				msg = "하위 옵션을 먼저 삭제해주세요.";
			} else {
				int updateMajorOption = adminMapper.updateMajorOptionValid(shop);
				if (updateMajorOption > 0) {
					int updateOptionGrade = adminMapper.updateOptionGrade(shop);
					if (updateOptionGrade > 0) {
						msg = "성공적으로 삭제하였습니다.";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}
	
	@Transactional
	public String deleteSubOption(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = "redirect:/mainCategoryDetail?mainCategoryID=" + shop.getMainCategoryID();
		String msg = "처리 중 오류가 발생하였습니다.";
		try {
			request.setCharacterEncoding(encoding);
			if(shop.getOptionGradeValidate().equals("OT3")) {
				msg = "하위 옵션을 먼저 삭제해주세요.";
			} else {
				int updateSubOption = adminMapper.updateSubOptionValid(shop);
				if (updateSubOption > 0) {
					int updateOptionGrade = adminMapper.updateOptionGrade(shop);
					if (updateOptionGrade > 0) {
						msg = "성공적으로 삭제하였습니다.";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}
	
	@Transactional
	public String deleteMinerOption(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = "redirect:/mainCategoryDetail?mainCategoryID=" + shop.getMainCategoryID();
		String msg = "처리 중 오류가 발생하였습니다.";
		try {
			request.setCharacterEncoding(encoding);
			int updateMinerOption = adminMapper.updateMinerOptionValid(shop);
			if (updateMinerOption > 0) {
				int updateOptionGrade = adminMapper.updateOptionGrade(shop);
				if (updateOptionGrade > 0) {
					msg = "성공적으로 삭제하였습니다.";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}
	
	@Transactional
	public String optionDetail(int mainCategoryID, Model model) {
		final String SURL = "admin/optionDetail";
		final String FURL = "redirect:/mainCategoryDetail?mainCategoryID=" + mainCategoryID;
		String view = FURL;
		try {
			ShopDTO mainCategory = adminMapper.mainCategoryDetail(mainCategoryID);
			ShopDTO optionList = shopMapper.getOptionList(mainCategoryID);
			List<ShopDTO> optionGradeList = adminMapper.optionGradeList();
			List<ShopDTO> isPriceChangeList = adminMapper.isPriceChangeList();
			List<ShopDTO> majorOptionList = adminMapper.majorOptionList(mainCategoryID);
			List<ShopDTO> subOptionList = adminMapper.subOptionList(mainCategoryID);
			List<ShopDTO> minerOptionList = adminMapper.minerOptionList(mainCategoryID);
			model.addAttribute("mainCategory", mainCategory);
			model.addAttribute("optionList", optionList);
			model.addAttribute("optionGradeList", optionGradeList);
			model.addAttribute("isPriceChangeList", isPriceChangeList);
			model.addAttribute("majorOptionList", majorOptionList);
			model.addAttribute("subOptionList", subOptionList);
			model.addAttribute("minerOptionList", minerOptionList);
			view = SURL;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
	
	@Transactional
	public String updateOptionGrade(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = "redirect:/optionDetail?mainCategoryID=" + shop.getMainCategoryID();
		String msg = "처리 중 오류가 발생하였습니다.";
		try {
			int updateOptionGrade = adminMapper.updateOptionGrade(shop);
			if (updateOptionGrade > 0) {
				if (shop.getOptionGrade().equals("OT3")) {
				} else if (shop.getOptionGrade().equals("OT2")) {
					int updateMinerOption = adminMapper.updateMinerOptionValid(shop);
					log.info("Delete: " + updateMinerOption + "MinerOption");
				} else if (shop.getOptionGrade().equals("OT1")) {
					int updateMinerOption = adminMapper.updateMinerOptionValid(shop);
					log.info("Delete: " + updateMinerOption + "MinerOption");
					int updateSubOption = adminMapper.updateSubOptionValid(shop);
					log.info("Delete: " + updateSubOption + "SubOption");
				} else if (shop.getOptionGrade().equals("OT0")) {
					int updateMinerOption = adminMapper.updateMinerOptionValid(shop);
					log.info("Delete: " + updateMinerOption + "MinerOption");
					int updateSubOption = adminMapper.updateSubOptionValid(shop);
					log.info("Delete: " + updateSubOption + "SubOption");
					int updateMajorOption = adminMapper.updateMajorOptionValid(shop);
					log.info("Delete: " + updateMajorOption + "MajorOption");
				}
				msg = "성공적으로 수정하였습니다.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}
	
	public String updateMajorOptionSort(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = "redirect:/optionDetail?mainCategoryID=" + shop.getMainCategoryID();
		String msg = "처리 중 오류가 발생하였습니다.";
		try {
			int result = adminMapper.updateMajorOptionSort(shop);
			if (result > 0) {
				msg = "성공적으로 수정하였습니다.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}
	
	public String updateSubOptionSort(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = "redirect:/optionDetail?mainCategoryID=" + shop.getMainCategoryID();
		String msg = "처리 중 오류가 발생하였습니다.";
		try {
			int result = adminMapper.updateSubOptionSort(shop);
			if (result > 0) {
				msg = "성공적으로 수정하였습니다.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}
	
	public String updateMinerOptionSort(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = "redirect:/optionDetail?mainCategoryID=" + shop.getMainCategoryID();
		String msg = "처리 중 오류가 발생하였습니다.";
		try {
			int result = adminMapper.updateMinerOptionSort(shop);
			if (result > 0) {
				msg = "성공적으로 수정하였습니다.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}
	
	public String deleteMajorOptionOne(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = "redirect:/optionDetail?mainCategoryID=" + shop.getMainCategoryID();
		String msg = "처리 중 오류가 발생하였습니다.";
		try {
			int result = adminMapper.updateMajorOptionValidOne(shop);
			if (result > 0) {
				msg = "성공적으로 삭제하였습니다.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}
	
	public String deleteSubOptionOne(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = "redirect:/optionDetail?mainCategoryID=" + shop.getMainCategoryID();
		String msg = "처리 중 오류가 발생하였습니다.";
		try {
			int result = adminMapper.updateSubOptionValidOne(shop);
			if (result > 0) {
				msg = "성공적으로 삭제하였습니다.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}
	
	public String deleteMinerOptionOne(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = "redirect:/optionDetail?mainCategoryID=" + shop.getMainCategoryID();
		String msg = "처리 중 오류가 발생하였습니다.";
		try {
			int result = adminMapper.updateMinerOptionValidOne(shop);
			if (result > 0) {
				msg = "성공적으로 삭제하였습니다.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}
	
	public String insertMajorOption(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = "redirect:/optionDetail?mainCategoryID=" + shop.getMainCategoryID();
		String msg = "처리 중 오류가 발생하였습니다.";
		try {
			int result = adminMapper.insertMajorOption(shop);
			if (result > 0) {
				msg = "성공적으로 삭제하였습니다.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}
	
	public String insertSubOption(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = "redirect:/optionDetail?mainCategoryID=" + shop.getMainCategoryID();
		String msg = "처리 중 오류가 발생하였습니다.";
		try {
			int result = adminMapper.insertSubOption(shop);
			if (result > 0) {
				msg = "성공적으로 삭제하였습니다.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}
	
	public String insertMinerOption(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = "redirect:/optionDetail?mainCategoryID=" + shop.getMainCategoryID();
		String msg = "처리 중 오류가 발생하였습니다.";
		try {
			int result = adminMapper.insertMinerOption(shop);
			if (result > 0) {
				msg = "성공적으로 삭제하였습니다.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}
	
	@Transactional
	public String adminProductList(ShopCriteria criteria, Model model) {
		final String SURL = "admin/adminProductList";
		final String FURL = "redirect:/admain";
		String view = FURL;
		try {
			List<ShopCriteria> mainCategory = adminMapper.adminMainCategoryList();
			List<ShopCriteria> subCategory = shopMapper.getSubCategoryList(criteria.getMainCategoryID());
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
		}
		return view;
	}
}
