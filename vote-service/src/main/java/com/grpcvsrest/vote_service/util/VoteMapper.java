package com.grpcvsrest.vote_service.util;

import com.grpcvsrest.vote_service.dto.VoteResponse;
import com.grpcvsrest.vote_service.entity.Vote;
import com.grpcvsrest.vote_service.dto.VoteCreateRequest;

public class VoteMapper {

    public static Vote toCreateEntity(VoteCreateRequest voteCreateRequest){
        Vote vote = new Vote();
        vote.setUserId(voteCreateRequest.getUserId());
        vote.setPollId(voteCreateRequest.getPollId());
        vote.setPollOptionId(voteCreateRequest.getPollOptionId());
        return vote;
    }

    public static VoteResponse toResponse (Vote vote){
        return VoteResponse.builder()
                .id(vote.getUuid())
                .userId(vote.getUserId())
                .pollId(vote.getPollId())
                .pollOptionId(vote.getPollOptionId())
                .createdAt(vote.getCreatedAt())
                .updatedAt(vote.getUpdatedAt())
                .build();
    }
}