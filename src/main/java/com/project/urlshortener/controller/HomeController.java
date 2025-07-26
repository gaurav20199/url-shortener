package com.project.urlshortener.controller;

import com.project.urlshortener.ApplicationProperties;
import com.project.urlshortener.dto.ShortUrlCommand;
import com.project.urlshortener.dto.ShortUrlDto;
import com.project.urlshortener.dto.UrlForm;
import com.project.urlshortener.entity.ShortUrl;
import com.project.urlshortener.repository.ShortUrlRepository;
import com.project.urlshortener.service.ShortUrlService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HomeController {

    private final ShortUrlService shortUrlService;
    private final ApplicationProperties applicationProperties;

    public HomeController(ShortUrlService shortUrlService,ApplicationProperties applicationProperties) {
        this.shortUrlService = shortUrlService;
        this.applicationProperties = applicationProperties;
    }

    @GetMapping("/")
    public String showHomePage(Model model) {
        List<ShortUrlDto> shortUrls = shortUrlService.findAllPublicUrls();
        model.addAttribute("shortUrls",shortUrls);
        model.addAttribute("baseUrl","http://localhost:8080");
        model.addAttribute("isPublic",true);
        model.addAttribute("urlInputForm",new UrlForm(""));
        return "index";
    }

    @PostMapping("/create/short-url")
    public String createShortUrl(@ModelAttribute("urlInputForm") @Valid UrlForm urlForm, BindingResult bindingResult,
                                 RedirectAttributes attributes, Model model) {
        if(bindingResult.hasErrors()){
            List<ShortUrlDto> shortUrls = shortUrlService.findAllPublicUrls();
            model.addAttribute("shortUrls",shortUrls);
            model.addAttribute("baseUrl",applicationProperties.baseUrl());
            model.addAttribute("isPublic",true);
            return "index";
        }
        try {
            ShortUrlCommand urlCommand = new ShortUrlCommand(urlForm.originalUrl());
            ShortUrlDto shortUrl = shortUrlService.createShortUrl(urlCommand);
            attributes.addFlashAttribute("successMessage", "Shorturl generated successfully::"+
                    applicationProperties.baseUrl()+"/s/"+shortUrl.shortKey());
        }catch (Exception e) {
            attributes.addFlashAttribute("errorMessage", "Shorurl generation failed");
        }
        return "redirect:/";

    }

    @GetMapping("/s/{shortUrl}")
    public String accessShortUrl(@PathVariable String shortUrl) {
        String originalUrl = shortUrlService.getOriginalUrl(shortUrl);
        return "redirect:"+originalUrl;

    }
}
