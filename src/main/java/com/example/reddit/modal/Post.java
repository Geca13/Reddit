package com.example.reddit.modal;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.sun.istack.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Post Name can not be empty or Null")
	private String postName;
	
	@Nullable
	private String url;
	
	@Nullable
	@Lob
	private String description;
	
	private Integer voteCount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	private Instant createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Subreddit subreddit;

}
