package com.sesi.studyunity2_back.demo.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class snsConfig {
    
    @Primary
    @Bean
    public AmazonSNSClient amazonSNSClient() {
        return (AmazonSNSClient)AmazonSNSClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_2)
        .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("AKIAXVQHL24PHXPXKYU4", "Xzg2LcJrNnJmEdYV4H1eOqX7QBIPs1/ELrn9d51z"))).build();
    }
}
