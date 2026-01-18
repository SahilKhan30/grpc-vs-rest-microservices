package com.grpcvsrest.poll_service.controller;

import com.grpcvsrest.poll_service.dto.request.PollCreateRequest;
import com.grpcvsrest.poll_service.dto.response.PollInfoResponse;
import com.grpcvsrest.poll_service.dto.response.PollResponse;
import com.grpcvsrest.poll_service.dto.request.PollUpdateRequest;
import com.grpcvsrest.poll_service.service.PollService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rest/polls")
@RequiredArgsConstructor
public class PollRestController {

    private final PollService pollService;

    @PostMapping
    public ResponseEntity<PollResponse> createPoll(@Valid @RequestBody PollCreateRequest pollCreateRequest){
        PollResponse createdPoll = pollService.createPoll(pollCreateRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdPoll.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdPoll);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PollResponse> getPollById(@PathVariable UUID id) {
        return ResponseEntity.ok(pollService.getPollById(id));
    }

    @GetMapping
    public ResponseEntity<List<PollResponse>> getAllPolls() {
        return ResponseEntity.ok(pollService.getAllPolls());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PollResponse> updatePoll(@PathVariable UUID id,
                                                   @Valid @RequestBody PollUpdateRequest pollUpdateRequest){
        return ResponseEntity.ok(pollService.updatePoll(id, pollUpdateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoll(@PathVariable UUID id) {
        pollService.deletePoll(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/info")
    public ResponseEntity<String> getPollInfo() {
        List<PollInfoResponse> pollInfoResponses =  pollService.getPollInfoRest();
        System.out.println(pollInfoResponses);
        return ResponseEntity.ok("success");
    }


}
