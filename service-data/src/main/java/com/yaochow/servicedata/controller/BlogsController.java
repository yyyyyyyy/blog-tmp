package com.yaochow.servicedata.controller;

import com.alibaba.fastjson.JSONObject;
import com.yaochow.servicedata.entity.Blogs;
import com.yaochow.servicedata.service.BlogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class BlogsController {

    @Autowired
    BlogsService blogsServiceImpl;

    @RequestMapping(value = "/findAllBlogs", method = RequestMethod.GET)
    public String findAll() {
        Long start = System.currentTimeMillis();
        log.info("findAll");
        String result = JSONObject.toJSONString(blogsServiceImpl.findAll());
        log.info("result : {}, cost : {}", result, System.currentTimeMillis() - start);
        return result;
    }

    @RequestMapping(value = "/findBlogById/{id}", method = RequestMethod.GET)
    public String findOne(@PathVariable String id) {
        Long start = System.currentTimeMillis();
        log.info("findById : {}", id);
        String result = JSONObject.toJSONString(blogsServiceImpl.findOneById(id));
        log.info("result : {}, cost : {}", result, System.currentTimeMillis() - start);
        return result;
    }

    @RequestMapping(value = "/addBlog", method = RequestMethod.POST)
    public String addBlog(@RequestBody String blogStr) {
        Long start = System.currentTimeMillis();
        log.info("addBlog : {}", blogStr);
        if (!StringUtils.isEmpty(blogStr)) {
            Blogs blogs = blogsServiceImpl.addOne(JSONObject.parseObject(blogStr, Blogs.class));
            String result = JSONObject.toJSONString(blogs);
            log.info("result : {}, cost : {}", result, System.currentTimeMillis() - start);
            return result;
        } else {
            return "{'success':'false','errorMsg':'param is null'}";
        }
    }
}
