package com.yaochow.servicedata;

import com.alibaba.fastjson.JSONObject;
import com.yaochow.servicedata.entity.Blogs;
import com.yaochow.servicedata.repository.BlogsRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServiceDataApplication.class)
public class MongodbTest {

    @Autowired
    private BlogsRepository blogsRepository;

    @Test
    public void testFindAll() {
        List<Blogs> all = blogsRepository.findAll();
        log.info("test result size : {}", all != null ? all.size() : 0);
    }

    @Test
    public void testFindOne(){
        Optional<Blogs> resultOne = blogsRepository.findById("5b6703c120cccc0e7d1e116d");
        log.info("test resultOne : {}", resultOne.isPresent());
    }
}
