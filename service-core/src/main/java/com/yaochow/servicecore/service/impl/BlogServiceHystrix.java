package com.yaochow.servicecore.service.impl;


import com.yaochow.servicecore.service.BlogService;
import org.springframework.stereotype.Component;

@Component
public class BlogServiceHystrix implements BlogService {

    @Override
    public String findBlogs() {
        return "find blog failed";
    }

    @Override
    public String findById(String id) {
        return "findById failed";
    }

    @Override
    public String addBlog(String blogStr) {
        return "add blog failed";
    }
}
