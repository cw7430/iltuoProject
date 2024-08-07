package kr.co.iltuo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.iltuo.dto.ShopDTO;
import kr.co.iltuo.service.ShopService;
import kr.co.iltuo.util.ShopCriteria;

@Controller
public class ShopController {

	@Autowired
	ShopService shopService;

	@GetMapping("/productList")
	public String productList(@RequestParam("mainCategoryID") int mainCategoryID, ShopCriteria criteria, Model model) {
		String view = shopService.productList(mainCategoryID, criteria, model);
		return view;
	}

	@GetMapping("/productDetail")
	public String productDetail(@RequestParam("productID") int productID,
			@RequestParam("mainCategoryID") int mainCategoryID, Model model) {
		String view = shopService.productDetail(productID, mainCategoryID, model);
		return view;
	}

	@GetMapping("/productDetail/getMajorOptionPrice")
	@ResponseBody
	public String getMajorOptionPrice(@RequestParam("productID") int productID,
			@RequestParam("majorOptionID") int majorOptionID) {
		String result = shopService.getMajorOptionPrice(productID, majorOptionID);
		return result;
	}

	@GetMapping("/productDetail/getSubOptionList")
	@ResponseBody
	public ResponseEntity<List<ShopDTO>> getSubOptionList(@RequestParam("productID") int productID,
			@RequestParam("majorOptionPrice") int majorOptionPrice) {
		try {
			List<ShopDTO> subOptionList = shopService.getSubOptionList(productID, majorOptionPrice);
			return ResponseEntity.ok(subOptionList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("/productDetail/getSubOptionPrice")
	@ResponseBody
	public String getSubOptionPrice(@RequestParam("subOptionID") int subOptionID, @RequestParam("majorOptionPrice") int majorOptionPrice) {
		String result = shopService.getSubOptionPrice(subOptionID, majorOptionPrice);
		return result;
	}
	
	@GetMapping("/productDetail/getMinerOptionList")
	@ResponseBody
	public ResponseEntity<List<ShopDTO>> getMinerOptionList(@RequestParam("productID") int productID, @RequestParam("subOptionPrice") int subOptionPrice) {
		try {
			List<ShopDTO> minerOptionList = shopService.getMinerOptionList(productID, subOptionPrice);
			return ResponseEntity.ok(minerOptionList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("/productDetail/getMinerOptionPrice")
	@ResponseBody
	public String getMinerOptionPrice(@RequestParam("minerOptionID") int minerOptionID, @RequestParam("subOptionPrice") int subOptionPrice) {
		String result = shopService.getMinerOptionPrice(minerOptionID, subOptionPrice);
		return result;
	}
	
	@GetMapping("/cart")
	public String cart(@RequestParam("userID") String userID, Model model) {
		String view = shopService.cart(userID, model);
		return view;
	}
	
	@PostMapping("/cart")
	@ResponseBody
	public ResponseEntity<Integer> cart(@RequestBody ShopDTO shop, HttpServletRequest request) {
		int result = shopService.cart(shop, request);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/cart/deleteCartOne")
	@ResponseBody
	public ResponseEntity<Integer> deleteCartOne(@RequestParam("cartID") int cartID) {
		int result = shopService.deleteCartOne(cartID);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/cart/refreshCart")
	@ResponseBody
	public ResponseEntity<List<ShopDTO>> refreshCart(@RequestParam("userID") String userID) {
		try {
			List<ShopDTO> cartList = shopService.getCartList(userID);
			return ResponseEntity.ok(cartList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
}
