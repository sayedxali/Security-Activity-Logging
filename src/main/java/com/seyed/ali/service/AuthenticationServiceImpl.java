package com.seyed.ali.service;

import com.seyed.ali.model.dto.UserDTO;
import com.seyed.ali.model.entity.LogUser;
import com.seyed.ali.repository.LogUserRepository;
import com.seyed.ali.util.converter.LogUserToUserDTOConverter;
import com.seyed.ali.util.converter.UserDTOToLogUserConverter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final LogUserRepository logUserRepository;
    private final LogUserToUserDTOConverter logUserToUserDTOConverter;

    @Override
    public UserDTO registerUser(LogUser user) {
        this.logUserRepository.save(user);
        return this.logUserToUserDTOConverter.convert(user);
    }

}
