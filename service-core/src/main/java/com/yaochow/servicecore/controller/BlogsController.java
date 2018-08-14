package com.yaochow.servicecore.controller;

import com.yaochow.servicecore.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class BlogsController {

    private Logger logger = Logger.getLogger(BlogsController.class.getName());
    @Autowired
    BlogService blogService;

    @RequestMapping("/findAllBlogs")
    public String findAllBlogs() {
        String result = blogService.findBlogs();
        logger.log(Level.INFO, "findAllBlogs : " + result);
        return result;
    }

    @RequestMapping("/findBlogById/{id}")
    public String findBlogById(@PathVariable String id) {
        String result = blogService.findById(id);
        logger.log(Level.INFO, "findOne : " + result);
        return result;
    }

    @RequestMapping(value = "/addBlog")
    public String addBlog(@RequestBody String request) {
        logger.log(Level.INFO, "addBlog request : " + request);
        String result = blogService.addBlog(request);
        logger.log(Level.INFO, "addBlog resut : " + result);
        return result;
    }
}
