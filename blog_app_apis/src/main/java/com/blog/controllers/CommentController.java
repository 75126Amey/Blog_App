package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.dto.CommentDTO; 
import com.blog.response.ApiResponseToken;
import com.blog.services.CommentService;

@RequestMapping("/api/")
@RestController
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO comment,@PathVariable Integer postId){
		CommentDTO createdComment = commentService.createComment(comment, postId);
		return new ResponseEntity<CommentDTO>(createdComment,HttpStatus.CREATED);
	}
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponseToken> deleteComment(@PathVariable Integer commentId){
		ApiResponseToken response = new ApiResponseToken("success",null,"Comment Deleted Successfully"); 
		commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponseToken>(response,HttpStatus.OK);
		
	}

}
