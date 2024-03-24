package com.seyed.ali.util.converter;

import com.seyed.ali.model.dto.UserDTO;
import com.seyed.ali.model.entity.LogUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDTOToLogUserConverter implements Converter<UserDTO, LogUser> {

    @Override
    public LogUser convert(UserDTO source) {
        return LogUser.builder()
                .userId(source.userId())
                .username(source.username())
                .roles(source.roles())
                .enabled(source.enabled())
                .build();
    }

}
