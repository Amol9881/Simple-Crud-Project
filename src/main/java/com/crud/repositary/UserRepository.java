package com.crud.repositary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.model.User;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByUsername(String username);
	
	boolean existsByUsername(String username);

}
