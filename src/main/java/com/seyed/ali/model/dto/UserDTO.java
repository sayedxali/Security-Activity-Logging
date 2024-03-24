package com.seyed.ali.model.dto;

import com.seyed.ali.model.entity.LogUser;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.io.Serializable;

/**
 * DTO for {@link LogUser}
 */
@Builder
public record UserDTO(String userId,
                      @NotEmpty(message = "username is required") String username,
                      @NotEmpty(message = "roles is required") String roles,
                      boolean enabled) implements Serializable {
}