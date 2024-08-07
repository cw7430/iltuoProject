package kr.co.iltuo.util;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import kr.co.iltuo.dto.UserDTO;

public class CustomUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	private final UserDTO user;
	
	public CustomUserDetails(UserDTO user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if ("AR1".equals(user.getAdminRightsID())) {
			return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
		} else if("AR0".equals(user.getAdminRightsID())) {
			return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
		} else {
            return Collections.emptyList();
        }
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUserID();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}