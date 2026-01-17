package com.grpcvsrest.vote_service.service.impl;

import com.google.protobuf.Timestamp;
import com.grpcvsrest.vote_service.entity.Vote;
import com.grpcvsrest.vote_service.grpc.GetVotesByPollIdRequest;
import com.grpcvsrest.vote_service.grpc.GetVotesByPollIdResponse;
import com.grpcvsrest.vote_service.grpc.VoteResponse;
import com.grpcvsrest.vote_service.grpc.VoteServiceGrpc;
import com.grpcvsrest.vote_service.repository.VoteRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@GrpcService
public class VoteServiceGrpcImpl extends VoteServiceGrpc.VoteServiceImplBase {


    private final VoteRepository voteRepository;

    private Timestamp toProtoTimestamp(LocalDateTime time) {
        if (time == null) return Timestamp.getDefaultInstance();
        return Timestamp.newBuilder()
                .setSeconds(time.toEpochSecond(ZoneOffset.UTC))
                .setNanos(time.getNano())
                .build();
    }

    private VoteResponse toProtoVoteResponse(Vote vote) {
        return VoteResponse.newBuilder()
                .setId(vote.getUuid().toString())
                .setUserId(vote.getUserId().toString())
                .setPollId(vote.getPollId().toString())
                .setPollOptionId(vote.getPollOptionId().toString())
                .setCreatedAt(toProtoTimestamp(vote.getCreatedAt()))
                .setUpdatedAt(toProtoTimestamp(vote.getUpdatedAt()))
                .build();
    }


    @Override
    public void getVotesByPollId(GetVotesByPollIdRequest request, StreamObserver<GetVotesByPollIdResponse> responseObserver) {
        UUID pollId = UUID.fromString(request.getPollId());

        // fetch votes from repository
        List<VoteResponse> votesData = voteRepository.findByPollId(pollId)
                .stream()
                .map(this::toProtoVoteResponse)
                .toList();
        // Simulating high-payload data for benchmarking REST vs gRPC performance.
        // This inflates the response by repeating the retrieved votes 200 times.
        List<VoteResponse> votes = Collections.nCopies(200, votesData)
                .stream()
                .flatMap(List::stream)
                .toList();

        // build response
        GetVotesByPollIdResponse response = GetVotesByPollIdResponse.newBuilder()
                .addAllVotes(votes)
                .build();

        // send response
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
