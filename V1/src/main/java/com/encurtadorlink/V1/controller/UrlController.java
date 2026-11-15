package com.encurtadorlink.V1.controller;

import com.encurtadorlink.V1.Service.UrlService;
import com.encurtadorlink.V1.dto.ShortenUrlRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }


    @PostMapping(value = "/shorten-url")
    public String shortenUrl(@Valid @RequestBody ShortenUrlRequest shortenUrlRequest, HttpServletRequest servletRequest){
       return urlService.retornarUrl(shortenUrlRequest,servletRequest);
    }

    @GetMapping("{id}")
    public ResponseEntity<Void> redirect(@PathVariable("id") String id){

        var result = urlService.redirect(id);

        if (result.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.FOUND).headers(result).build();
    }
}
