package com.seyed.ali.repository;

import com.seyed.ali.model.entity.LogUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogUserRepository extends JpaRepository<LogUser, String> {
}