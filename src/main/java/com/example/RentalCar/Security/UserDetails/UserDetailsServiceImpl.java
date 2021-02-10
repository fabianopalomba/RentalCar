package com.example.RentalCar.Security.UserDetails;

import com.example.RentalCar.Entity.User;
import com.example.RentalCar.Service.RoleService;
import com.example.RentalCar.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (userService.existsByUsername(username)) {
			User user = userService.getByUsername(username);
			return UserPrinciple.build(user);
		}
		else throw new UsernameNotFoundException("User Not Found with -> username or email : " + username);
	}
}
