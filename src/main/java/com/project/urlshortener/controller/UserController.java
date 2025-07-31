package com.project.urlshortener.controller;

import com.project.urlshortener.ApplicationProperties;
import com.project.urlshortener.dto.PageResult;
import com.project.urlshortener.dto.ShortUrlDto;
import com.project.urlshortener.entity.ShortUrl;
import com.project.urlshortener.entity.User;
import com.project.urlshortener.repository.UserRepository;
import com.project.urlshortener.service.ShortUrlService;
import com.project.urlshortener.utils.SecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserController {

    private SecurityUtil securityUtil;

    private ShortUrlService shortUrlService;

    private ApplicationProperties properties;

    public UserController(SecurityUtil securityUtil, ShortUrlService shortUrlService, ApplicationProperties properties) {
        this.securityUtil = securityUtil;
        this.shortUrlService = shortUrlService;
        this.properties = properties;
    }


    @GetMapping("/login")
    public String showLoginForm(){
        return "login";
    }

    @GetMapping("/user/urls")
    public String showUrls(@RequestParam(defaultValue = "1") int page, Model model){
        Optional<User> authenticatedUser = securityUtil.getAuthenticatedUser();
        PageResult<ShortUrlDto> urlsForCurrentUser = shortUrlService.findAllUrlsForUser(page, authenticatedUser, properties.pageSize());
        model.addAttribute("shortUrls",urlsForCurrentUser);
        model.addAttribute("baseUrl",properties.baseUrl());
        model.addAttribute("paginationUrl","/user/urls");
        return "user-dashboard";
    }

}
