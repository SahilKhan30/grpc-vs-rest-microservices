package com.grpcvsrest.poll_service.util;

import com.grpcvsrest.poll_service.dto.response.PollOptionResponse;
import com.grpcvsrest.poll_service.entity.PollOption;

public class PollOptionMapper {

    public static PollOptionResponse toResponse(PollOption pollOption){
        return PollOptionResponse.builder()
                .id(pollOption.getUuid())
                .pollId(pollOption.getPoll().getUuid())
                .optionText(pollOption.getOptionText())
                .createdAt(pollOption.getCreatedAt())
                .updatedAt(pollOption.getUpdatedAt())
                .build();
    }

}
