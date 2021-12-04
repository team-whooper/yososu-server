package com.whooper.model;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Inventory {
    private String addr; // 주유소 주소
    private String code; // 주유소 코드
    private String inventory; // 재고량
    private String lat; // 주유소 위도
    private String lng; // 주유소 경도
    private String name; // 주유소 이름
    private String openTime; // 영업시간
    private String price; // 요소수 가격
    //@JsonFormat(pattern = "yyyy-MM-dd")
    private String regDt; // 업데이트 일시
    private String tel; // 주유소 전화번호
    private String color; // 잔량 수량 구간

    public Inventory() {

    }

    public Inventory(String addr, String code, String inventory, String lat, String lng, String name, String openTime, String price, String regDt, String tel, String color) {
        this.addr = addr;
        this.code = code;
        this.inventory = inventory;
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.openTime = openTime;
        this.price = price;
        this.regDt = regDt;
        this.tel = tel;
        this.color = color;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRegDt() {
        return regDt;
    }

    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
