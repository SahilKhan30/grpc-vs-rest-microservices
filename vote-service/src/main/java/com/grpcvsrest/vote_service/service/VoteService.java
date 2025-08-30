package com.grpcvsrest.vote_service.service;

import com.grpcvsrest.vote_service.dto.VoteCreateRequest;
import com.grpcvsrest.vote_service.dto.VoteResponse;

import java.util.List;
import java.util.UUID;

public interface VoteService {
    VoteResponse createVote(VoteCreateRequest voteCreateRequest);
    VoteResponse getVoteById(UUID id);
    List<VoteResponse> getAllVotes();
    void deleteVote(UUID id);
}
