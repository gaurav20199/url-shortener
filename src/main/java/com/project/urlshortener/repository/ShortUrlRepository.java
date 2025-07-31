package com.project.urlshortener.repository;

import com.project.urlshortener.entity.ShortUrl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    @Query("select u from ShortUrl u left join fetch u.createdBy where u.isPrivate=false")
    Page<ShortUrl> findAllPublicUrls(Pageable pageable);

    boolean existsByShortKey(String shortKey);

    Optional<ShortUrl> findByShortKey(String shorUrl);

    Page<ShortUrl> findByCreatedById(Long userId, Pageable pageable);

    @Query("select u from ShortUrl u left join fetch u.createdBy")
    Page<ShortUrl> findAllShortUrls(Pageable pageable);

}
