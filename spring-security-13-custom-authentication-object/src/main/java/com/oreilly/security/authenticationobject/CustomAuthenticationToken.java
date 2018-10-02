package com.oreilly.security.authenticationobject;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {

	private static final long serialVersionUID = 617296457148760391L;

	private @Getter String make;

	public CustomAuthenticationToken(String principal, String credentials, String make) {
		super(principal, credentials);
		this.make = make;
	}

	public CustomAuthenticationToken(UserDetails user, String credentials,
			Collection<? extends GrantedAuthority> authorities, String make) {
		super(user, credentials, authorities);
		this.make = make;
	}

}
