package com.keymax.apiabtfx;

import com.keymax.apiabtfx.api.API;
import com.keymax.apiabtfx.api.SIEM;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DeviceDetectionSIEM implements Runnable {
    private final static long PERIOD_INTERVAL = 5; // Seconds
    private final ScheduledExecutorService scheduledExecutorService;
    private boolean threadAlive;


    public DeviceDetectionSIEM() {
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        this.threadAlive = true;
    }

    @Override
    public void run() {
        this.scheduledExecutorService.scheduleAtFixedRate(this::SIEMSearch, PERIOD_INTERVAL, PERIOD_INTERVAL, TimeUnit.SECONDS);
    }

    private void SIEMSearch() {
        if (!this.threadAlive) {
            this.scheduledExecutorService.shutdown();
            return;
        }

        System.out.println("Searching username changes...");
        SIEM siem = new SIEM();
        String[] siemData = {siem.getMethod(),siem.getContentType(),siem.getUri(),siem.getQuery(),siem.getPayload()};
        String response = new API().doRequest(siemData);
        System.out.println(response);
    }

    public void setThreadAlive(boolean threadAlive) {
        this.threadAlive = threadAlive;
    }
}
