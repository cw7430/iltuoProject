package kr.co.iltuo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import kr.co.iltuo.service.ShopService;

@ControllerAdvice
public class GlobalControllerAdvice {
	@Autowired
	ShopService shopService;

	@ModelAttribute
	public void mainCategoryList(Model model) {
		shopService.getMainCategoryList(model);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView handleAllExceptions(Exception ex) {
		ModelAndView model = new ModelAndView("error");
		model.addObject("errorMessage", ex.getMessage());
		return model;
	}
}
