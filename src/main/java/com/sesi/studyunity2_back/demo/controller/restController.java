package com.sesi.studyunity2_back.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.sesi.studyunity2_back.demo.service.kakaoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.messaging.support.MessageBuilder;
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
    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;
    @Autowired
    private AmazonSNSClient amazonSNSClient;

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
    @RequestMapping(value = "/message",method =RequestMethod.POST )
    public void sendSqs(HttpServletRequest request,HttpServletResponse response) {
        logger.info("sendSqs");
        String url="https://sqs.ap-northeast-2.amazonaws.com/527222691614/testsqs";
        String message=request.getParameter("message");
        queueMessagingTemplate.send(url,MessageBuilder.withPayload(message).build());
        
    }
    @SqsListener("testsqs")
    public void loadMessage(String message) {
        logger.info("message: "+message);
    
        PublishRequest PublishRequest=new  PublishRequest("arn:aws:sns:ap-northeast-2:527222691614:test", message,"test제목");
        amazonSNSClient.publish(PublishRequest);
    }
    @RequestMapping(value = "/sns",method = RequestMethod.GET)
    public void awsSns(HttpServletRequest request) {
        logger.info("awsSns");
        //단체이메일 보내는법
        PublishRequest PublishRequest=new  PublishRequest("arn:aws:sns:ap-northeast-2:527222691614:test", "test본문내용","test제목");
        amazonSNSClient.publish(PublishRequest);

    }
}
