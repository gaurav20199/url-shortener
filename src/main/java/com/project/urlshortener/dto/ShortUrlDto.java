package com.project.urlshortener.dto;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.project.urlshortener.entity.ShortUrl}
 */
public record ShortUrlDto(String shortKey, String originalUrl, Instant expiresAt,
                          UserDto createdBy, Long clickCount, Instant createdAt,Boolean isPrivate) implements Serializable {
}