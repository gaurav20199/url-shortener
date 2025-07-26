package com.project.urlshortener.repository;

import com.project.urlshortener.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    @Query("select u from ShortUrl u left join fetch u.createdBy where u.isPrivate=false order by u.createdAt desc")
    List<ShortUrl> findAllPublicUrls();

    boolean existsByShortKey(String shortKey);

    Optional<ShortUrl> findByShortKey(String shorUrl);
}
