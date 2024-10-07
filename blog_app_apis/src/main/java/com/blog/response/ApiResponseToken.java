package com.blog.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ApiResponseToken {
	private String status;
	private String token;
	private String message; 
	
	public ApiResponseToken(String status, String token) {
        this.status = status;
        this.token = token;
    }
	
	public ApiResponseToken(String status, String token,String message) {
        this.status = status;
        this.token = token;
        this.message = message;
    }
}
