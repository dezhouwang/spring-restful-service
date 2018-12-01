package com.simple.spring.simpleservices;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	boolean existsByName(String name);

	// query user by salary
	List<User> findBySalaryBetween(Double min, Double max);

}