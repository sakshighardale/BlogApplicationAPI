package com.blog.payloads;

import java.util.List;

import com.blog.entities.Category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CategoryResponse {
	private List<CategoryDto> content;
	private int pageNum;
	private int pageSize;
	private long totalElement;
	private int totalPage;
	private boolean lastPage;

}
