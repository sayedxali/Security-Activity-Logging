package com.seyed.ali.service;

import com.seyed.ali.config.security.jwt.JwtProvider;
import com.seyed.ali.model.dto.UserDTO;
import com.seyed.ali.model.entity.LogUser;
import com.seyed.ali.model.entity.UserDetailsImpl;
import com.seyed.ali.repository.LogUserRepository;
import com.seyed.ali.service.interfaces.AuthenticationService;
import com.seyed.ali.util.converter.LogUserToUserDTOConverter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final LogUserRepository changeLogUserRepository;
    private final LogUserToUserDTOConverter logUserToUserDTOConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;


    @Override
    public UserDTO registerUser(LogUser user) {
        String encodedPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        this.changeLogUserRepository.save(user);
        return this.logUserToUserDTOConverter.convert(user);
    }

    @Override
    public Map<String, Object> createLoginInfo(Authentication authentication) {
        // Create user info.
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        LogUser logUser = principal.getLogUser();
        UserDTO userDto = this.logUserToUserDTOConverter.convert(logUser);

        // Create a JWT.
        String token = this.jwtProvider.createToken(authentication);

        Map<String, Object> loginResultMap = new HashMap<>();
        loginResultMap.put("userInfo", userDto);
        loginResultMap.put("token", token);
        return loginResultMap;
    }

}
