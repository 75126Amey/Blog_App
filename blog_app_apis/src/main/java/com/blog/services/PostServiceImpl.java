package com.blog.services;

import java.util.Date;
import java.util.List;import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.dto.PostDTO;
import com.blog.dto.PostResponse;
import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.respositories.CategoryRepo;
import com.blog.respositories.PostRepo;
import com.blog.respositories.UserRepo;

@Service
@Transactional
public class PostServiceImpl implements PostService {
	
	@Autowired
	PostRepo postRepo;
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Override
	public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {
		User user = userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User Not Found"));
		Category cat = categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category Not Found"));
		Post post = mapper.map(postDTO, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(cat);
		Post newPost = postRepo.save(post);
		return mapper.map(newPost, PostDTO.class);
	}
	@Override
	public PostDTO updatePost(PostDTO postDTO, Integer postId) {
		Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post Not Found"));
		post.setTitle(postDTO.getTitle());
		post.setContent(postDTO.getContent());
		post.setImageName(postDTO.getImageName());
		Post updated = postRepo.save(post);
		return mapper.map(updated, PostDTO.class);
	}

	@Override
	public void deletePost(Integer postId) {
	Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post Not Found"));
	postRepo.delete(post);
		
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc"))
		{
			sort = Sort.by(sortBy).ascending();
		}else {
			sort = Sort.by(sortBy).descending();
		}
		Pageable p = PageRequest.of(pageNumber, pageSize,sort);
		Page<Post>pagePost = postRepo.findAll(p);
		List<Post>allPosts = pagePost.getContent();
		List<PostDTO> posts = allPosts.stream().map((post)->mapper.map(post, PostDTO.class)).collect(Collectors.toList());
		PostResponse postResponse= new PostResponse();
		postResponse.setContent(posts);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDTO getPostById(Integer postId) {
		Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post Not Found"));
		return mapper.map(post, PostDTO.class);
	}

	@Override
	public List<PostDTO> getPostsByCategory(Integer categoryId) {
		Category cat= categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category Not Found")); 
		List<Post> posts = postRepo.findByCategory(cat);
		List<PostDTO> postDTOs = posts.stream().map((post)->mapper.map(post,PostDTO.class)).collect(Collectors.toList());
		return postDTOs;
	}

	@Override
	public List<PostDTO> getPostsByUser(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found")); 
		List<Post> posts = postRepo.findByUser(user);
		List<PostDTO> postDTOs = posts.stream().map((post)->mapper.map(post, PostDTO.class)).collect(Collectors.toList());
		return postDTOs;
	}

	
	//Search BY Title
	@Override
	public List<PostDTO> searchPost(String keyword) {
		List<Post> posts = postRepo.findByTitleContaining("%"+keyword+"%");
		List<PostDTO> postDTO =  posts.stream().map(post->mapper.map(post, PostDTO.class)).collect(Collectors.toList());
		return postDTO;
	}

	

}
