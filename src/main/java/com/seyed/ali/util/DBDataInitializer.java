package com.seyed.ali.util;

import com.seyed.ali.model.entity.LogUser;
import com.seyed.ali.repository.LogUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DBDataInitializer implements CommandLineRunner {

    private final LogUserRepository logUserRepository;

    @Override
    public void run(String... args) throws Exception {
        LogUser user = new LogUser(
                "1",
                "user",
                "user",
                "USER",
                false,
                Instant.now());

        LogUser admin = new LogUser(
                "2",
                "admin",
                "admin",
                "ADMIN",
                true,
                Instant.now());

        this.logUserRepository.saveAll(List.of(user, admin));
    }

}
