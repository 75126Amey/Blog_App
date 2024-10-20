package com.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.dto.CategoryDTO;
import com.blog.response.ApiResponseToken;
import com.blog.services.CategoryService;



@RestController
@RequestMapping("/api/category")
public class CategoryController {
	@Autowired
	CategoryService catService;
	
	//Create
	@PostMapping("/create")
	public ResponseEntity<CategoryDTO> createCategory(@Valid@RequestBody CategoryDTO catDTO){
		CategoryDTO createCat = catService.createCatgeory(catDTO);
		return new ResponseEntity<CategoryDTO>(createCat,HttpStatus.CREATED);
	}
	
	//Update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid@RequestBody CategoryDTO catDTO,@PathVariable Integer categoryId){
		CategoryDTO cat = catService.updateCatgeory(catDTO,categoryId);
		return new ResponseEntity<CategoryDTO>(cat,HttpStatus.OK);
	}
	
	//Delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponseToken> deleteCategory(@PathVariable Integer categoryId) {
	    catService.deletCatory(categoryId);
	    ApiResponseToken response = new ApiResponseToken("success", null, "Category deleted successfully");
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> getCategory(@PathVariable Integer categoryId){
		 CategoryDTO catDTO = catService.getCatgeory(categoryId);
		 return new ResponseEntity<CategoryDTO>(catDTO, HttpStatus.OK);
	}
	
	@GetMapping("/getAllCategories")
	public ResponseEntity<List<CategoryDTO>> getAllCategories(){
		 List<CategoryDTO> catDTO = catService.getCatgeories();
		 return ResponseEntity.ok(catDTO);
	}
	
	
}
