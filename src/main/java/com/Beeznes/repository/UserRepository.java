package com.Beeznes.repository;

import com.Beeznes.model.Status;
import com.Beeznes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    List<User> findAllByStatus(Status status);
}
