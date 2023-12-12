package com.shems.server.converter;

import com.shems.server.domain.User;
import com.shems.server.dto.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserToUserResponseConverter {

    public UserResponse convert(User source) {
        UserResponse response = new UserResponse();
        response.setId(source.getId());
        response.setName(source.getName());
        response.setEmail(source.getEmail());
        return response;
    }
}
