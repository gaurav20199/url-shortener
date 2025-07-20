package com.project.urlshortener.dto;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.project.urlshortener.entity.User}
 */
public record UserDto(String name, Instant createdAt) implements Serializable {
}