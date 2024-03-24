package com.seyed.ali.util.converter;

import com.seyed.ali.model.dto.UserDTO;
import com.seyed.ali.model.entity.LogUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LogUserToUserDTOConverter implements Converter<LogUser, UserDTO> {

    @Override
    public UserDTO convert(LogUser source) {
        return new UserDTO(
                source.getUserId(),
                source.getUsername(),
                source.getRoles(),
                source.isEnabled()
        );
    }

}
