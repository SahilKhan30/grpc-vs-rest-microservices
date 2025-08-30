package com.grpcvsrest.poll_service.service.impl;

import com.grpcvsrest.poll_service.dto.PollCreateRequest;
import com.grpcvsrest.poll_service.dto.PollResponse;
import com.grpcvsrest.poll_service.dto.PollUpdateRequest;
import com.grpcvsrest.poll_service.entity.Poll;
import com.grpcvsrest.poll_service.entity.PollOption;
import com.grpcvsrest.poll_service.repository.PollRepository;
import com.grpcvsrest.poll_service.service.PollOptionService;
import com.grpcvsrest.poll_service.service.PollService;
import com.grpcvsrest.poll_service.util.PollMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PollServiceImpl implements PollService {

    private final PollRepository pollRepository;
    private final PollOptionService pollOptionService;

    @Override
    public PollResponse createPoll(PollCreateRequest pollCreateRequest) {
        Poll poll = PollMapper.toCreateEntity(pollCreateRequest);
        Poll savedPoll =  pollRepository.save(poll);
        return PollMapper.toResponse(savedPoll);
    }

    @Override
    public PollResponse getPollById(UUID id) {
        Poll poll = pollRepository.findByUuid(id)
                .orElseThrow(()->new RuntimeException("Poll not found"));
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
        Poll poll = pollRepository.findByUuid(id).
                orElseThrow(()->new RuntimeException("Poll not found"));
        PollMapper.toUpdateEntity(poll, pollUpdateRequest);
        List<PollOption> pollOptions = pollOptionService.updatePollOptions(poll,pollUpdateRequest);
        poll.setPollOptions(pollOptions);
        Poll savedUser = pollRepository.save(poll);
        return PollMapper.toResponse(savedUser);
    }

    @Override
    public void deletePoll(UUID id) {
        Poll poll = pollRepository.findByUuid(id)
                .orElseThrow(()->new RuntimeException("Poll not found"));
        pollRepository.delete(poll);
    }
}
