package com.project.urlshortener.service;

import com.project.urlshortener.entity.ShortUrl;
import com.project.urlshortener.repository.ShortUrlRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;

    public ShortUrlService(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    public List<ShortUrl> findAllPublicUrls() {
        return shortUrlRepository.findAllPublicUrls();
    }
}
