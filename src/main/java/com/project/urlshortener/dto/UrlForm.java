package com.project.urlshortener.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UrlForm(@NotBlank(message = "Url can't be blank")
                      String originalUrl,
                      @Min(1)
                      @Max(30)
                      Integer expirationInDays,
                      Boolean isPrivate
                      ) {
}
