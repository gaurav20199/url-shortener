package com.project.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;

public record UrlForm(@NotBlank(message = "Url can't be blank")
                      String originalUrl) {
}
