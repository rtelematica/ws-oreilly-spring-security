package com.oreilly.security.userdetails;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.oreilly.security.domain.entities.AutoUser;
import com.oreilly.security.domain.repositories.AutoUserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("customUserDetailService")
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private AutoUserRepository autoUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<AutoUser> autoUserOptional = autoUserRepository.findByUsername(username);

		if (autoUserOptional.isPresent()) {

			AutoUser autoUser = autoUserOptional.get();

			// Return fully user authenticated
			@SuppressWarnings("unused")
			User user = new User(autoUser.getUsername(), autoUser.getPassword(),
					AuthorityUtils.createAuthorityList(autoUser.getRole()));

			// No need to verify credentials.
			log.info("Returning user logged: {}", autoUser);
			//return user
			return autoUser;
		}

		log.info("User not exists");

		// Return UsernameNotFound
		throw new UsernameNotFoundException("Username " + username + " not found");
	}

}
