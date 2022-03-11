package com.pi.login.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pi.login.model.User;

import lombok.Getter;


@Getter
public class PrincipalDetail implements UserDetails{

	private User user;

	public PrincipalDetail(User user) {
		this.user=user;
	}

	@Override
	public String getPassword() {
		
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	} 
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 Collection<GrantedAuthority> collectors= new ArrayList<>();
			/*
			 * collectors.add(new GrantedAuthority() {
			 * 
			 * @Override public String getAuthority() { // TODO Auto-generated method stub
			 * return "ROLE_"+user.getRole();//ROLE_USER 약속
			 * 
			 * } });
			 */
		 collectors.add(()->{return "ROLE_"+user.getRole();});
		return collectors;
	}
	
}
