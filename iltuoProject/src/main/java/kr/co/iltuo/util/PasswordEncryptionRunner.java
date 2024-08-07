package kr.co.iltuo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import kr.co.iltuo.service.PasswordEncryptionService;

@Component
public class PasswordEncryptionRunner implements CommandLineRunner {

	@Autowired
	PasswordEncryptionService passwordEncryptionService;

	@Override
	public void run(String... args) throws Exception {
		// 애플리케이션 시작 시 비밀번호 암호화 작업을 수행
		passwordEncryptionService.encryptExistingPasswords();
	}

}
