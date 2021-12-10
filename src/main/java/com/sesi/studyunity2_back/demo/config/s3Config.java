package com.sesi.studyunity2_back.demo.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class s3Config {
    
    //@Primary
    @Bean
    public AmazonS3 S3Client() {
        AWSCredentials  credentials=new BasicAWSCredentials("AKIAXVQHL24PHXPXKYU4", "Xzg2LcJrNnJmEdYV4H1eOqX7QBIPs1/ELrn9d51z");
        return AmazonS3ClientBuilder.standard()
                                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                                    .withRegion(Regions.AP_NORTHEAST_2).build();
    }
}
