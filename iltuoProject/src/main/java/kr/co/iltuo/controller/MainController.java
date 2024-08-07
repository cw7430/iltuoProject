package kr.co.iltuo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.iltuo.service.ShopService;

@Controller
public class MainController {

	@Autowired
	ShopService shopService;
	
	@GetMapping("/")
	public String main(Model model) throws Exception {
		shopService.getRecommenedProductListGrouped(model);
		return "index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

}
