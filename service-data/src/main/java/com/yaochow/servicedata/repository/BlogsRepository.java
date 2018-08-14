package com.yaochow.servicedata.repository;

import com.yaochow.servicedata.entity.Blogs;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogsRepository extends MongoRepository<Blogs, String> {

}
