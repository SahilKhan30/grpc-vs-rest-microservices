package com.grpcvsrest.vote_service.controller;

import com.grpcvsrest.vote_service.dto.VoteCreateRequest;
import com.grpcvsrest.vote_service.dto.VoteResponse;
import com.grpcvsrest.vote_service.service.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rest/votes")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping()
    public ResponseEntity<VoteResponse> createVote(@Valid @RequestBody VoteCreateRequest voteCreateRequest){
        VoteResponse createdVote  = voteService.createVote(voteCreateRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdVote.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdVote);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoteResponse> getVoteById(@PathVariable UUID id){
        VoteResponse voteResponse = voteService.getVoteById(id);
        return ResponseEntity.ok(voteResponse);
    }

    @GetMapping
    public ResponseEntity<List<VoteResponse>> getAllVotes(){
        List<VoteResponse> voteResponseList = voteService.getAllVotes();
        return ResponseEntity.ok(voteResponseList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVote(@PathVariable UUID id){
        voteService.deleteVote(id);
        return ResponseEntity.noContent().build();
    }

}
