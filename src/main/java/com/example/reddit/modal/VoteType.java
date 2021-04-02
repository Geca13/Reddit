package com.example.reddit.modal;

import java.util.Arrays;

import com.example.reddit.exceptions.VoteException;
public enum VoteType {

	UPVOTE(1),
	DOWNVOTE(-1),
	;
	
	private int direction;
	
	VoteType(int direction){
		
	}
	
	public static VoteType lookup(Integer direction) {
        return Arrays.stream(VoteType.values())
                .filter(value -> value.getDirection().equals(direction))
                .findAny()
                .orElseThrow(() -> new VoteException("Vote not found"));
    }

    public Integer getDirection() {
        return direction;
    }
}
