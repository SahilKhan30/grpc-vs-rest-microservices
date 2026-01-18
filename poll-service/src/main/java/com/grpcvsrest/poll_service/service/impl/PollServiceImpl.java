package com.grpcvsrest.poll_service.service.impl;

import com.grpcvsrest.poll_service.clients.UserRestClient;
import com.grpcvsrest.poll_service.clients.VoteRestClient;
import com.grpcvsrest.poll_service.dto.request.PollCreateRequest;
import com.grpcvsrest.poll_service.dto.response.PollInfoResponse;
import com.grpcvsrest.poll_service.dto.response.PollResponse;
import com.grpcvsrest.poll_service.dto.request.PollUpdateRequest;
import com.grpcvsrest.poll_service.dto.response.UserResponse;
import com.grpcvsrest.poll_service.dto.response.VoteResponse;
import com.grpcvsrest.poll_service.entity.Poll;
import com.grpcvsrest.poll_service.entity.PollOption;
import com.grpcvsrest.poll_service.repository.PollRepository;
import com.grpcvsrest.poll_service.service.PollOptionService;
import com.grpcvsrest.poll_service.service.PollService;
import com.grpcvsrest.poll_service.util.PollInfoMapper;
import com.grpcvsrest.poll_service.util.PollMapper;
import com.grpcvsrest.poll_service.util.UserGrpcResponseMapper;
import com.grpcvsrest.poll_service.util.VoteGrpcResponseMapper;
import com.grpcvsrest.user_service.grpc.GetUserByIdRequest;
import com.grpcvsrest.user_service.grpc.UserServiceGrpc;
import com.grpcvsrest.vote_service.grpc.GetVotesByPollIdRequest;
import com.grpcvsrest.vote_service.grpc.VoteServiceGrpc;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PollServiceImpl implements PollService {

    private final PollRepository pollRepository;
    private final PollOptionService pollOptionService;
    private final UserRestClient userRestClient;
    private final VoteRestClient voteRestClient;
    private final UserServiceGrpc.UserServiceBlockingStub userStub;
    private final VoteServiceGrpc.VoteServiceBlockingStub voteStub;

    @Override
    public PollResponse createPoll(PollCreateRequest pollCreateRequest) {
        Poll poll = PollMapper.toCreateEntity(pollCreateRequest);
        Poll savedPoll = pollRepository.save(poll);
        return PollMapper.toResponse(savedPoll);
    }

    @Override
    public PollResponse getPollById(UUID id) {
        Poll poll = pollRepository.findByUuid(id)
                .orElseThrow(() -> new RuntimeException("Poll not found"));
        return PollMapper.toResponse(poll);
    }

    @Override
    public List<PollResponse> getAllPolls() {
        return pollRepository.findAll()
                .stream()
                .map(PollMapper::toResponse)
                .toList();
    }

    @Override
    public PollResponse updatePoll(UUID id, PollUpdateRequest pollUpdateRequest) {
        Poll poll = pollRepository.findByUuid(id).orElseThrow(() -> new RuntimeException("Poll not found"));
        PollMapper.toUpdateEntity(poll, pollUpdateRequest);
        List<PollOption> pollOptions = pollOptionService.updatePollOptions(poll, pollUpdateRequest);
        poll.setPollOptions(pollOptions);
        Poll savedUser = pollRepository.save(poll);
        return PollMapper.toResponse(savedUser);
    }

    @Override
    public void deletePoll(UUID id) {
        Poll poll = pollRepository.findByUuid(id)
                .orElseThrow(() -> new RuntimeException("Poll not found"));
        pollRepository.delete(poll);
    }

    @Override
    public List<PollInfoResponse> getPollInfoRest() {
        return pollRepository.findAll(PageRequest.of(0, 20))
                .stream()
                .map(poll -> {
                    PollResponse pollResponse = PollMapper.toResponse(poll);
                    UserResponse userResponse = userRestClient.getUserById(pollResponse.getUserId());
                    List<VoteResponse> voteResponseList = voteRestClient.getVoteByPollId(pollResponse.getId());
                    return PollInfoMapper.toResponse(pollResponse, userResponse, voteResponseList);
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PollInfoResponse> getPollInfoGrpc() {
        return pollRepository.findAll(PageRequest.of(0, 20))
                .stream()
                .map(poll -> {
                    PollResponse pollResponse = PollMapper.toResponse(poll);
                    GetUserByIdRequest request = GetUserByIdRequest.newBuilder()
                            .setId(String.valueOf(pollResponse.getUserId()))
                            .build();
                    GetVotesByPollIdRequest votesByPollIdRequest = GetVotesByPollIdRequest.newBuilder()
                            .setPollId(String.valueOf(pollResponse.getId()))
                            .build();
                    UserResponse userResponse = UserGrpcResponseMapper.toUserResponse(userStub.getUserById(request));
                    List<VoteResponse> voteResponseList = voteStub.getVotesByPollId(votesByPollIdRequest).getVotesList()
                            .stream()
                            .map(VoteGrpcResponseMapper::toVoteResponse)
                            .toList();
                    return PollInfoMapper.toResponse(pollResponse, userResponse, voteResponseList);
                })
                .toList();
    }

}
