package kr.co.iltuo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.co.iltuo.dto.UserDTO;
import kr.co.iltuo.mapper.UserMapper;
import kr.co.iltuo.util.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String userID) throws UsernameNotFoundException {
		UserDTO user = null;
		try {
            user = userMapper.findByUserID(userID);
        } catch (Exception e) {
            e.printStackTrace();
        	throw new UsernameNotFoundException("DB오류");
        }
		
		if (user == null) {
			throw new UsernameNotFoundException("사용자 정보를 찾을 수 없습니다.");
		}
		
		return new CustomUserDetails(user);
	}

}
