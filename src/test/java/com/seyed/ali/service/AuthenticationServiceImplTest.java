package com.seyed.ali.service;

import com.seyed.ali.config.security.jwt.JwtProvider;
import com.seyed.ali.model.dto.UserDTO;
import com.seyed.ali.model.entity.LogUser;
import com.seyed.ali.repository.LogUserRepository;
import com.seyed.ali.util.converter.LogUserToUserDTOConverter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    private @InjectMocks AuthenticationServiceImpl authenticationService;

    private @Mock LogUserRepository changeLogUserRepository;
    private @Mock LogUserToUserDTOConverter logUserToUserDTOConverter;
    private @Mock PasswordEncoder passwordEncoder;
    private @Mock JwtProvider jwtProvider;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("registerUser should return success when input is valid")
    public void registerUser_ValidInputs_Success() {
        // Given (Arrange) - Set up the conditions for your test.
        LogUser user = new LogUser("1", "user", "user", false, "USER", Instant.now());
        given(this.changeLogUserRepository.save(user))
                .willReturn(user);

        UserDTO userDTO = new UserDTO("1", "user", "USER", false);
        given(this.logUserToUserDTOConverter.convert(user))
                .willReturn(userDTO);

        // When (Act) - Call the method you're testing.
        UserDTO savedUser = this.authenticationService.registerUser(user);

        // Then (Assert) - Check the result.
        assertThat(savedUser).as("Registration must be successful and not null.").isNotNull();
        assertThat(savedUser.userId()).isEqualTo(user.getId());
        assertThat(savedUser.username()).isEqualTo(user.getUsername());
        assertThat(savedUser.roles()).isEqualTo(user.getRoles());
        assertThat(savedUser.enabled()).isEqualTo(user.isEnabled());
    }

}