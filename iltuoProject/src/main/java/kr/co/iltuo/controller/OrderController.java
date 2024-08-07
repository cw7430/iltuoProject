package kr.co.iltuo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.iltuo.dto.CartListDTO;
import kr.co.iltuo.dto.OrderDTO;
import kr.co.iltuo.dto.ShopDTO;
import kr.co.iltuo.dto.UserDTO;
import kr.co.iltuo.service.OrderService;


@Controller
public class OrderController {
	@Autowired
	OrderService orderService;
	
	@GetMapping("/orderOne")
	public String orderOne(@RequestParam("orderID") int orderID, @RequestParam("userID") String userID, Model model) {
		String view = orderService.orderOne(orderID, userID, model);
		return view;
	}
	
	@PostMapping("/order/orderOne")
	public String orderOne(ShopDTO shop, HttpServletRequest request) {
		String view = orderService.orderOne(shop, request);
		return view;
	}
	
	@PostMapping("/order/insertPriceOne")
	public String insertPriceOne(ShopDTO shop, HttpServletRequest request) {
		String view = orderService.insertPriceOne(shop, request);
		return view;
	}
	
	@GetMapping("/payment")
	public String payment(@RequestParam("priceID") int priceID, @RequestParam("userID") String userID, Model model) {
		String view = orderService.payment(priceID, userID, model);
		return view;
	}
	
	@PostMapping("/payment")
	public String payment(UserDTO user, OrderDTO order, HttpServletRequest request, RedirectAttributes rttr) {
		String view = orderService.payment(user, order, request, rttr);
		return view;
	}
	
	
	@PostMapping("/order/insertPriceMultiple")
	public String insertPriceMultiple(ShopDTO shop, @ModelAttribute CartListDTO cartListDTO, HttpServletRequest request) {
		List<ShopDTO> cartList = cartListDTO.getCartList();
		String view = orderService.insertPriceMultiple(shop, cartList, request);
		return view;
	}
	
	@GetMapping("/orderList")
	public String orderList(@RequestParam("userID") String userID, Model model) {
		String view = orderService.orderList(userID, model);
		return view;
	}
	
	@GetMapping("/orderDetail")
	public String orderDetail(@RequestParam("priceID") int priceID, @RequestParam("userID") String userID, Model model) {
		String view = orderService.orderDetail(priceID, userID, model);
		return view;
	}
	
	@PostMapping("/orderList/cancelOrder")
	public String cancelOrder(OrderDTO order, HttpServletRequest request, RedirectAttributes rttr) {
		String view = orderService.cancelOrder(order, request, rttr);
		return view;
	}
	
}
