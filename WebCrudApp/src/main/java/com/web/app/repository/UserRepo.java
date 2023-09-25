package com.web.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.app.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	
	
	User findByEmail(String email);

	List<User> findAll();
	
    
}
