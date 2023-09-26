package com.cesarschool.project.emailsender.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.cesarschool.project.emailsender.spring.entities.User;



public interface UserRepository extends JpaRepository<User, String>{
	
	User findByEmail(@Param("email") String email);
	
	User findByName(@Param("name") String name);
	
	List<User> findByOrganization(@Param("organization") String organization);

	List<User> findByfunc(@Param("func") String func);
	
}
