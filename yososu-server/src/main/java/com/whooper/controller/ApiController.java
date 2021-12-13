package com.whooper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whooper.model.Inventory;
import com.whooper.model.YososuResponse;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collections;


@CrossOrigin("*")
@RestController
public class ApiController {

    private static String baseUrl = "https://api.odcloud.kr/api";
    private static String serviceKey = "cMLDVXTfZgJjwZAXo0nJhL6qkwytv3Tkvw2fywJZ3cSf68LQ2iZ9q7nPaP3T9AZRzEGgF4ToSPdNHoMG3YaJgQ%3D%3D";
    private static int TOTALCOUNT = 1; // 요소수거점주유소 수


    // 재고량으로 정렬 (default)
    @GetMapping("/inventories/stock")
    public @ResponseBody YososuResponse getInventoriesInStockOrder(@RequestParam(required=false) String addr) throws IOException {
        System.out.println("Request URL: /inventories/stock?addr=" + addr);

        setTotalCount();

        StringBuilder urlBuilder = new StringBuilder(baseUrl+"/uws/v1/inventory");
        urlBuilder.append("?" + URLEncoder.encode("page", "UTF-8") + "=1");
        urlBuilder.append("&" + URLEncoder.encode("perPage", "UTF-8") + "=" + TOTALCOUNT);
        if (addr != null) {
            urlBuilder.append("&" + URLEncoder.encode("cond[addr::LIKE]", "UTF-8") + "=" + addr);
        }
        urlBuilder.append("&" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);

        YososuResponse  response = callApi(urlBuilder.toString());


        // 재고량으로 정렬
        Collections.sort(response.getData(), (o1, o2) -> {

            if (Integer.parseInt(o1.getInventory().trim()) > Integer.parseInt(o2.getInventory().trim())) {
                return -1;
            } else if(Integer.parseInt(o1.getInventory().trim()) < Integer.parseInt(o2.getInventory().trim())) {
                return 1;
            } else {
                return 0;
            }
        });
        

        return response;
    }


    // 가격으로 정렬
    @GetMapping("/inventories/price")
    public @ResponseBody YososuResponse getInventoriesInPriceOrder(@RequestParam(required=false) String addr) throws IOException {
        System.out.println("Request URL: /inventories/price?addr=" + addr);

        setTotalCount();

        StringBuilder urlBuilder = new StringBuilder(baseUrl+"/uws/v1/inventory");
        urlBuilder.append("?" + URLEncoder.encode("page", "UTF-8") + "=1");
        urlBuilder.append("&" + URLEncoder.encode("perPage", "UTF-8") + "=" + TOTALCOUNT);
        if (addr != null) {
            urlBuilder.append("&" + URLEncoder.encode("cond[addr::LIKE]", "UTF-8") + "=" + addr);
        }
        urlBuilder.append("&" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);

        YososuResponse  response = callApi(urlBuilder.toString());


        // 가격으로 정렬
        Collections.sort(response.getData(), (o1, o2) -> {
            String str1 = o1.getPrice().trim();
            String str2 = o2.getPrice().trim();

            if (str1 == "정보없음") {
                if (str2 == "정보없음") {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                if (str2 == "정보없음") {
                    return -1;
                } else {
                    int price1 = Integer.parseInt(str1);
                    int price2 = Integer.parseInt(str2);

                    if (price1 > price2) {
                        return 1;
                    } else if(price1 < price2) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            }

        });

        return response;
    }


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


        for (Inventory inventory: response.getData()) {

            // 영업시간정보가 null이면 "정보없음"으로 바꿔서 내려줌
            if (inventory.getOpenTime() == null) {
                inventory.setOpenTime("정보없음");
            }

            // 가격정보가 null이거나 숫자가 아니면 "정보없음"으로 바꿔서 내려줌
            if (!isProper(inventory.getPrice())) {
                System.out.println("price: " + inventory.getPrice());
                inventory.setPrice("정보없음");
            }



        }

        System.out.println("Match Count: " + response.getMatchCount());

        return response;
    }

    public void setTotalCount() throws IOException {
        StringBuilder urlBuilder = new StringBuilder(baseUrl+"/uws/v1/inventory");
        urlBuilder.append("?" + URLEncoder.encode("page", "UTF-8") + "=1");
        urlBuilder.append("&" + URLEncoder.encode("perPage", "UTF-8") + "=1");
        urlBuilder.append("&" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);

        YososuResponse  response = callApi(urlBuilder.toString());

        TOTALCOUNT = response.getTotalCount();
    }

    // 가격정보가 null이거나 숫자가 아니면 false 리턴
    public Boolean isProper(String price) {
        boolean flag = true;
        if (price == null) {
            return false;
        } else if (price.equals("0")) {
            return false;
        } else {
            char[] charArr = price.toCharArray();
            for (char c : charArr) {
                if (!Character.isDigit(c)) {
                    flag = false;
                }
            }
            if (!flag) {
                return false;
            } else {
                return true;
            }
        }

    }

}