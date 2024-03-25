package com.seyed.ali.util.converter;

import com.seyed.ali.model.dto.UserDTO;
import com.seyed.ali.model.entity.LogUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDTOToLogUserConverter implements Converter<UserDTO, LogUser> {

    @Override
    public LogUser convert(UserDTO source) {
        LogUser logUser = new LogUser();
        logUser.setUsername(source.username());
        logUser.setEnabled(source.enabled());
        logUser.setRoles(source.roles());
        return logUser;
    }

}
