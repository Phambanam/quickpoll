package com.phamnam.quickpoll.repository;

import com.phamnam.quickpoll.domain.Poll;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends PagingAndSortingRepository<Poll,Long> {
}
