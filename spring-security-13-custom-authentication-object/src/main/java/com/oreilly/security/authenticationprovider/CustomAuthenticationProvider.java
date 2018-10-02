package com.oreilly.security.authenticationprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.oreilly.security.authenticationobject.CustomAuthenticationToken;
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

		CustomAuthenticationToken token = (CustomAuthenticationToken) authentication;

		String username = token.getName();
		String password = (String) token.getCredentials();
		String make = token.getMake();

		UserDetails user = customUserDetailsService.loadUserByUsername(username);

		if (!user.getPassword().trim().equalsIgnoreCase(password) || !make.equalsIgnoreCase("BMW")) {

			throw new BadCredentialsException("Invalid credentials");
		}

		// return new UsernamePasswordAuthenticationToken(username, user.getPassword(),
		// user.getAuthorities());
		return new CustomAuthenticationToken(user, user.getPassword(), user.getAuthorities(), make);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// return UsernamePasswordAuthenticationToken.class.equals(authentication);
		return CustomAuthenticationToken.class.equals(authentication);
	}

}
