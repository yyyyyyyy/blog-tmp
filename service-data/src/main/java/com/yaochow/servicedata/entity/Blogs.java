package com.yaochow.servicedata.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "blogs")
@Data
public class Blogs {
    @Id
    private String id;
    @Field("topic")
    private String topic;
    @Field("content")
    private String content;
}
