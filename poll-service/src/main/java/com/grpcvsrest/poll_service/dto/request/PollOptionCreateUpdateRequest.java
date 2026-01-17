package com.grpcvsrest.poll_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PollOptionCreateUpdateRequest {

    private UUID id;

    private UUID pollId;

    @NotBlank(message = "Option text cannot be blank")
    private String optionText;

}
