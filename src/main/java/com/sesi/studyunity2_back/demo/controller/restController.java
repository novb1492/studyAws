package com.sesi.studyunity2_back.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sesi.studyunity2_back.demo.service.kakaoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class restController {
    
    private Logger logger=LoggerFactory.getLogger(restController.class);

    @Autowired
    private kakaoService kakaoService;
    @Autowired
    private RedisTemplate<String,String>redisTemplate;

    @RequestMapping(value = "/api/kakao/**",method = RequestMethod.GET)
    public String callApi(HttpServletRequest request,HttpServletResponse response) {
        logger.info("callApi");
        try {
            return  kakaoService.showPage(request);
        } catch (Exception e) {
            return null;
        }
    }
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public void test(HttpServletRequest request) {
        logger.info("test controller");
        ValueOperations<String,String>valueOperations=redisTemplate.opsForValue();
        valueOperations.set("b", "testb");

        String s=valueOperations.get("b");
        logger.info(s);
    }
}
