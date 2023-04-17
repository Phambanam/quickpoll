package com.phamnam.quickpoll.repository;

import com.phamnam.quickpoll.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    public User findByUsername(String username);

}
