package com.keymax.apiabtfx.api;

import com.keymax.apiabtfx.interfaces.IAPIQuery;

public class DeviceReporting implements IAPIQuery {
//    private static final int PAGE_SIZE = 500;

    private final String[] arrSelect = {
            "deviceName",
            "deviceStatus",
            "serialNumber",
            "username"
    };
    public DeviceReporting() {

    }

    @Override
    public String getQuery() {
        return "pageSize=" + pageSize + "&" + getSortBy() + "&" + ArrSelectToString();
    }

    @Override
    public String getUri() {
        return "/v3/reporting/devices";
    }

    @Override
    public String getContentType() {
        return "application/json";
    }

    @Override
    public String getMethod() {
        return "GET";
    }

    @Override
    public String getPayload() {
        return "{}";
    }

    private String getSortBy() {
        return String.format("sortBy=%s:asc", "deviceName");
    }

    private String ArrSelectToString() {
        return "select=" + String.join("%2C", arrSelect);
    }
}
