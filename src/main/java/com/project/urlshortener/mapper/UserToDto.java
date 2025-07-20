package com.project.urlshortener.mapper;

import com.project.urlshortener.dto.UserDto;
import com.project.urlshortener.entity.User;import org.springframework.stereotype.Component;

@Component
public class UserToDto {

    public UserDto convertToDto(User user) {
        return new UserDto(user.getName(),user.getCreatedAt());
    }
}
