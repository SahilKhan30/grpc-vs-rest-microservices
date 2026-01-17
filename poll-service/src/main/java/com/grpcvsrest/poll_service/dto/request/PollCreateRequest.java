package com.grpcvsrest.poll_service.dto.request;

import com.grpcvsrest.poll_service.enums.PollCategory;
import com.grpcvsrest.poll_service.enums.PollType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PollCreateRequest {

    @NotNull(message = "user id can not be null")
    private UUID userId;

    @NotBlank(message = "title can not be blank")
    private String title;

    private String description;

    @NotNull(message = "poll type can not be null")
    private PollType pollType;

    @NotNull(message = "poll category can not be null")
    private PollCategory pollCategory;

    @NotEmpty(message = "poll options can not be empty")
    private List<PollOptionCreateUpdateRequest> pollOptions;

}
