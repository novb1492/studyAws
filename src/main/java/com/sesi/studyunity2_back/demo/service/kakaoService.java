package com.sesi.studyunity2_back.demo.service;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class kakaoService {
    
    private Logger logger=LoggerFactory.getLogger(kakaoService.class);

    public String showPage(HttpServletRequest request) {
        logger.info("showPage");
        String scope=request.getParameter("scope");
        if(scope.equals("login")){
            return "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=af4a8c915e62c0115f59ed7b59f051d8&redirect_uri=http://localhost:8080/kakao/callback?scope=login";
        }
        throw new RuntimeException("지원하는 api가 존재하지 않습니다");
    }
}
