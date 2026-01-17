package com.grpcvsrest.poll_service.service.impl;

import com.grpcvsrest.poll_service.grpc.PollInfoRequest;
import com.grpcvsrest.poll_service.grpc.PollInfoResponse;
import com.grpcvsrest.poll_service.grpc.PollInfoResponseList;
import com.grpcvsrest.poll_service.grpc.PollServiceGrpc;
import com.grpcvsrest.poll_service.service.PollService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class PollGrpcServiceImpl extends PollServiceGrpc.PollServiceImplBase {

    private final PollService pollService;

    @Override
    public void getPollInfo(PollInfoRequest request,
                            StreamObserver<PollInfoResponseList> responseObserver) {

        var pollInfoList = pollService.getPollInfoGrpc();
        PollInfoResponseList.Builder responseList = PollInfoResponseList.newBuilder();

        for (var poll : pollInfoList) {
            PollInfoResponse.Builder grpcPoll = PollInfoResponse.newBuilder()
                    .setId(poll.getId().toString())
                    .setTitle(poll.getTitle())
                    .setDescription(poll.getDescription())
                    .setPollType(poll.getPollType().toString())
                    .setPollCategory(poll.getPollCategory().toString());

            // Map user
            var user = poll.getUserResponse();
            if (user != null) {
                grpcPoll.setUserResponse(
                        com.grpcvsrest.user_service.grpc.UserResponse.newBuilder()
                                .setId(user.getId().toString())
                                .setFirstName(user.getFirstName())
                                .setLastName(user.getLastName())
                                .setEmail(user.getEmail())
                                .build());
            }

            // Map votes
            if (poll.getPollVotes() != null) {
                poll.getPollVotes().forEach(v -> grpcPoll.addPollVotes(
                        com.grpcvsrest.vote_service.grpc.VoteResponse.newBuilder()
                                .setId(v.getId().toString())
                                .setUserId(v.getUserId().toString())
                                .setPollId(v.getPollId().toString())
                                .setPollOptionId(v.getPollOptionId().toString())
                                .build()
                ));
            }

            responseList.addPolls(grpcPoll.build());
        }

        responseObserver.onNext(responseList.build());
        responseObserver.onCompleted();
    }
}