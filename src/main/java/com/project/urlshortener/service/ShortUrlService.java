package com.project.urlshortener.service;

import com.project.urlshortener.ApplicationProperties;
import com.project.urlshortener.dto.ShortUrlCommand;
import com.project.urlshortener.dto.ShortUrlDto;
import com.project.urlshortener.entity.ShortUrl;
import com.project.urlshortener.mapper.ShortUrlToDto;
import com.project.urlshortener.repository.ShortUrlRepository;
import com.project.urlshortener.utils.ShortUrlUtil;
import com.project.urlshortener.utils.UrlValidator;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;
    private final ShortUrlToDto shortUrlToDto;
    private final ApplicationProperties applicationProperties;

    public ShortUrlService(ShortUrlRepository shortUrlRepository, ShortUrlToDto shortUrlToDto,ApplicationProperties properties) {
        this.shortUrlRepository = shortUrlRepository;
        this.shortUrlToDto = shortUrlToDto;
        this.applicationProperties = properties;
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

    @Transactional
    public ShortUrlDto createShortUrl(ShortUrlCommand shortUrlCommand) {
        if(!UrlValidator.isUrlExists(shortUrlCommand.originalUrl())) {
            throw new RuntimeException("Invalid URL. URL does not exist.");
        }
        ShortUrl shortUrl = new ShortUrl();
        String shortUrlKey = generateUniqueShortKey();
        shortUrl.setOriginalUrl(shortUrlCommand.originalUrl());
        shortUrl.setCreatedAt(Instant.now());
        shortUrl.setExpiresAt(Instant.now().plus(applicationProperties.defaultExpiryInDays(), ChronoUnit.DAYS));
        shortUrl.setCreatedBy(null);
        shortUrl.setClickCount(0L);
        shortUrl.setIsPrivate(false);
        shortUrl.setShortKey(shortUrlKey);
        shortUrlRepository.save(shortUrl);
        return  shortUrlToDto.convertToDto(shortUrl);
    }

    @Transactional
    public String getOriginalUrl(String shorUrl) {
        Optional<ShortUrl> shortUrlOptional = shortUrlRepository.findByShortKey(shorUrl);

        if(shortUrlOptional.isEmpty())
            throw new RuntimeException("Invalid Short URL...");

        ShortUrl shortUrl = shortUrlOptional.get();
        if(shortUrl.getExpiresAt()!=null && shortUrl.getExpiresAt().isBefore(Instant.now()))
            throw new RuntimeException("Short URL is expired...");

        shortUrl.setClickCount(shortUrl.getClickCount()+1);
        shortUrlRepository.save(shortUrl);
        return shortUrl.getOriginalUrl();

    }
}
