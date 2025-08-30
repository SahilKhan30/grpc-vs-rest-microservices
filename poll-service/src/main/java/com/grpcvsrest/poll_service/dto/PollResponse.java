package com.grpcvsrest.poll_service.dto;


import com.grpcvsrest.poll_service.entity.PollOption;
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
public class PollResponse {
    private UUID id;
    private UUID userId;
    private String title;
    private String description;
    private PollType pollType;
    private PollCategory pollCategory;
    private List<PollOptionResponse> pollOptions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
