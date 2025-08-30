package com.grpcvsrest.poll_service.service;


import com.grpcvsrest.poll_service.dto.PollCreateRequest;
import com.grpcvsrest.poll_service.dto.PollResponse;
import com.grpcvsrest.poll_service.dto.PollUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface PollService {
    PollResponse createPoll(PollCreateRequest pollCreateRequest);
    PollResponse getPollById(UUID id);
    List<PollResponse> getAllPolls();
    PollResponse updatePoll(UUID id, PollUpdateRequest pollUpdateRequest);
    void deletePoll(UUID id);
}
