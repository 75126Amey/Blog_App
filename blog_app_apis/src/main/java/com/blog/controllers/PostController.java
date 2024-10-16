package com.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.dto.CategoryDTO;
import com.blog.dto.PostDTO;
import com.blog.dto.PostResponse;
import com.blog.response.ApiResponseToken;
import com.blog.services.FileService;
import com.blog.services.PostService;



@RequestMapping("/api/")
@RestController
public class PostController {
	
	@Autowired
	PostService postService;
	
	@Autowired
	FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	//CreatePost
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	ResponseEntity<PostDTO> createPost(@RequestBody PostDTO post,@PathVariable Integer userId,@PathVariable Integer categoryId){
		PostDTO createdPost = postService.createPost(post, userId, categoryId);
		return new ResponseEntity<PostDTO>(createdPost,HttpStatus.CREATED);
	}
	
	//GetByUser
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDTO>> getPostByUser(@PathVariable Integer userId){
		List<PostDTO> posts = postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDTO>>(posts,HttpStatus.OK);
	}
	
	//GetByCategory
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDTO>> getPostByCategory(@PathVariable Integer categoryId){
		List<PostDTO> posts = postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDTO>>(posts,HttpStatus.OK);
	}
	
	
	//GetALL
	@GetMapping("/posts/getAllPosts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value="pageNumber",defaultValue = "0",required = false)Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue = "10",required = false)Integer pageSize,
			@RequestParam(value="sortBy",defaultValue = "postId",required = false)String sortBy,
			@RequestParam(value="sortDir",defaultValue = "asc",required = false)String sortDir
			){
		PostResponse allPost = postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(allPost,HttpStatus.OK);
	}
	
	//GetById
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable Integer postId){
		PostDTO post = postService.getPostById(postId);
		return new ResponseEntity<PostDTO>(post,HttpStatus.OK);
	}
	
	//Delete
	@DeleteMapping("/{postId}")
	public ResponseEntity<ApiResponseToken> deletePost(@PathVariable Integer postId){
		 postService.deletePost(postId);
		 ApiResponseToken response = new ApiResponseToken("success", null, "Post deleted successfully");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	//Update
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO,@PathVariable Integer postId){
		PostDTO updatePost =  postService.updatePost(postDTO,postId);
		return new ResponseEntity<PostDTO>(updatePost,HttpStatus.OK);
	}
	
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDTO>> searchByPostTitle(
			@PathVariable("keywords")String keywords){
		List<PostDTO> searchedPost = postService.searchPost(keywords);
		return new ResponseEntity<List<PostDTO>>(searchedPost,HttpStatus.OK);
		
	}
	
	
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDTO> uploadPostImage(
			@RequestParam("image")MultipartFile image,
			@PathVariable Integer postId
			) throws IOException{
		PostDTO postdto = postService.getPostById(postId);
		
		String fileName = fileService.uploadImage(path, image);
		postdto.setImageName(fileName);
		PostDTO updatePost = postService.updatePost(postdto, postId);
		return new ResponseEntity<PostDTO>(updatePost,HttpStatus.OK);
	}
	
	@GetMapping(value="post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName")String imageName,
			HttpServletResponse response) throws IOException {
		InputStream resource = fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
	}
	

}
