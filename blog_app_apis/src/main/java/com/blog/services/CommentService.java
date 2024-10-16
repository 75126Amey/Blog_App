package com.blog.services;

import com.blog.dto.CommentDTO;

public interface CommentService {
	
	CommentDTO createComment(CommentDTO commentDTO,Integer postId);
	
	void deleteComment(Integer commentId);
}
