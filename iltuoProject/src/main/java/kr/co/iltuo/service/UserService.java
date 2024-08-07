package kr.co.iltuo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.iltuo.dto.UserDTO;
import kr.co.iltuo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Autowired
	UserMapper userMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private String encoding = "UTF-8";

	public String userIDDuplicateCheck(String userID) {
		String userIDValidationCode = null;
		String regex = "^[a-zA-Z][a-zA-Z0-9]{5,24}$";
		try {
			if (userID == null || userID.isEmpty()) {
				userIDValidationCode = "b";
			} else if (!userID.matches(regex)) {
				userIDValidationCode = "c";
			} else {
				int count = userMapper.userIDDuplicateCheck(userID);
				if (count > 0) {
					userIDValidationCode = "d";
				} else {
					userIDValidationCode = "a";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			userIDValidationCode = "e";
		}
		return userIDValidationCode;
	}

	public String passwordDuplicateCheck(String password, String passwordDuplicateCheck) {
		String passwordValidationCode = null;
		String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&-])[A-Za-z\\d@$!%*?&-]{10,25}$";

		try {
			if (password == null || password.isEmpty()) {
				passwordValidationCode = "b";
			} else if (!password.matches(regex)) {
				passwordValidationCode = "c";
			} else {
				if (password.equals(passwordDuplicateCheck)) {
					passwordValidationCode = "a";
				} else {
					passwordValidationCode = "d";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			passwordValidationCode = "e";
		}

		return passwordValidationCode;
	}

	public String phoneNumValidationCheck(String phoneNum) {
		String phoneNumValidationCode = null;
		String regex = "^(010|011|016|017|018|019)-\\d{3,4}-\\d{4}$";

		try {
			if (phoneNum == null || phoneNum.isEmpty()) {
				phoneNumValidationCode = "b";
			} else {
				if (phoneNum.matches(regex)) {
					phoneNumValidationCode = "a";
				} else {
					phoneNumValidationCode = "c";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			phoneNumValidationCode = "d";
		}

		return phoneNumValidationCode;
	}

	@Transactional(rollbackFor = Exception.class)
	public String join(UserDTO user, HttpServletRequest request, RedirectAttributes rttr) throws Exception {
		request.setCharacterEncoding(encoding);
		String msg = null;
		String view = null;
		final String SUCCESSURL = "redirect:/";
		final String FAILURL = "redirect:/join";

		try {
			// 유효성 검사
			String userIDValidationCode = userIDDuplicateCheck(user.getUserID());
			String passwordValidationCode = passwordDuplicateCheck(user.getPassword(),
					user.getPasswordDuplicateCheck());
			String phoneNumValidationCode = phoneNumValidationCheck(user.getPhoneNum());

			if (!userIDValidationCode.equals("a")) {
				msg = "아이디 형식을 확인해주세요.";
				if (userIDValidationCode.equals("b")) {
					msg = "아이디를 입력해주세요.";
				}
				view = FAILURL;
			} else if (!passwordValidationCode.equals("a")) {
				msg = "비밀번호 형식을 확인해주세요.";
				if (passwordValidationCode.equals("b")) {
					msg = "비밀번호를 입력해주세요.";
				}
				view = FAILURL;
			} else if (user.getUserName() == null || user.getUserName().trim().isEmpty()) {
				msg = "이름을 입력해주세요.";
				view = FAILURL;
			} else if (!phoneNumValidationCode.equals("a")) {
				msg = "휴대전화번호 형식을 확인해주세요.";
				if (phoneNumValidationCode.equals("b")) {
					msg = "휴대전화번호를 입력해주세요.";
				}
				view = FAILURL;
			} else if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
				msg = "이메일을 입력해주세요.";
				view = FAILURL;
			} else if (user.getAddress() == null || user.getAddress().trim().isEmpty()) {
				msg = "주소를 입력해주세요.";
				view = FAILURL;
			} else {
				// 비밀번호 암호화
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				// 회원가입 처리
				int result = userMapper.join(user);

				// 주소등록 처리
				if (result == 1) {
					int addressResult = userMapper.insertAddress(user);
					if (addressResult == 1) {
						msg = "회원가입이 완료되었습니다.";
						log.info(msg);
						view = SUCCESSURL;
					} else {
						msg = "주소 등록 중 에러가 발생하였습니다.";
						throw new Exception(msg);
					}
				} else {
					msg = "회원가입 중 에러가 발생하였습니다.";
					throw new Exception(msg);
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			msg = "회원가입 중 에러가 발생하였습니다.";
			view = FAILURL;
			throw e;
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}

	@Transactional
	public String myProfile(String userID, Model model) {
		String view = null;
		final String SURL = "user/myProfile";
		final String FURL = "redirect:/";
		try {
			UserDTO user = userMapper.findByUserID(userID);
			List<UserDTO> address = userMapper.findAddress(userID);
			user.setFullAddress();
			address.forEach(UserDTO::setFullAddress);
			model.addAttribute("user", user);
			model.addAttribute("address", address);
			view = SURL;
		} catch (Exception e) {
			log.error("Error retrieving user profile for userID: {}", userID, e);
			view = FURL;
		}
		return view;
	}

	public String updateUserInformation(String userID, Model model) {
		String view = null;
		final String SURL = "user/updateUserInformation";
		final String FURL = "redirect:/myProfile?userID=" + userID;
		try {
			UserDTO user = userMapper.findByUserID(userID);
			model.addAttribute("user", user);
			view = SURL;
		} catch (Exception e) {
			log.error("Error retrieving user profile for userID: {}", userID, e);
			view = FURL;
		}
		return view;
	}

	public String updateUserInformation(UserDTO user, HttpServletRequest request, RedirectAttributes rttr) {
		String msg = null;
		final String SURL = "redirect:/myProfile?userID=" + user.getUserID();
		final String FURL = "redirect:/updateUserInformation?userID=" + user.getUserID();
		String view = null;
		
		try {
			request.setCharacterEncoding(encoding);
			
			// 유효성 검사
			String passwordValidationCode = passwordDuplicateCheck(user.getPassword(), user.getPasswordDuplicateCheck());
			String phoneNumValidationCode = phoneNumValidationCheck(user.getPhoneNum());
			
			if (!passwordValidationCode.equals("a")) {
				msg = "비밀번호 형식을 확인해주세요.";
				if (passwordValidationCode.equals("b")) {
					msg = "비밀번호를 입력해주세요.";
				}
				view = FURL;
			} else if (user.getUserName() == null || user.getUserName().trim().isEmpty()) {
				msg = "이름을 입력해주세요.";
				view = FURL;
			} else if (!phoneNumValidationCode.equals("a")) {
				msg = "휴대전화번호 형식을 확인해주세요.";
				if (phoneNumValidationCode.equals("b")) {
					msg = "휴대전화번호를 입력해주세요.";
				}
				view = FURL;
			} else if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
				msg = "이메일을 입력해주세요.";
				view = FURL;
			} else {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				int result = userMapper.updateUserInformation(user);
				if (result == 1) {
					msg = "회원정보 수정이 성공하였습니다.";
					view = SURL;
				} else {
					msg = "회원정보 수정 중 에러가 발생하였습니다.";
					view = FURL;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "회원정보 수정 중 에러가 발생하였습니다.";
			view = FURL;
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}

	public String insertAddress(UserDTO user, HttpServletRequest request, RedirectAttributes rttr) {
		String msg = "null";
		String view = "redirect:/myProfile?userID=" + user.getUserID();
		try {
			request.setCharacterEncoding(encoding);
			if (user.getAddress() == null || user.getAddress().trim().isEmpty()) {
				msg = "주소를 입력해주세요.";
			} else {
				int result = userMapper.insertAddress(user);
				if (result == 1) {
					msg = "주소등록이 성공하였습니다.";
				} else {
					msg = "주소등록 중 에러가 발생하였습니다.";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "주소등록 중 에러가 발생하였습니다.";
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}

	@Transactional
	public String setAddressMain(UserDTO user, HttpServletRequest request, RedirectAttributes rttr) {
		String msg = null;
		String view = "redirect:/myProfile?userID=" + user.getUserID();
		try {
			request.setCharacterEncoding(encoding);
			int result1 = userMapper.setAddressNotMain(user);
			int result2 = userMapper.setAddressMain(user);
			if (result1 == 1 && result2 == 1) {
				msg = "기본주소변경이 성공하였습니다.";
			} else {
				msg = "기본주소변경 중 에러가 발생하였습니다.";
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "기본주소변경 중 에러가 발생하였습니다.";
		}
		rttr.addFlashAttribute("msg", msg);
		return view;
	}

	public String deleteAddress(UserDTO user, String userID, RedirectAttributes rttr) {
		String msg = null;
		try {
			int result = userMapper.deleteAddress(user);
			if (result == 1) {
				msg = "주소가 삭제되었습니다.";
			} else {
				msg = "주소삭제 중 에러가 발생하였습니다.";
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "주소삭제 중 에러가 발생하였습니다.";
		}
		String view = "redirect:/myProfile?userID=" + userID;
		rttr.addFlashAttribute("msg", msg);
		return view;
	}
}
