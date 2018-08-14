package com.yaochow.servicecore.service;

import com.yaochow.servicecore.service.impl.BlogServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "service-data", fallback = BlogServiceHystrix.class)
public interface BlogService {

    @RequestMapping(value = "/findAllBlogs", method = RequestMethod.GET)
    String findBlogs();

    @RequestMapping(value = "/findBlogById/{id}", method = RequestMethod.GET)
    String findById(@PathVariable("id") String id);

    @RequestMapping(value = "/addBlog", method = RequestMethod.POST)
    String addBlog(@RequestBody String blogStr);
}
