package com.steamstatistics.data;

public class RestMessageModel {
    private String status, request;
    private Object message;

    public RestMessageModel(String status, String request) {
        this.status = status;
        this.request = request;
    }

    public RestMessageModel(String status, String request, Object message) {
        this.status = status;
        this.request = request;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
