package com.keymax.apiabtfx.interfaces;

public interface IAPIQuery {
    int pageSize = 500;
    String getUri();
    String getContentType();
    String getMethod();
    String getPayload();
    String getQuery();
}
