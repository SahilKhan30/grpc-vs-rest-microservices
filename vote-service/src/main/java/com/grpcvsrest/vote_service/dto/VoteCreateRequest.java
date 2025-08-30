package com.grpcvsrest.vote_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteCreateRequest {

    @NotNull(message = "user id can not be null")
    private UUID userId;

    @NotNull(message = "poll id can not be null")
    private UUID pollId;

    @NotNull(message = "poll option id can not be null")
    private UUID pollOptionId;
}
