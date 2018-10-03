package com.oreilly.security.authenticationprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.oreilly.security.userdetails.CustomUserDetailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private CustomUserDetailService customUserDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.info("starting authentication process");

		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

		String username = token.getName();
		String password = (String) token.getCredentials();

		UserDetails user = customUserDetailsService.loadUserByUsername(username);

		if (!user.getPassword().trim().equalsIgnoreCase(password)) {
			
			throw new BadCredentialsException("Invalid credentials");
		}
		
		return new UsernamePasswordAuthenticationToken(username, user.getPassword(), user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.equals(authentication);
	}

}
