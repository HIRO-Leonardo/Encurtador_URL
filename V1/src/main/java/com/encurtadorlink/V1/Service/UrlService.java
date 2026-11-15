package com.encurtadorlink.V1.Service;


import com.encurtadorlink.V1.dto.ShortenUrlRequest;
import com.encurtadorlink.V1.entity.UrlEntity;
import com.encurtadorlink.V1.repository.UrlRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class UrlService {


    private final UrlRepository url;
    private final MongoTemplate mongoTemplate;

    public UrlService(UrlRepository url, MongoTemplate mongoTemplate) {
        this.url = url;
        this.mongoTemplate = mongoTemplate;
    }


    public String retornarUrl(ShortenUrlRequest shortenUrlRequest, HttpServletRequest servletRequest){
        System.out.println("=== SHORTEN URL CHAMADO ===");
        System.out.println("URL: " + shortenUrlRequest.url());

        String id;

        do{
            id = RandomStringUtils.randomAlphabetic(5,10);
        }while (url.existsById(id));

        var saved = url.save(new UrlEntity(id, shortenUrlRequest.url(), Instant.now().plus(1, ChronoUnit.HOURS)));

        var redirectUrl = servletRequest.getRequestURL().toString().replace("shorten-url",id);
        System.out.println("SALVO: " + saved);
        System.out.println("DB: " + mongoTemplate.getDb().getName());
        System.out.println("CLUSTER: " + mongoTemplate.getMongoDatabaseFactory()
                .getMongoDatabase().getName());
        System.out.println("COUNT: " + mongoTemplate.getCollection("urls").countDocuments());
        return redirectUrl;
    }


    public
    HttpHeaders redirect(String id){
        var url1 =  url.findById(id);

        HttpHeaders headers = new HttpHeaders();

        headers.setLocation(URI.create(url1.get().getFullUrl()));
        return headers;
    }
}
