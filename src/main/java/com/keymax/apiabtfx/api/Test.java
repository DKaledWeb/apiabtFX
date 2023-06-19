package com.keymax.apiabtfx.api;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.keys.HmacKey;

import java.nio.charset.StandardCharsets;

public class Test {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        // Fill in the Token ID for your API token
        String tokenId = dotenv.get("TOKEN_ID");
        // Fill in the Secret Key for your API token
        String tokenSecret = dotenv.get("SECRET_KEY");

        // Update the requestUrl, if required:
        // If you log in to https://cc.absolute.com,
        // use https://api.absolute.com/jws/validate.
        // If you log in to https://cc.us.absolute.com,
        // use https://api.us.absolute.com/jws/validate.
        // If you log in to https://cc.eu2.absolute.com,
        // use https://api.eu2.absolute.com/jws/validate.
        String requestUrl = "https://api.absolute.com/jws/validate";
        OkHttpClient client = new OkHttpClient();

        // Example calling GET /reporting/devices endpoint with query parameters
        // Query parameters are URL encoded
        String uri = "/v3/reporting/devices";
//        String uri = "/v3/reporting/siem-events";
        String contentType = "application/json";
        String method = "GET";
        String queryString = "deviceUid=0b79b703-1ea7-4036-8a12-4c11f5ec9c0a&sortBy=deviceName&select=esn%2CoperatingSystem.name%2CdeviceUid%2CdeviceName%2CserialNumber%2Cbattery.estimatedChargeRemaining%2CfullSystemName%2Cusername%2CcurrentUsername%2ClastUpdatedDateTimeUtc"; //%2C == ,
//        String queryString = "pageSize=1&sortBy=deviceName:asc&deviceName=API&select=deviceUid%2CdeviceName%2CserialNumber%2CfullSystemName%2Cesn%2CagentStatus%2CsystemModel%2CsystemType";
        // For GET requests, the payload is empty
        // SIEM
//        LocalDateTime localDateTime = LocalDateTime.now(ZoneOffset.UTC);
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//        String nowDate = localDateTime.format(dateTimeFormatter);
//        String fromDateTime = localDateTime.toLocalDate().atStartOfDay().format(dateTimeFormatter);
//        String queryString = "pageSize=100&fromDateTimeUtc=" + fromDateTime + "&toDateTimeUtc=" + nowDate;
        String payload = "{}";

//        deviceUid = c22da536-4fff-4db9-912e-64c7c977be57

        // Example calling POST /actions/reach/get-actions endpoint with a payload
//        String uri = "/v3/actions/requests/eum";
//        String contentType = "application/json";
//        String method = "POST";
//        String queryString = "";
//        // The "data" key is important when you have a non-empty payload with POST
//        // This deviceUid is an example only
////         String payload = "{\"data\":{\"deviceUids\":[\"c22da536-4fff-4db9-912e-64c7c977be57\"]}}";
//        String payload = "{\"data\":{\n" +
//                "  \"deviceUids\": [\n" +
//                "    \"c22da536-4fff-4db9-912e-64c7c977be57\"\n" +
//                "  ],\n" +
//                "  \"scheduledDateTimeUtc\": \"2023-05-16T18:02:00Z\",\n" +
//                "  \"message\": \"Esto es un mensaje2\",\n" +
//                "  \"snoozeTimes\": 2,\n" +
//                "  \"displayMode\": \"Dialog\",\n" +
//                "  \"submitButtonCaption\": \"Submito\"\n" +
//                "}}";

        JsonWebSignature jws = new JsonWebSignature();
        jws.setHeader("alg", "HS256");
        jws.setHeader("kid", tokenId);
        jws.setHeader("issuedAt", System.currentTimeMillis());
        // Set the signature algorithm on the JWS that will integrity protect the payload
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);

        // Set the signing key on the JWS
        HmacKey key = new HmacKey(tokenSecret.getBytes(StandardCharsets.UTF_8));
        jws.setKey(key);
        jws.setDoKeyValidation(false); // relaxes the key length requirement

        jws.setHeader("method", method);
        jws.setHeader("content-type", contentType);
        jws.setHeader("uri", uri);
        jws.setHeader("query-string", queryString);
        jws.setPayload(payload);

        try{
            String jwsCompactSerialization = jws.getCompactSerialization();
            // Post it with okhttpclient
            RequestBody body = RequestBody.create(MediaType.parse("text/plain"), jwsCompactSerialization);

            Request request = new Request.Builder()
                    .url(requestUrl)
                    .post(body)
                    .build();

            Call call = client.newCall(request);
            Response response = call.execute();
            ResponseBody responseBody = response.body();
            System.out.println(response.code());
            assert responseBody != null;
            System.out.println(responseBody.string());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}