package com.optum.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.optum.model.User;

@Repository
public interface LogInRepository extends JpaRepository<User, Long> {
	
	public Optional<User> findByUserName(String userName);

}
