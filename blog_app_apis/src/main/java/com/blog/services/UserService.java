package com.blog.services;

import java.util.List;

import com.blog.dto.UserDTO;


public interface UserService {
	UserDTO registerUser(UserDTO userDTO);
	public String createUser(UserDTO user);
	UserDTO updateUser(UserDTO user,Integer userId);
	UserDTO getUserByID(Integer userId);
	List<UserDTO> getAllUsers();
	void deleteUser(Integer userId);
}
