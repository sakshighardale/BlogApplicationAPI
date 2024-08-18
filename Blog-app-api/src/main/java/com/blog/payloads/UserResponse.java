package com.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserResponse {

	private List<UserDto> content;
	private int pageNum;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean lastPage;
}
