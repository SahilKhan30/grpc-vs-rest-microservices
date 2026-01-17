package com.grpcvsrest.poll_service.util;

import com.google.protobuf.Timestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

public class VoteGrpcResponseMapper {

    private static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null || timestamp.getSeconds() == 0) {
            return null;
        }
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static com.grpcvsrest.poll_service.dto.response.VoteResponse toVoteResponse(
            com.grpcvsrest.vote_service.grpc.VoteResponse grpcVoteResponse){
        com.grpcvsrest.poll_service.dto.response.VoteResponse dto =
                new com.grpcvsrest.poll_service.dto.response.VoteResponse();
        dto.setId(UUID.fromString(grpcVoteResponse.getId()));
        dto.setPollId(UUID.fromString(grpcVoteResponse.getPollId()));
        dto.setUserId(UUID.fromString(grpcVoteResponse.getUserId()));
        dto.setPollOptionId(UUID.fromString(grpcVoteResponse.getPollOptionId()));
        dto.setCreatedAt(toLocalDateTime(grpcVoteResponse.getCreatedAt()));
        dto.setUpdatedAt(toLocalDateTime(grpcVoteResponse.getUpdatedAt()));

        return dto;
    }
}
