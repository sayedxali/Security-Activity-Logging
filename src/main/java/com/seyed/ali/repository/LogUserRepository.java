package com.seyed.ali.repository;

import com.seyed.ali.model.entity.LogUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LogUserRepository extends JpaRepository<LogUser, Integer> {

    Optional<LogUser> findByUsername(String username);

}