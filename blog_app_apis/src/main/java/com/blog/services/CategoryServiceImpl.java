package com.blog.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dto.CategoryDTO;
import com.blog.entities.Category;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.respositories.CategoryRepo;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryRepo categoryRepo;
	@Autowired
	ModelMapper mapper;

	@Override
	public CategoryDTO createCatgeory(CategoryDTO categoryDTO) {
		Category createdCat = mapper.map(categoryDTO, Category.class);
		Category addedCat = categoryRepo.save(createdCat);
		return mapper.map(addedCat, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCatgeory(CategoryDTO categoryDTO, Integer categoryId) {
		Category cat = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category Not Found"));
		cat.setCategoryTitle(categoryDTO.getCategoryTitle());
		cat.setCategoryDescription(categoryDTO.getCategoryDescription());
		Category updatedCat = categoryRepo.save(cat); 
		return mapper.map(updatedCat, CategoryDTO.class);
	}

	@Override
	public void deletCatory(Integer cateoryId) {
	Category cat = categoryRepo.findById(cateoryId)
			.orElseThrow(()->new ResourceNotFoundException("Category Not Found"));
	categoryRepo.delete(cat);
	}

	@Override
	public CategoryDTO getCatgeory(Integer cateoryId) {
	Category cat = categoryRepo.findById(cateoryId)
			.orElseThrow(()-> new ResourceNotFoundException("Category Not Found"));
	
		return mapper.map(cat, CategoryDTO.class);
	}

	@Override
	public List<CategoryDTO> getCatgeories() {
		List<Category> categories = categoryRepo.findAll();
		List<CategoryDTO> collected = categories.stream().map((cat)->mapper.map(cat, CategoryDTO.class)).collect(Collectors.toList());
		return collected;
	}

}
