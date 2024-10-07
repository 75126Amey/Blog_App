package com.blog.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CategoryDTO {
	
	private Integer categoryId;
	
	@NotBlank
	@Size(min = 4,message="Min size of Category Title should be 4")
	private String categoryTitle;
	
	@NotBlank
	@Size(min = 10,message="Min size of Category Description should be 10")
	private String categoryDescription;
}
