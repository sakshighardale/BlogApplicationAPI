package com.blog.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="commnets")
@Getter
@Setter
public class Comment {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String content;
	
	@ManyToOne
	private Post post;
}
