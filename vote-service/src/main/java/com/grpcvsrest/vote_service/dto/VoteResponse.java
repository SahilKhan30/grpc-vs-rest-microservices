package com.grpcvsrest.vote_service.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteResponse {
    private UUID id;
    private UUID userId;
    private UUID pollId;
    private UUID pollOptionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
