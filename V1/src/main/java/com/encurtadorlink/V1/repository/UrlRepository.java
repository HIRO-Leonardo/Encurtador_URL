package com.encurtadorlink.V1.repository;

import com.encurtadorlink.V1.entity.UrlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepository extends MongoRepository<UrlEntity,String> {

}
