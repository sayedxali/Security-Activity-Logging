package com.seyed.ali.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LogUser implements Serializable {

    @Id
    private String userId;


    private @NotEmpty(message = "username is required") String username;
    private @NotEmpty(message = "password is required") String password;
    private @NotEmpty(message = "roles is required") String roles; // space separated string
    private boolean enabled;

    @CreatedDate
    private Instant createdAt;

}
