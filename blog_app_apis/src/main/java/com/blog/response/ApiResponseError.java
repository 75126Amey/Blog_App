package com.blog.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter 
@Setter
public class ApiResponseError<T> {
	private String status = "error";
	private T data;

}