package com.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PostResponse {
		private List<PostDto> content;
		private int pageNum;
		private int pageSize;
		private long totalElements;
		private int totalPages;
		private boolean lastPage;
}
