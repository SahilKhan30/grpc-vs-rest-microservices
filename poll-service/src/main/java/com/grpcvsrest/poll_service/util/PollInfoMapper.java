package com.grpcvsrest.poll_service.util;

import com.grpcvsrest.poll_service.dto.response.PollInfoResponse;
import com.grpcvsrest.poll_service.dto.response.PollResponse;
import com.grpcvsrest.poll_service.dto.response.UserResponse;
import com.grpcvsrest.poll_service.dto.response.VoteResponse;

import java.util.List;

public class PollInfoMapper {

    public static PollInfoResponse toResponse(PollResponse pollResponse, UserResponse userResponse, List<VoteResponse> voteResponseList){
        return PollInfoResponse.builder()
                .id(pollResponse.getId())
                .title(pollResponse.getTitle())
                .description(pollResponse.getDescription())
                .pollOptions(pollResponse.getPollOptions())
                .pollType(pollResponse.getPollType())
                .pollCategory(pollResponse.getPollCategory())
                .userResponse(userResponse)
                .pollVotes(voteResponseList)
                .createdAt(pollResponse.getCreatedAt())
                .updatedAt(pollResponse.getUpdatedAt())
                .build();
    }
}
