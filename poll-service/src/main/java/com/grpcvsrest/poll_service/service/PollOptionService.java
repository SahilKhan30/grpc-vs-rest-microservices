package com.grpcvsrest.poll_service.service;

import com.grpcvsrest.poll_service.dto.PollUpdateRequest;
import com.grpcvsrest.poll_service.entity.Poll;
import com.grpcvsrest.poll_service.entity.PollOption;

import java.util.List;

public interface PollOptionService {
    List<PollOption> updatePollOptions(Poll poll, PollUpdateRequest pollUpdateRequest);
}
