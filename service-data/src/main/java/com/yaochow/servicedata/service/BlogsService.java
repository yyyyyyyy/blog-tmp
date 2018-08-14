package com.yaochow.servicedata.service;

import com.yaochow.servicedata.entity.Blogs;

import java.util.List;

public interface BlogsService {
    List<Blogs> findAll();
    Blogs findOneById(String id);
    Blogs addOne(Blogs blogs);
}
