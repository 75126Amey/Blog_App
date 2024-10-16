package com.blog.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.respositories.UserRepo;
@Service
@Transactional
public class CustomUserDetailService implements UserDetailsService {
	@Autowired
	UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//loading user from db
		User user = userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
		return  user;
	}

}
