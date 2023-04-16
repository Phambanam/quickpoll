package com.phamnam.quickpoll.repository;

import com.phamnam.quickpoll.domain.Option;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends CrudRepository<Option,Long> {
}
