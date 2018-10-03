package com.oreilly.security.ldap.mapper;

import java.util.Collection;

import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.stereotype.Component;

import com.oreilly.security.domain.entities.AutoUser;

@Component("customUserDetailsLdapContextMapper")
public class CustomUserDetailsLdapContextMapper implements UserDetailsContextMapper {

	@Override
	public UserDetails mapUserFromContext(DirContextOperations ctx, String username, 
										  Collection<? extends GrantedAuthority> authorities) {
		AutoUser autoUser = new AutoUser();
		
		autoUser.setFirstName(ctx.getStringAttribute("givenName"));
		autoUser.setLastName(ctx.getStringAttribute("sn"));
		autoUser.setEmail(ctx.getStringAttribute("mail"));
		autoUser.setUsername(username);
		autoUser.setAuthorities(authorities);
		return autoUser;
	}

	@Override
	public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
		
		AutoUser autoUser = (AutoUser)user;
		
		ctx.setAttributeValue("givenName", autoUser.getFirstName());
		ctx.setAttributeValue("sn", autoUser.getLastName());
		ctx.setAttributeValue("mail", autoUser.getEmail());
		ctx.setAttributeValue("uid", autoUser.getUsername());
	}

}
