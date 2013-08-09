package com.mscconfig.services.auth.impl;

/**
 * User: Vladimir
 * Date: 29.07.13
 * Time: 14:02
 * Сервис (Реалм) вытягивает из БД пользователя смотрит права доступа
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mscconfig.repository.auth.UserRepository;
import com.mscconfig.services.auth.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A custom {@link org.springframework.security.core.userdetails.UserDetailsService} where user information
 * is retrieved from a JPA repository
 */
@Service("customUserDetailsService")
@Transactional(readOnly = true)
public class CustomUserDetailsServiceImpl implements UserDetailsService {


	@Qualifier("userRepository")
	@Autowired
	private UserRepository userRepository;


	/**
	 * Returns a populated {@link org.springframework.security.core.userdetails.UserDetails} object.
	 * The username is first retrieved from the database and then mapped to
	 * a {@link org.springframework.security.core.userdetails.UserDetails} object.
	 */
	public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			com.mscconfig.entities.auth.User user = userRepository.findByUsername(username);
			if (user ==null) throw new RuntimeException("User not exist in DB : user ="+username);
			boolean enabled = true;
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;
			CustomUserDetails userDetails = new CustomUserDetails(
					user.getUsername(),
					user.getPassword().toLowerCase(),
					enabled,
					accountNonExpired,
					credentialsNonExpired,
					accountNonLocked,
					getAuthorities(user.getRole().getRole()));
			userDetails.setFirstName(user.getFirstName());
			userDetails.setLastName(user.getLastName());
			return userDetails;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Retrieves a collection of {@link org.springframework.security.core.GrantedAuthority} based on a numerical role
	 * @param role the numerical role
	 * @return a collection of {@link org.springframework.security.core.GrantedAuthority
	 */
	public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
		List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
		return authList;
	}

	/**
	 * Converts a numerical role to an equivalent list of roles
	 * @param role the numerical role
	 * @return list of roles as as a list of {@link String}
	 */
	public List<String> getRoles(Integer role) {
		List<String> roles = new ArrayList<String>();

		if (role.intValue() == 1) {
			roles.add("ROLE_USER");
			roles.add("ROLE_ADMIN");

		} else if (role.intValue() == 2) {
			roles.add("ROLE_USER");
		}

		return roles;
	}

	/**
	 * Wraps {@link String} roles to {@link org.springframework.security.core.authority.SimpleGrantedAuthority} objects
	 * @param roles {@link String} of roles
	 * @return list of granted authorities
	 */
	public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

}
