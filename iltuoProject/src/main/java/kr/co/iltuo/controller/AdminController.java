package kr.co.iltuo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.iltuo.dto.ShopDTO;
import kr.co.iltuo.service.AdminService;
import kr.co.iltuo.service.ShopService;
import kr.co.iltuo.util.ShopCriteria;

@Controller
public class AdminController {
	@Autowired
	AdminService adminService;
	@Autowired
	ShopService shopService;
	
	@GetMapping("/admain")
	public String admain(Model model) throws Exception {
		shopService.getRecommenedProductListGrouped(model);
		return "admin/admain";
	}
	
	@GetMapping("/categoryManagement")
	public String categoryManagement(Model model) throws Exception {
		String view = adminService.categoryManagement(model);
		return view;
	}
	
	@PostMapping("/categoryManagement/updateMainCategoryValid")
	public String updateMainCategoryValid(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = adminService.updateMainCategoryValid(shop, request, rttr);
		return view;
	}
	
	@PostMapping("/categoryManagement/updateMainCategoryName")
	public String updateMainCategoryName(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = adminService.updateMainCategoryName(shop, request, rttr);
		return view;
	}
	
	@PostMapping("/categoryManagement/insertMainCategory")
	public String insertMainCategory(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = adminService.insertMainCategory(shop, request, rttr);
		return view;
	}
	
	@GetMapping("/mainCategoryDetail")
	public String mainCategoryDetail(@RequestParam("mainCategoryID") int mainCategoryID, Model model) {
		String view = adminService.mainCategoryDetail(mainCategoryID, model);
		return view;
	}
	
	@PostMapping("/mainCategoryDetail/insertSubCategory")
	public String insertSubCategory(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = adminService.insertSubCategory(shop, request, rttr);
		return view;
	}
	
	@PostMapping("/mainCategoryDetail/updateSubCategoryValid")
	public String updateSubCategoryValid(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = adminService.updateSubCategoryValid(shop, request, rttr);
		return view;
	}
	
	@PostMapping("/mainCategoryDetail/updateSubCategoryName")
	public String updateSubCategoryName(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = adminService.updateSubCategoryName(shop, request, rttr);
		return view;
	}
	
	@PostMapping("/mainCategoryDetail/updateMajorOptionCategory")
	public String updateMajorOptionCategory(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = adminService.updateMajorOptionCategory(shop, request, rttr);
		return view;
	}
	
	@PostMapping("/mainCategoryDetail/updateSubOptionCategory")
	public String updateSubOptionCategory(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = adminService.updateSubOptionCategory(shop, request, rttr);
		return view;
	}
	
	@PostMapping("/mainCategoryDetail/updateMinerOptionCategory")
	public String updateMinerOptionCategory(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = adminService.updateMinerOptionCategory(shop, request, rttr);
		return view;
	}
	
	@PostMapping("/mainCategoryDetail/deleteMajorOption")
	public String deleteMajorOption(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = adminService.deleteMajorOption(shop, request, rttr);
		return view;
	}
	
	@PostMapping("/mainCategoryDetail/deleteSubOption")
	public String deleteSubOption(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = adminService.deleteSubOption(shop, request, rttr);
		return view;
	}
	
	@PostMapping("/mainCategoryDetail/deleteMinerOption")
	public String deleteMinerOption(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = adminService.deleteMinerOption(shop, request, rttr);
		return view;
	}
	
	@GetMapping("/optionDetail")
	public String optionDetail(@RequestParam("mainCategoryID") int mainCategoryID, Model model) {
		String view = adminService.optionDetail(mainCategoryID, model);
		return view;
	}
	
	@PostMapping("/optionDetail/updateOptionGrade")
	public String updateOptionGrade(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = adminService.updateOptionGrade(shop, request, rttr);
		return view;
	}
	
	@PostMapping("/optionDetail/updateMajorOptionSort")
	public String updateMajorOptionSort(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = adminService.updateMajorOptionSort(shop, request, rttr);
		return view;
	}
	
	@PostMapping("/optionDetail/updateSubOptionSort")
	public String updateSubOptionSort(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = adminService.updateSubOptionSort(shop, request, rttr);
		return view;
	}
	
	@PostMapping("/optionDetail/updateMinerOptionSort")
	public String updateMinerOptionSort(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = adminService.updateMinerOptionSort(shop, request, rttr);
		return view;
	}
	
	@PostMapping("/optionDetail/deleteMajorOptionOne")
	public String deleteMajorOptionOne(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = adminService.deleteMajorOptionOne(shop, request, rttr);
		return view;
	}
	
	@PostMapping("/optionDetail/deleteSubOptionOne")
	public String deleteSubOptionOne(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = adminService.deleteSubOptionOne(shop, request, rttr);
		return view;
	}
	
	@PostMapping("/optionDetail/deleteMinerOptionOne")
	public String deleteMinerOptionOne(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = adminService.deleteMinerOptionOne(shop, request, rttr);
		return view;
	}
	
	@PostMapping("/optionDetail/insertMajorOption")
	public String insertMajorOption(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = adminService.insertMajorOption(shop, request, rttr);
		return view;
	}
	
	@PostMapping("/optionDetail/insertSubOption")
	public String insertSubOption(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = adminService.insertSubOption(shop, request, rttr);
		return view;
	}
	
	@PostMapping("/optionDetail/insertMinerOption")
	public String insertMinerOption(ShopDTO shop, HttpServletRequest request, RedirectAttributes rttr) {
		String view = adminService.insertMinerOption(shop, request, rttr);
		return view;
	}
	
	@GetMapping("/adminProductList")
	public String adminProductList(ShopCriteria criteria, Model model) {
		String view = adminService.adminProductList(criteria, model);
		return view;
	}
}
