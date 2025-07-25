package com.project.urlshortener.service;

import com.project.urlshortener.dto.ShortUrlCommand;
import com.project.urlshortener.dto.ShortUrlDto;
import com.project.urlshortener.entity.ShortUrl;
import com.project.urlshortener.mapper.ShortUrlToDto;
import com.project.urlshortener.repository.ShortUrlRepository;
import com.project.urlshortener.utils.ShortUrlUtil;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;
    private final ShortUrlToDto shortUrlToDto;

    public ShortUrlService(ShortUrlRepository shortUrlRepository, ShortUrlToDto shortUrlToDto) {
        this.shortUrlRepository = shortUrlRepository;
        this.shortUrlToDto = shortUrlToDto;
    }

    public List<ShortUrlDto> findAllPublicUrls() {
        return shortUrlRepository.findAllPublicUrls().stream().map(shortUrlToDto::convertToDto).toList();
    }

    private String generateUniqueShortKey() {
        String shortKey;
        do {
            shortKey = ShortUrlUtil.generateRandomShortKey();
        } while (shortUrlRepository.existsByShortKey(shortKey));
        return shortKey;
    }

    public ShortUrlDto createShortUrl(ShortUrlCommand shortUrlCommand) {
        ShortUrl shortUrl = new ShortUrl();
        String shortUrlKey = generateUniqueShortKey();
        shortUrl.setOriginalUrl(shortUrlCommand.originalUrl());
        shortUrl.setCreatedAt(Instant.now());
        shortUrl.setExpiresAt(Instant.now().plus(1, ChronoUnit.DAYS));
        shortUrl.setCreatedBy(null);
        shortUrl.setClickCount(0L);
        shortUrl.setIsPrivate(false);
        shortUrl.setShortKey(shortUrlKey);
        shortUrlRepository.save(shortUrl);
        return  shortUrlToDto.convertToDto(shortUrl);
    }
}
