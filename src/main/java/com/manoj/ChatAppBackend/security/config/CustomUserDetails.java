package com.manoj.ChatAppBackend.security.config;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.manoj.ChatAppBackend.entity.User;
import com.manoj.ChatAppBackend.entity.dao.IUserDao;

@Component
public class CustomUserDetails implements UserDetailsService {
	
	@Autowired
	private IUserDao userDao;

	 @Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			User user =userDao.findByMobileNumber(username).orElseThrow(()-> new UsernameNotFoundException("User not exist") );
			return new UserDetails() {
				private static final long serialVersionUID = -5657765016120456484L;

				@Override
				public boolean isEnabled() {
					return user.isProfileStatus();
				}
				
				@Override
				public boolean isCredentialsNonExpired() {
					return true;
				}
				
				@Override
				public boolean isAccountNonLocked() {
					return true;
				}
				
				@Override
				public boolean isAccountNonExpired() {
					return true;
				}
				
				@Override
				public String getUsername() {
					return user.getMobileNumber();
				}
				
				@Override
				public String getPassword() {
					return null;
				}
				
				@Override
				public Collection<? extends GrantedAuthority> getAuthorities() {
					return null;
				}
			};
		}
	

}
