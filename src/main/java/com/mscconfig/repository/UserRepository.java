package com.mscconfig.repository;

/**
 * User: Vladimir
 * Date: 29.07.13
 * Time: 14:01
 * Please describe this stuff
 */
import com.mscconfig.entities.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
}
