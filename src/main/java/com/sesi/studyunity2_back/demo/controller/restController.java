package com.sesi.studyunity2_back.demo.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.sesi.studyunity2_back.demo.model.dao;
import com.sesi.studyunity2_back.demo.service.kakaoService;

import org.mybatis.spring.annotation.MapperScan;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@MapperScan(basePackages = "com.sesi.studyunity2_back.demo")
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
    @Autowired
    private AmazonS3 amazonS3;
    @Autowired
    private dao dao;

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
    public void loadMessage(String message,String kind) {
        logger.info("message: "+message);
    
        PublishRequest PublishRequest=new  PublishRequest("arn:aws:sns:ap-northeast-2:527222691614:test", message,"test제목");
        amazonSNSClient.publish(PublishRequest);
    }
    @RequestMapping(value = "/sns",method = RequestMethod.POST)
    public void awssns2(HttpServletRequest request) {
        logger.info("awssns2");
     
        
    }
    @RequestMapping(value = "/testmy",method = RequestMethod.GET)
    public void testmy() {
        logger.info("testmy");
        List<Map<String,Object>>maps=dao.selectAll();
        logger.info(maps.toString());
    }
    @RequestMapping(value = "/sns",method = RequestMethod.GET)
    public void awsSns(HttpServletRequest request) {
        logger.info("awsSns");
        //단체이메일 보내는법
        PublishRequest PublishRequest=new  PublishRequest("arn:aws:sns:ap-northeast-2:527222691614:test", "test본문내용","test제목");
        amazonSNSClient.publish(PublishRequest);

    }
    @RequestMapping(value = "/upload",method = RequestMethod.GET)
    public void upload(MultipartHttpServletRequest request) {
        logger.info("upload");
        uploadImage(request.getFile("file"),"kimsshop/images");
    }
    public String uploadImage(MultipartFile multipartFile,String bucketName) {
        logger.info("uploadImage");
        File file=convert(multipartFile);
        String saveName=file.getName();
        amazonS3.putObject(bucketName,saveName, file);
        file.delete();
        logger.info("파일업로드 완료");
        return saveName;
    }
    public void deleteFile(String bucktetName,String fileName) {
        logger.info("deleteFile");
        amazonS3.deleteObject(bucktetName, fileName);
    }
    private File convert(MultipartFile multipartFile) {
        logger.info("convert");
        File file=new File(LocalDate.now().toString()+UUID.randomUUID()+multipartFile.getOriginalFilename());
        try(FileOutputStream fileOutputStream=new FileOutputStream(file)){
            fileOutputStream.write(multipartFile.getBytes()); 
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        return file;
    }
}
