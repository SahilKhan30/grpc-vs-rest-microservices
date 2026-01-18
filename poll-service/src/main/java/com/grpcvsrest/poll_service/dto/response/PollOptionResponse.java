package com.grpcvsrest.poll_service.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PollOptionResponse {
    private UUID id;
    private UUID pollId;
    private String optionText;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
