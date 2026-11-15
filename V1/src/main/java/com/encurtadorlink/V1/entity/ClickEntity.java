package com.encurtadorlink.V1.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clicks")
public class ClickEntity {

    @Id
    private String id;


    private String urlId;
}
