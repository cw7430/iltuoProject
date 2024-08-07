package kr.co.iltuo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.iltuo.dto.UserDTO;
import kr.co.iltuo.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping("/join")
	public String join() {
		log.info("회원가입 창 진입");
		return "user/join";
	}

	@GetMapping("join/userIDDuplicateCheck")
	@ResponseBody
	public ResponseEntity<String> userIDDuplicateCheck(@RequestParam("userID") String userID) {
		String validationCode = userService.userIDDuplicateCheck(userID);
		String msg = null;
		String color = null;
		String red = "#FF5722";
		String blue = "#C12DFF";
		if (validationCode.equals("b")) {
			msg = "";
		} else if (validationCode.equals("c")) {
			msg = "아이디는 6자 이상 25자 이하의 영문 또는 영문, 숫자의 조합으로 입력해주세요.";
			color = red;
		} else if (validationCode.equals("d")) {
			msg = "중복된 아이디입니다.";
			color = red;
		} else if (validationCode.equals("a")) {
			msg = "사용 가능한 아이디입니다.";
			color = blue;
		} else if (validationCode.equals("e")) {
			msg = "아이디 중복체크 중 에러 발생";
			color = red;
		}
		String responseHtml = "<span style='color:" + color + ";'>" + msg + "</span>";
		responseHtml += "<script th:inline='javascript'>document.getElementById('userIDValidationCode').value = '" + validationCode + "';</script>";
		return ResponseEntity.ok(responseHtml);
	}

	@GetMapping({"join/passwordDuplicateCheck", "updateUserInformation/passwordDuplicateCheck"})
	@ResponseBody
	public ResponseEntity<String> passwordDuplicateCheck(@RequestParam("password") String password,
			@RequestParam("passwordDuplicateCheck") String passwordDuplicateCheck) {
		String validationCode = userService.passwordDuplicateCheck(password, passwordDuplicateCheck);
		String msg = null;
		String color = null;
		String red = "#FF5722";
		String blue = "#C12DFF";
		if (validationCode.equals("b")) {
			msg = "";
		} else if (validationCode.equals("c")) {
			msg = "비밀번호는 10자 이상 25자 이하의 영문, 숫자, 특수문자의 조합으로 입력해주세요.";
			color = red;
		} else if (validationCode.equals("a")) {
			msg = "비밀번호가 일치합니다.";
			color = blue;
		} else if (validationCode.equals("d")) {
			msg = "비밀번호가 일치하지 않습니다.";
			color = red;
		} else if (validationCode.equals("e")) {
			msg = "비밀번호 검사 중 에러 발생.";
			color = red;
		}
		String responseHtml = "<span style='color:" + color + ";'>" + msg + "</span>";
		responseHtml += "<script th:inline='javascript'>document.getElementById('passwordValidationCode').value = '" + validationCode + "';</script>";
		return ResponseEntity.ok(responseHtml);
	}

	@GetMapping({"join/phoneNumValidationCheck", "updateUserInformation/phoneNumValidationCheck"})
	@ResponseBody
	public ResponseEntity<String> phoneNumValidationCheck(@RequestParam("phoneNum") String phoneNum) {
		String validationCode = userService.phoneNumValidationCheck(phoneNum);
		String msg = null;
		String color = null;
		String red = "#FF5722";
		String blue = "#C12DFF";
		if (validationCode.equals("b")) {
			msg = "";
		} else if (validationCode.equals("a")) {
			msg = "유효한 전화번호 형식 입니다.";
			color = blue;
		} else if (validationCode.equals("c")) {
			msg = "유효하지 않은 전화번호 형식 입니다.";
			color = red;
		} else if (validationCode.equals("d")) {
			msg = "전화번호 검사 중 에러 발생.";
			color = red;
		}
		String responseHtml = "<span style='color:" + color + ";'>" + msg + "</span>";
		responseHtml += "<script th:inline='javascript'>document.getElementById('phoneNumValidationCode').value = '" + validationCode + "';</script>";
		return ResponseEntity.ok(responseHtml);
	}

	@PostMapping("/join")
	public String join(UserDTO user, HttpServletRequest request, RedirectAttributes rttr) throws Exception {
		String view = userService.join(user, request, rttr);
		return view;
	}
	
	@GetMapping("/myProfile")
	public String myProfile(@RequestParam("userID") String userID, Model model) {
		String view = userService.myProfile(userID, model);
		return view;
	}
	
	@GetMapping("/updateUserInformation")
	public String updateUserInformation(@RequestParam("userID") String userID, Model model) {
		String view = userService.updateUserInformation(userID, model);
		return view;
	}
	
	
	@PostMapping("/updateUserInformation")
	public String updateUserInformation(UserDTO user, HttpServletRequest request, RedirectAttributes rttr) {
		String view = userService.updateUserInformation(user, request, rttr);
		return view;
	}
	
	@PostMapping("/myProfile/insertAddress")
	public String insertAddress(UserDTO user, HttpServletRequest request, RedirectAttributes rttr) {
		String view = userService.insertAddress(user, request, rttr);
		return view;
	}
	
	@PostMapping("/myProfile/setAddressMain")
	public String setAddressMain(UserDTO user, HttpServletRequest request, RedirectAttributes rttr) {
		String view = userService.setAddressMain(user, request, rttr);
		return view;
	}
	
	@PostMapping("/myProfile/deleteAddress")
	public String deleteAddress(UserDTO user, @RequestParam("userID") String userID, RedirectAttributes rttr) {
		String view = userService.deleteAddress(user, userID, rttr);
		return view;
	}
}
