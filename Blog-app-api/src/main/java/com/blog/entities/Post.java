package com.blog.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.annotations.ManyToAny;

import com.blog.payloads.CommentDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "post")
@Getter
@Setter
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer postId;

	@Column(name = "post_title", length = 100, nullable = false)
	private String title;

	@Column(length = 15000)
	private String content;
	private String imageName;
	private Date addedDate;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "user")
	private User user;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();

}
