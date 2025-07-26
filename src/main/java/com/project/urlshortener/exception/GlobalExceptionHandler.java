package com.project.urlshortener.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidUrl.class)
    public ModelAndView handleInvalidUrl(InvalidUrl ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("status", ex.getStatus());
        modelAndView.addObject("error", ex.getMessage());
        modelAndView.addObject("message", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(ExpiredUrl.class)
    public ModelAndView handleInvalidUrl(ExpiredUrl ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("status", ex.getStatus());
        modelAndView.addObject("error", ex.getMessage());
        modelAndView.addObject("message", ex.getMessage());
        return modelAndView;
    }
}
