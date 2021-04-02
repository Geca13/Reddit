package com.example.reddit.dto;

import com.example.reddit.modal.VoteType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteDto {

	private VoteType voteType;
    private Long postId;

}
