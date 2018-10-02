package com.oreilly.security;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SuppressWarnings("deprecation")
public class PasswordEncoderMain {

	public static void main(String[] args) {

		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);

		String encodedPassword = encoder.encodePassword("pau", null);

		System.out.println(encodedPassword);

		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

		encodedPassword = bcrypt.encode("pau");

		System.out.println(encodedPassword);
	}

}
