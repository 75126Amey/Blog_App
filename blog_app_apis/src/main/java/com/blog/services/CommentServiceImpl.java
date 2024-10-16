package com.blog.services;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dto.CommentDTO;
import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.respositories.CommentRepo;
import com.blog.respositories.PostRepo;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public CommentDTO createComment(CommentDTO commentDTO, Integer postId) {
		Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post Not Found"));
		Comment comment = mapper.map(commentDTO, Comment.class);
		comment.setPost(post);
		Comment savedComment = commentRepo.save(comment);
		return mapper.map(savedComment, CommentDTO.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
	Comment comm = commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment Not Found"));
	commentRepo.delete(comm);
	}

}
