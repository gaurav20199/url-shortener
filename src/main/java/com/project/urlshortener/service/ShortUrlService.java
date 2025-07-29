package com.project.urlshortener.service;

import com.project.urlshortener.ApplicationProperties;
import com.project.urlshortener.dto.PageResult;
import com.project.urlshortener.dto.ShortUrlCommand;
import com.project.urlshortener.dto.ShortUrlDto;
import com.project.urlshortener.entity.ShortUrl;
import com.project.urlshortener.entity.User;
import com.project.urlshortener.exception.ExpiredUrl;
import com.project.urlshortener.exception.InvalidUrl;
import com.project.urlshortener.exception.InvalidUserDetails;
import com.project.urlshortener.mapper.ShortUrlToDto;
import com.project.urlshortener.repository.ShortUrlRepository;
import com.project.urlshortener.utils.ShortUrlUtil;
import com.project.urlshortener.utils.UrlValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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

    private Pageable getPageableObj(int pageNumber, int pageSize) {
        pageNumber = pageNumber>1?pageNumber-1:0;
        return PageRequest.of(pageNumber,pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public PageResult<ShortUrlDto> findAllPublicUrls(int pageNumber, int pageSize) {
        Pageable pageable = getPageableObj(pageNumber, pageSize);
        Page<ShortUrlDto> pageInfo = shortUrlRepository.findAllPublicUrls(pageable).map(shortUrlToDto::convertToDto);
        return PageResult.from(pageInfo);
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
        if(!UrlValidator.isUrlExists(shortUrlCommand.originalUrl()))
            throw new InvalidUrl(HttpStatus.BAD_REQUEST,"Invalid URL. URL does not exist.");

        ShortUrl shortUrl = new ShortUrl();
        String shortUrlKey = generateUniqueShortKey();
        shortUrl.setOriginalUrl(shortUrlCommand.originalUrl());
        shortUrl.setCreatedAt(Instant.now());
        if(shortUrlCommand.user().isPresent()) {
            shortUrl.setCreatedBy(shortUrlCommand.user().get());
            shortUrl.setExpiresAt(Instant.now().plus(shortUrlCommand.expirationInDays(), ChronoUnit.DAYS));
            shortUrl.setIsPrivate(shortUrlCommand.isPrivate()!=null && shortUrlCommand.isPrivate());
        }else {
            shortUrl.setExpiresAt(Instant.now().plus(applicationProperties.defaultExpiryInDays(), ChronoUnit.DAYS));
            shortUrl.setCreatedBy(null);
            shortUrl.setIsPrivate(false);
        }
        shortUrl.setClickCount(0L);
        shortUrl.setShortKey(shortUrlKey);
        shortUrlRepository.save(shortUrl);
        return  shortUrlToDto.convertToDto(shortUrl);
    }

    @Transactional
    public String getOriginalUrl(String shorUrl,Optional<User> authenticatedUser) {
        Optional<ShortUrl> shortUrlOptional = shortUrlRepository.findByShortKey(shorUrl);

        if(shortUrlOptional.isEmpty())
            throw new InvalidUrl(HttpStatus.BAD_REQUEST,"Invalid Short URL...");

        ShortUrl shortUrl = shortUrlOptional.get();
        if(shortUrl.getExpiresAt()!=null && shortUrl.getExpiresAt().isBefore(Instant.now()))
            throw new ExpiredUrl(HttpStatus.GONE,"Short URL is expired...");

        boolean isPrivate = isPrivateUrl(shortUrl.getIsPrivate());

        if(isPrivate && !isUrlOwnedByUser(authenticatedUser,shortUrl))
            throw new InvalidUrl(HttpStatus.UNAUTHORIZED,"You don't have sufficient permission(s) to access the url");

        shortUrl.setClickCount(shortUrl.getClickCount()+1);
        shortUrlRepository.save(shortUrl);
        return shortUrl.getOriginalUrl();

    }

    private boolean isPrivateUrl(Boolean isPrivate) {
        return isPrivate != null && isPrivate;
    }

    public boolean isUrlOwnedByUser(Optional<User> authenticatedUser, ShortUrl shortUrl) {
        return authenticatedUser.isPresent() && shortUrl.getCreatedBy() != null &&
                authenticatedUser.get().getId().equals(shortUrl.getCreatedBy().getId());
    }

    public PageResult<ShortUrlDto> findAllPublicUrlsForUser(int page, Optional<User> authenticatedUser, int pageSize) {
        if(authenticatedUser.isEmpty())
            throw new InvalidUserDetails(HttpStatus.UNAUTHORIZED,"You don't have sufficient permissions to access this resource");
        
        Pageable pageableObj = getPageableObj(page, pageSize);
        Page<ShortUrlDto> currentUserUrls = shortUrlRepository.findByCreatedById(authenticatedUser.get().getId(), pageableObj).map(shortUrlToDto::convertToDto);
        return PageResult.from(currentUserUrls);
    }
}
