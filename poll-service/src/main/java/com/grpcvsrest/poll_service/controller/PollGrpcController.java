package com.grpcvsrest.poll_service.controller;

import com.grpcvsrest.poll_service.dto.response.PollInfoResponse;
import com.grpcvsrest.poll_service.service.PollService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/grpc/polls")
@RequiredArgsConstructor
public class PollGrpcController {

    private final PollService pollService;

    @GetMapping("/info")
    public ResponseEntity<List<PollInfoResponse>> getPollInfo() {
        return ResponseEntity.ok(pollService.getPollInfoGrpc());
    }
}
