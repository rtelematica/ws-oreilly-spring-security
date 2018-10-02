package com.oreilly.security.authenticationfilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.oreilly.security.authenticationobject.CustomAuthenticationToken;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	public static final String MAKE_KEY = "make";

	private String makeParameter = MAKE_KEY;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		String username = super.obtainUsername(request);
		String password = super.obtainPassword(request);
		String make = this.obtainMake(request);

		CustomAuthenticationToken token = new CustomAuthenticationToken(username, password, make);

		super.setDetails(request, token);

		// return super.attemptAuthentication(request, response);

		return this.getAuthenticationManager().authenticate(token);
	}

	protected String obtainMake(HttpServletRequest request) {
		return request.getParameter(makeParameter);
	}

}
