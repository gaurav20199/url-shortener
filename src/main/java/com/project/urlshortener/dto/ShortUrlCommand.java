package com.project.urlshortener.dto;

import com.project.urlshortener.entity.User;
import java.util.Optional;

public record ShortUrlCommand(String originalUrl, Integer expirationInDays, Boolean isPrivate, Optional<User> user) {
}
