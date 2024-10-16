package com.blog.services;

import java.util.List;

import com.blog.dto.PostDTO;
import com.blog.dto.PostResponse;
import com.blog.entities.Post;

public interface PostService {

	PostDTO createPost(PostDTO postDTO,Integer userId,Integer categoryId);
	
	PostDTO updatePost(PostDTO postDTO,Integer postId);
	
	void deletePost(Integer postId);
	
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	PostDTO getPostById(Integer postId);
	
	List<PostDTO> getPostsByCategory(Integer categoryId);
	
	List<PostDTO> getPostsByUser(Integer userId);
	
	List<PostDTO> searchPost(String keyword);
	
	
}
