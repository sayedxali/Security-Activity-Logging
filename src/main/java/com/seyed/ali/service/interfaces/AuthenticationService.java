package com.seyed.ali.service.interfaces;

import com.seyed.ali.model.dto.UserDTO;
import com.seyed.ali.model.entity.LogUser;
import org.springframework.security.core.Authentication;

import java.util.Map;

public interface AuthenticationService {

    UserDTO registerUser(LogUser logUser);

    Map<String, Object> createLoginInfo(Authentication authentication);

}
