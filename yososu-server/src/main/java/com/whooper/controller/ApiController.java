package com.whooper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whooper.model.YososuResponse;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@RestController
public class ApiController {

    private static String baseUrl = "https://api.odcloud.kr/api";
    private static String serviceKey = "cMLDVXTfZgJjwZAXo0nJhL6qkwytv3Tkvw2fywJZ3cSf68LQ2iZ9q7nPaP3T9AZRzEGgF4ToSPdNHoMG3YaJgQ%3D%3D";


    // 모든 주유소 조회
    @GetMapping("/getInventories")
    public @ResponseBody YososuResponse getInventories() throws IOException {

        StringBuilder urlBuilder = new StringBuilder(baseUrl+"/uws/v1/inventory");
        urlBuilder.append("?" + URLEncoder.encode("page", "UTF-8") + "=1");
        urlBuilder.append("&" + URLEncoder.encode("perPage", "UTF-8") + "=365");
        urlBuilder.append("&" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);

        YososuResponse  response = callApi(urlBuilder.toString());

        return response;
    }



    // 주소로 검색
    @GetMapping("/searchByAddr")
    public @ResponseBody YososuResponse searchByAddr(@RequestParam String addr) throws IOException {

        StringBuilder urlBuilder = new StringBuilder(baseUrl + "/uws/v1/inventory");
        urlBuilder.append("?" + URLEncoder.encode("page", "UTF-8") + "=1");
        urlBuilder.append("&" + URLEncoder.encode("perPage", "UTF-8") + "=365");
        urlBuilder.append("&" + URLEncoder.encode("cond[addr::LIKE]", "UTF-8") + "=" + addr);
        urlBuilder.append("&" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);


        YososuResponse response = callApi(urlBuilder.toString());

        return response;
    }



    // 모든 주유소 가격으로 정렬

    // 모든 주유소 재고량으로 정렬

    // 주소검색결과 가격으로 정렬

    // 주소검색결과 재고량으로 정렬



    public YososuResponse callApi(String url_str) throws IOException {
        URL url = new URL(url_str);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        System.out.println("url code: " + url);
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        ObjectMapper objectMapper = new ObjectMapper();
        YososuResponse response = objectMapper.readValue(sb.toString(), YososuResponse.class);


        System.out.println(sb.toString());
        System.out.println(response.getTotalCount());

        return response;
    }

}