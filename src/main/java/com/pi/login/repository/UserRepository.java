package com.pi.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pi.login.model.User;



public interface UserRepository extends JpaRepository<User, Integer>{
	
	Optional<User>findByUsername(String username);


}



