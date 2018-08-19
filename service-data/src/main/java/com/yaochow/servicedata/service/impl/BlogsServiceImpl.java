package com.yaochow.servicedata.service.impl;

import com.yaochow.servicedata.entity.Blogs;
import com.yaochow.servicedata.repository.BlogsRepository;
import com.yaochow.servicedata.service.BlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogsServiceImpl implements BlogsService {

    @Autowired
    private BlogsRepository blogsRepository;

    @Override
    public List<Blogs> findAll() {
        List<Blogs> blogs = blogsRepository.findAll();
        blogs.forEach(blog -> blog.setContent(null));
        return blogs;
    }

    @Override
    public Blogs findOneById(String id) {
        Optional<Blogs> result = blogsRepository.findById(id);
        return result.isPresent() ? result.get() : null;
    }

    @Override
    public Blogs addOne(Blogs blogs) {
        return blogsRepository.insert(blogs);
    }
}
