package com.seyed.ali.service;

import com.seyed.ali.model.dto.UserDTO;
import com.seyed.ali.model.entity.LogUser;

public interface AuthenticationService {

    UserDTO registerUser(LogUser user);

}
