package com.edwinrhc.paymentservice.utils;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PaymentUtils {

    private PaymentUtils(){

    }

    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus status){
        return new ResponseEntity<>("{\"message\":\""+responseMessage+"\"}", status);
    }
    public static String getUUID(){
        Date date = new Date();
        long time = date.getTime();
        return "BILL-"+ time;
    }

}
