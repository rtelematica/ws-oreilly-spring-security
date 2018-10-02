package com.oreilly.security.domain.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oreilly.security.domain.entities.AutoUser;

public interface AutoUserRepository extends JpaRepository<AutoUser, Long> {

	public Optional<AutoUser> findByUsername(String username);
}
