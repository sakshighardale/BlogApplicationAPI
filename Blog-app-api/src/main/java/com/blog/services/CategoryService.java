package com.blog.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blog.payloads.CategoryDto;
import com.blog.payloads.CategoryResponse;
public interface CategoryService {

	//create
	CategoryDto createCategory(CategoryDto categoryDto);
	
	//update
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	
	//delete
	public void deleteCategory(Integer categoryId);
	
	//select all
	public CategoryDto getSingleCategory(Integer categoryId);
	
	//select single
	public CategoryResponse getAllCategory(Integer pageNum, Integer pageSize,String sortBy,String sortDir);
}
