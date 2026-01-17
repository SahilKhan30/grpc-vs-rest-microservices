package com.grpcvsrest.poll_service.util;

import com.grpcvsrest.poll_service.dto.request.PollCreateRequest;
import com.grpcvsrest.poll_service.dto.response.PollResponse;
import com.grpcvsrest.poll_service.dto.request.PollUpdateRequest;
import com.grpcvsrest.poll_service.entity.Poll;
import com.grpcvsrest.poll_service.entity.PollOption;

import java.util.List;
import java.util.stream.Collectors;

public class PollMapper {

    public static Poll toCreateEntity(PollCreateRequest pollCreateRequest){
        Poll poll = new Poll();
        poll.setUserId(pollCreateRequest.getUserId());
        poll.setTitle(pollCreateRequest.getTitle());
        poll.setDescription(pollCreateRequest.getDescription());
        poll.setPollType(pollCreateRequest.getPollType());
        poll.setPollCategory(pollCreateRequest.getPollCategory());

        List<PollOption> listOptions = pollCreateRequest.getPollOptions().stream().map(option->{
            PollOption pollOption =  new PollOption();
            pollOption.setOptionText(option.getOptionText());
            pollOption.setPoll(poll);
            return pollOption;
        }).toList();
        poll.setPollOptions(listOptions);

        return poll;
    }

    public static void toUpdateEntity(Poll poll, PollUpdateRequest pollUpdateRequest){
        if(pollUpdateRequest.getTitle()!=null){
            poll.setTitle(pollUpdateRequest.getTitle());
        }
        if(pollUpdateRequest.getDescription()!=null){
            poll.setDescription((pollUpdateRequest.getDescription()));
        }
        if(pollUpdateRequest.getPollType()!=null){
            poll.setPollType(pollUpdateRequest.getPollType());
        }
        if(pollUpdateRequest.getPollCategory()!=null){
            poll.setPollCategory(pollUpdateRequest.getPollCategory());
        }
    }

    public static PollResponse toResponse(Poll poll){
        return PollResponse.builder()
                .id(poll.getUuid())
                .userId(poll.getUserId())
                .title(poll.getTitle())
                .description(poll.getDescription())
                .pollType(poll.getPollType())
                .pollCategory(poll.getPollCategory())
                .pollOptions(poll.getPollOptions().stream()
                        .map(PollOptionMapper::toResponse).collect(Collectors.toList()))
                .createdAt(poll.getCreatedAt())
                .updatedAt(poll.getUpdatedAt())
                .build();
    }

}
