package com.project.urlshortener.mapper;

import com.project.urlshortener.dto.ShortUrlDto;
import com.project.urlshortener.dto.UserDto;
import com.project.urlshortener.entity.ShortUrl;
import org.springframework.stereotype.Component;

@Component
public class ShortUrlToDto {

    private final UserToDto userToDto;

    ShortUrlToDto(UserToDto userToDto) {
        this.userToDto = userToDto;
    }

    public ShortUrlDto convertToDto(ShortUrl shortUrl) {
        UserDto userDto=null;
        if(shortUrl.getCreatedBy() != null) {
            userDto = userToDto.convertToDto(shortUrl.getCreatedBy());
        }

        return new ShortUrlDto(shortUrl.getShortKey(),shortUrl.getOriginalUrl(),shortUrl.getExpiresAt(),
                userDto,shortUrl.getClickCount(),shortUrl.getCreatedAt(),shortUrl.getIsPrivate());

    }
}
