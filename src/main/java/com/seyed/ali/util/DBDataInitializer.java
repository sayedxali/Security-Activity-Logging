package com.seyed.ali.util;

import com.seyed.ali.model.entity.LogUser;
import com.seyed.ali.service.interfaces.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DBDataInitializer {

    private final AuthenticationService authenticationService;

    @Bean
    public CommandLineRunner run() {
        return _ -> {
            // Create some users.
            LogUser u1 = new LogUser();
            u1.setId("1");
            u1.setUsername("admin");
            u1.setPassword("admin");
            u1.setEnabled(true);
            u1.setRoles("admin user");

            LogUser u2 = new LogUser();
            u2.setId("2");
            u2.setUsername("user");
            u2.setPassword("user");
            u2.setEnabled(true);
            u2.setRoles("user");

            this.authenticationService.registerUser(u1); // because the logic to encode password is int userService.
            this.authenticationService.registerUser(u2); // because the logic to encode password is int userService.
        };
    }

}
