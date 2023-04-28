package com.sysmap.parrot.data;

import com.sysmap.parrot.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByUsername(String username);
}
