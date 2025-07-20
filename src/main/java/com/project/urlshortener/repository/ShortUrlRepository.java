package com.project.urlshortener.repository;

import com.project.urlshortener.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    @Query("select u from ShortUrl u where u.isPrivate=false order by u.createdAt desc")
    List<ShortUrl> findAllPublicUrls();
}
