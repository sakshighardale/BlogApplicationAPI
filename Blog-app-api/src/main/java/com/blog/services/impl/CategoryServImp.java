package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CategoryDto;
import com.blog.payloads.CategoryResponse;
import com.blog.repositories.CategoryRepo;
import com.blog.services.CategoryService;

@Service
public class CategoryServImp implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category savedCategory = this.categoryRepo.save(category);
		return this.modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		// TODO Auto-generated method stub
		Category cate = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		cate.setCategoryTitle(categoryDto.getCategoryTitle());
		cate.setCategoryDescription(categoryDto.getCategoryDescription());
		this.categoryRepo.save(cate);
		return this.modelMapper.map(cate, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDto getSingleCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Categoy Id", categoryId));

		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public CategoryResponse getAllCategory(Integer pageNum, Integer pageSize,String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		Sort sortingDir=sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNum, pageSize,sortingDir);
		Page<Category> pageCategory= this.categoryRepo.findAll(pageable);
		List<Category> categories=pageCategory.getContent();
		List<CategoryDto> categoryDtos = categories.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class))
				.collect(Collectors.toList());
		CategoryResponse categoryResp=new CategoryResponse();
		categoryResp.setContent(categoryDtos);
		categoryResp.setLastPage(pageCategory.isLast());
		categoryResp.setPageNum(pageCategory.getNumber());
		categoryResp.setPageSize(pageCategory.getSize());
		categoryResp.setTotalElement(pageCategory.getTotalElements());
		categoryResp.setTotalPage(pageCategory.getTotalPages());
		
		return categoryResp;
	}

}
