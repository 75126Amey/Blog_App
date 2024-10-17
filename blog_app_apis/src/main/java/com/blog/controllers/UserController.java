package com.blog.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.dto.UserDTO;
import com.blog.response.ApiResponseSuccess;
import com.blog.services.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {
		
	@Autowired
	private UserService userService;
		
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@Valid @RequestBody UserDTO userDto){
		String createdUser = userService.createUser(userDto);
		return new ResponseEntity<>(createdUser,HttpStatus.CREATED);
		
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO,@PathVariable Integer userId){
		UserDTO updatedUser = userService.updateUser(userDTO, userId);
		return ResponseEntity.ok(updatedUser);
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deletUser(@PathVariable Integer userId){
		userService.deleteUser(userId);
		ApiResponseSuccess<String> response = new ApiResponseSuccess<>();
		response.setData("User deleted successfully");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getById(@PathVariable Integer userId){
		UserDTO getByid = userService.getUserByID(userId);
		return ResponseEntity.ok(getByid);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<UserDTO>> getAllUsers(){
	return ResponseEntity.ok(userService.getAllUsers());
	}
	
}
