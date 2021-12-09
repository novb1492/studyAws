package com.sesi.studyunity2_back.demo;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class utillService {
    
    private static Logger logger=LoggerFactory.getLogger(utillService.class);

    public static JSONObject makeJson(boolean flag,String message) {
        logger.info("makeJson");
        JSONObject response=new JSONObject();
        response.put("flag",flag);
        response.put("message", message);
        return response;
    }
}
