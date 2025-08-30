package com.grpcvsrest.poll_service.service.impl;

import com.grpcvsrest.poll_service.dto.PollOptionCreateUpdateRequest;
import com.grpcvsrest.poll_service.dto.PollUpdateRequest;
import com.grpcvsrest.poll_service.entity.Poll;
import com.grpcvsrest.poll_service.entity.PollOption;
import com.grpcvsrest.poll_service.service.PollOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PollOptionServiceImpl implements PollOptionService {

    public List<PollOption> updatePollOptions(Poll poll, PollUpdateRequest pollUpdateRequest){
        List<PollOption> existingOptions = poll.getPollOptions();
        List<PollOptionCreateUpdateRequest> newOptions = pollUpdateRequest.getPollOptions();
        Map<UUID, PollOption> existingOptionsMap = existingOptions.stream()
                .collect(Collectors.toMap(PollOption::getUuid, Function.identity()));

        Set<UUID> updatedIds = new HashSet<>();

        for (PollOptionCreateUpdateRequest newOption: newOptions){
            UUID id = newOption.getId();
            if(id != null && existingOptionsMap.containsKey(id)){
                PollOption pollOption = existingOptionsMap.get(id);
                pollOption.setOptionText(newOption.getOptionText());
                updatedIds.add(id);
            } else {
                PollOption pollOption = new PollOption();
                pollOption.setOptionText(newOption.getOptionText());
                pollOption.setPoll(poll);
                existingOptions.add(pollOption);
                updatedIds.add(pollOption.getUuid());
            }
        }
        existingOptions.removeIf(pollOption -> !updatedIds.contains(pollOption.getUuid()));
        return existingOptions;
    }
}
