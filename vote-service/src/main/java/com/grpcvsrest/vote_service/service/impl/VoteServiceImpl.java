package com.grpcvsrest.vote_service.service.impl;

import com.grpcvsrest.vote_service.dto.VoteCreateRequest;
import com.grpcvsrest.vote_service.dto.VoteResponse;
import com.grpcvsrest.vote_service.entity.Vote;
import com.grpcvsrest.vote_service.grpc.GetVotesByPollIdRequest;
import com.grpcvsrest.vote_service.grpc.GetVotesByPollIdResponse;
import com.grpcvsrest.vote_service.repository.VoteRepository;
import com.grpcvsrest.vote_service.service.VoteService;
import com.grpcvsrest.vote_service.util.VoteMapper;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    @Override
    public List<VoteResponse> getVotesByPollId(UUID pollId) {
        List<VoteResponse> votesData =   voteRepository.findByPollId(pollId).stream()
                .map(VoteMapper::toResponse)
                .toList();

        // Simulating high-payload data for benchmarking REST vs gRPC performance.
        // This inflates the response by repeating the retrieved votes 200 times.
        return Collections.nCopies(200, votesData)
                .stream()
                .flatMap(List::stream)
                .toList();
    }


}
