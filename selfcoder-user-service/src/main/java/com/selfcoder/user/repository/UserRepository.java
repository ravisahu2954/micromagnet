package com.selfcoder.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.selfcoder.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);

	void deleteAllByIdIn(List<Long> ids);

	Optional<User> findByUsername(String username);

}
