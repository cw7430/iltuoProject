package kr.co.iltuo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.iltuo.dto.UserDTO;
import kr.co.iltuo.mapper.UserMapper;

@Service
public class PasswordEncryptionService {
	
	@Autowired
	UserMapper userMapper;
	
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public void encryptExistingPasswords() {
		try {
			List<UserDTO> users = userMapper.findAllUsers();
			
			for (UserDTO user : users) {
				if (!user.getPassword().startsWith("$2a$")) {
					String encryptedPassword = passwordEncoder.encode(user.getPassword());
					user.setPassword(encryptedPassword);
					userMapper.updateUserPassword(user.getUserID(), encryptedPassword);
				}
			}
		} catch (Exception e) {
			 e.printStackTrace();
		}
	}
}
