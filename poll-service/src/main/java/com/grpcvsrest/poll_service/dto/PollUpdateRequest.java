package com.grpcvsrest.poll_service.dto;

import com.grpcvsrest.poll_service.enums.PollCategory;
import com.grpcvsrest.poll_service.enums.PollType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PollUpdateRequest {
    private String title;
    private String description;
    private PollType pollType;
    private PollCategory pollCategory;
    private List<PollOptionCreateUpdateRequest> pollOptions;
}
