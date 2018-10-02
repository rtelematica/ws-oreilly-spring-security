package com.oreilly.security;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@SuppressWarnings("deprecation")
public class PasswordEncoderMain {

	public static void main(String[] args) {

		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);

		String encodedPassword = encoder.encodePassword("pau", null);

		System.out.println("sha-256 (not recommended): "+encodedPassword);

		StandardPasswordEncoder standardEncoder = new StandardPasswordEncoder();

		encodedPassword = standardEncoder.encode("pau");

		System.out.println("standard sha-256 (salted but not recommended): "+encodedPassword);

		System.out.println("=======");

		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

		encodedPassword = bcrypt.encode("pau");

		System.out.println("bcrypt (recommended and salted): "+encodedPassword);

		Pbkdf2PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder();

		encodedPassword = pbkdf2Encoder.encode("pau");

		System.out.println("pbkdf2 (recommended and salted): "+encodedPassword);
	}

}
