package com.grpcvsrest.vote_service.service.impl;

import com.grpcvsrest.vote_service.dto.VoteCreateRequest;
import com.grpcvsrest.vote_service.dto.VoteResponse;
import com.grpcvsrest.vote_service.entity.Vote;
import com.grpcvsrest.vote_service.repository.VoteRepository;
import com.grpcvsrest.vote_service.service.VoteService;
import com.grpcvsrest.vote_service.util.VoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;

    @Override
    public VoteResponse createVote(VoteCreateRequest voteCreateRequest) {
        Vote vote = VoteMapper.toCreateEntity(voteCreateRequest);
        Vote savedVote = voteRepository.save(vote);
        return VoteMapper.toResponse(savedVote);
    }

    @Override
    public VoteResponse getVoteById(UUID id) {
        Vote vote = voteRepository.findByUuid(id)
                .orElseThrow(()->new RuntimeException("Vote not found"));
        return VoteMapper.toResponse(vote);
    }

    @Override
    public List<VoteResponse> getAllVotes() {
        return voteRepository.findAll().stream()
                .map(VoteMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteVote(UUID uuid){
        Vote vote = voteRepository.findByUuid(uuid)
                .orElseThrow(()->new RuntimeException("Vote not found"));
        voteRepository.delete(vote);
    }
}
