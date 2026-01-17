package com.grpcvsrest.poll_service.clients;

import com.grpcvsrest.poll_service.dto.response.VoteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "vote-service", url = "http://localhost:8101/api/rest/votes")
public interface VoteRestClient {

    @GetMapping("/poll/{pollId}")
    List<VoteResponse> getVoteByPollId(@PathVariable("pollId") UUID pollId);
}
