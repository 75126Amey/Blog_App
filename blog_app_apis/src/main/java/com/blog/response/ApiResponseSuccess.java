package com.blog.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter 
@Setter
public class ApiResponseSuccess<T> {
	private String status = "success";
	private T data;

}
