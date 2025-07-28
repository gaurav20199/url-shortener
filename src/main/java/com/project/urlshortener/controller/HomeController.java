package com.project.urlshortener.controller;

import com.project.urlshortener.ApplicationProperties;
import com.project.urlshortener.dto.ShortUrlCommand;
import com.project.urlshortener.dto.ShortUrlDto;
import com.project.urlshortener.dto.UrlForm;
import com.project.urlshortener.service.ShortUrlService;
import jakarta.validation.Valid;
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

    private void addToModel(Model model, int page) {
        List<ShortUrlDto> shortUrls = shortUrlService.findAllPublicUrls(page,applicationProperties.pageSize());
        model.addAttribute("shortUrls",shortUrls);
        model.addAttribute("baseUrl",applicationProperties.baseUrl());
        model.addAttribute("isPublic",true);
    }

    @GetMapping("/")
    public String showHomePage(@RequestParam(defaultValue = "1") Integer page, Model model) {
        addToModel(model,page);
        model.addAttribute("urlInputForm",new UrlForm(""));
        return "index";
    }

    @PostMapping("/create/short-url")
    public String createShortUrl(@ModelAttribute("urlInputForm") @Valid UrlForm urlForm, BindingResult bindingResult,
                                 RedirectAttributes attributes, Model model) {
        if(bindingResult.hasErrors()){
            addToModel(model,1);
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
