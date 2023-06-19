package com.keymax.apiabtfx.api;

import com.keymax.apiabtfx.interfaces.IAPIQuery;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class SIEM extends Thread implements IAPIQuery {
    private String fromDate;
    private String toDate;
    private final LocalDateTime localDateTime;
    private final DateTimeFormatter dateTimeFormatter;

    public SIEM() {
        this.localDateTime = LocalDateTime.now(ZoneOffset.UTC);
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        setFromDate();
        setToDate();
    }

    @Override
    public String getPayload() {
        return "{}";
    }

    @Override
    public String getQuery() {
        return "pageSize=" + pageSize + "&fromDateTimeUtc=" + this.fromDate + "&toDateTimeUtc=" + this.toDate;
    }

    @Override
    public String getUri() {
        return "/v3/reporting/siem-events";
    }

    @Override
    public String getContentType() {
        return "application/json";
    }

    @Override
    public String getMethod() {
        return "GET";
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate() {
        this.fromDate = localDateTime.toLocalDate().atStartOfDay().format(dateTimeFormatter);
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate() {
        this.toDate = localDateTime.format(dateTimeFormatter);
    }

}