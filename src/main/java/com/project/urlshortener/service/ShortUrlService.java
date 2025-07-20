package com.project.urlshortener.service;

import com.project.urlshortener.dto.ShortUrlDto;
import com.project.urlshortener.entity.ShortUrl;
import com.project.urlshortener.mapper.ShortUrlToDto;
import com.project.urlshortener.repository.ShortUrlRepository;
import org.springframework.stereotype.Service;

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
}
