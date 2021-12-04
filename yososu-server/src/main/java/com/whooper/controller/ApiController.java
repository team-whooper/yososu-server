package com.whooper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whooper.model.Inventory;
import com.whooper.model.YososuResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ApiController {

    private static String baseUrl = "https://api.odcloud.kr/api";
    private static String serviceKey = "cMLDVXTfZgJjwZAXo0nJhL6qkwytv3Tkvw2fywJZ3cSf68LQ2iZ9q7nPaP3T9AZRzEGgF4ToSPdNHoMG3YaJgQ%3D%3D";

//    private WebClient webClient = WebClient.create(baseUrl);

    @GetMapping("/getInventories")
    public String getInventories() throws IOException {

        StringBuilder urlBuilder = new StringBuilder(baseUrl+"/uws/v1/inventory");
        urlBuilder.append("?" + URLEncoder.encode("page", "UTF-8") + "=1");
        urlBuilder.append("&" + URLEncoder.encode("perPage", "UTF-8") + "=111");
        urlBuilder.append("&" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        System.out.println("url code: " + url);
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(),"UTF-8"));
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
        System.out.println(response.getData().get(2).getName());
        return sb.toString();


//        List<YososuResponse> result = webClient.get().uri(uriBuilder -> uriBuilder.path("/uws/v1/inventory")
//                .queryParam("page", 1)
//                .queryParam("perPage", 111)
//                .queryParam("serviceKey", serviceKey)
//                .build()
//        ).accept(MediaType.APPLICATION_JSON).retrieve().bodyToFlux(YososuResponse.class).toStream().collect(Collectors.toList());

        //YososuResponse response = result.block();

//        String apiUrl = UriComponentsBuilder.newInstance().scheme("https")
//                .host(baseUrl)
//                .path("/uws/v1/inventory")
//                .queryParam("page", 1)
//                .queryParam("perPage", 111)
//                .queryParam("serviceKey", serviceKey)
//                .build()
//                .encode()
//                .toString();
//
//
//        ResponseEntity<YososuResponse> result = restTemplate.exchange(apiUrl, HttpMethod.GET, null, new ParameterizedTypeReference<YososuResponse>() {
//        });

//        System.out.println("SUCCESS!: " + result.size());
//        return "SUCCESS!";



    }
}