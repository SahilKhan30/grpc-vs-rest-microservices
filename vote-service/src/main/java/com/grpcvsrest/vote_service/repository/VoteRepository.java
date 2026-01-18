package com.grpcvsrest.vote_service.repository;

import com.grpcvsrest.vote_service.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoteRepository extends JpaRepository<Vote, Integer> {
    Optional<Vote> findByUuid(UUID id);

    List<Vote> findByPollId(UUID pollId);
}
