package com.example.reddit.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reddit.dto.VoteDto;
import com.example.reddit.exceptions.PostNotFoundException;
import com.example.reddit.exceptions.VoteException;
import com.example.reddit.modal.Post;
import com.example.reddit.modal.Vote;
import com.example.reddit.repository.PostRepository;
import com.example.reddit.repository.VoteRepository;
import static com.example.reddit.modal.VoteType.UPVOTE;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VoteService {
	
	private final VoteRepository voteRepository;
	private final PostRepository postRepository;
	private final AuthService authService;
	
	@Transactional
	public void vote(VoteDto voteDto) {
		Post post = postRepository.findById(voteDto.getPostId())
				.orElseThrow(() -> new PostNotFoundException("POST WASN'T FOUND"));
		
		Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
		if(voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
			throw new VoteException("You have already voted on this post");
		}
			if(UPVOTE.equals(voteDto.getVoteType())) {
				post.setVoteCount(post.getVoteCount()+1);
			} else {
				post.setVoteCount(post.getVoteCount()-1);
			}
			voteRepository.save(mapToVote(voteDto,post));
			postRepository.save(post);
		}

	private Vote mapToVote(VoteDto voteDto, Post post) {
		
		return Vote.builder()
				.voteType(voteDto.getVoteType())
				.post(post).user(authService.getCurrentUser()).build();
	}
	

	

}
