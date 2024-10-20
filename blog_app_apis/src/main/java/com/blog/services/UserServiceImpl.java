package com.blog.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.dto.UserDTO;
import com.blog.entities.Role;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.respositories.RoleRepo;
import com.blog.respositories.UserRepo;

@Service 
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	RoleRepo roleRepo;
	@Override
	public String createUser(UserDTO user) {
//		user.setPassword(encoder.encode(user.getPassword()));
		User user1 = mapper.map(user,User.class);
		userRepo.save(user1);
		return "Added Successfully";
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User Not Found with Id : "+userId));
		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setAbout(userDTO.getAbout());
		User updatedUser = userRepo.save(user);
		return mapper.map(updatedUser, UserDTO.class);
	}

	@Override
	public UserDTO getUserByID(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User Not Found with Id : "+userId));
		return mapper.map(user, UserDTO.class);
	}

	@Override
	public List<UserDTO> getAllUsers() {
	List<User> users = userRepo.findAll();
	List<UserDTO> userDTOs = users.stream().map(user->mapper.map(user,UserDTO.class)).collect(Collectors.toList());
		return userDTOs;
	}

	@Override
	public void deleteUser(Integer userId) {
	User user =  userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found with Id : "+userId));
		userRepo.delete(user);
		
	}

	@Override
	public UserDTO registerUser(UserDTO userDTO) {
		User user = mapper.map(userDTO, User.class);
		
		//Encoded Password
		user.setPassword(encoder.encode(user.getPassword()));
		Role role = roleRepo.findById(502).get();
		user.getRoles().add(role);
		User newUser = userRepo.save(user);
		return mapper.map(newUser, UserDTO.class);
	}

}
