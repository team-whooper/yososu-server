package com.whooper.model;

import java.util.List;

public class YososuResponse {
    int currentCount;
    List<Inventory> data;
    int matchCount;
    int page;
    int perPage;
    int totalCount;

    public  YososuResponse() {

    }

    public YososuResponse(int currentCount, List<Inventory> data, int matchCount, int page, int perPage, int totalCount) {
        this.currentCount = currentCount;
        this.data = data;
        this.matchCount = matchCount;
        this.page = page;
        this.perPage = perPage;
        this.totalCount = totalCount;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public List<Inventory> getData() {
        return data;
    }

    public void setData(List<Inventory> data) {
        this.data = data;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
