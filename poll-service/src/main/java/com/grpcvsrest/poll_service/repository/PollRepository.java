package com.grpcvsrest.poll_service.repository;

import com.grpcvsrest.poll_service.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PollRepository extends JpaRepository<Poll, Integer> {
    Optional<Poll> findByUuid(UUID id);
}
