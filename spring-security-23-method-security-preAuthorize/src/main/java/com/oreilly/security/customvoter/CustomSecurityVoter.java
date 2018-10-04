package com.oreilly.security.customvoter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("customSecurityVoter")
public class CustomSecurityVoter {

	public boolean isValidUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		return authentication != null && authentication.getAuthorities()
				.contains(AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER").get(0));
	}
}
