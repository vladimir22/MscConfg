package com.mscconfig.repository.auth;

/**
 * User: Vladimir
 * Date: 29.07.13
 * Time: 14:01
 * JPA Repo работает с сущностями auth.* Для вытяшивания юзеров из БД
 */
import com.mscconfig.entities.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
}
