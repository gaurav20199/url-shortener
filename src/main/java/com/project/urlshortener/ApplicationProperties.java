package com.project.urlshortener;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "app")
@Validated
public record ApplicationProperties(@NotBlank(message = "Base url can't be empty")
                                    String baseUrl,
                                    @Min(1)
                                    @Max(10)
                                    int defaultExpiryInDays,
                                    @DefaultValue("10")
                                    int pageSize) {
}
