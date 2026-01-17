package com.grpcvsrest.poll_service.dto.response;

import com.grpcvsrest.poll_service.enums.PollCategory;
import com.grpcvsrest.poll_service.enums.PollType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PollInfoResponse {
    private UUID id;
    private UserResponse userResponse;
    private String title;
    private String description;
    private PollType pollType;
    private PollCategory pollCategory;
    private List<PollOptionResponse> pollOptions;
    private List<VoteResponse> pollVotes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
