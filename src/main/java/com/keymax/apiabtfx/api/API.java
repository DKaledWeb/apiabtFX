package com.keymax.apiabtfx.api;

import com.keymax.apiabtfx.ApiRequestException;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class API {
    private final Dotenv dotenv = Dotenv.load();
    private final String tokenId = dotenv.get("TOKEN_ID");
    private final String tokenSecret = dotenv.get("SECRET_KEY");

    public API() {

    }

    public String doRequest(String[] pJwsValues) {
        JsonWebSignature jws = createJws();
        jws.setHeader("method", pJwsValues[0]);
        jws.setHeader("content-type", pJwsValues[1]);
        jws.setHeader("uri", pJwsValues[2]);
        jws.setHeader("query-string", pJwsValues[3]);
        jws.setPayload(pJwsValues[4]);

        OkHttpClient client = new OkHttpClient();

        try (Response response = client.newCall(createRequest(jws)).execute()) {
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                return responseBody.string();
            }
        } catch (JoseException | IOException e) {
            throw new ApiRequestException("Error executing API request", e);
        }

        return "Error with data...";
    }

    private JsonWebSignature createJws(){
        JsonWebSignature jws = new JsonWebSignature();
        jws.setHeader("alg", "HS256");
        jws.setHeader("kid", this.tokenId);
        jws.setHeader("issuedAt", System.currentTimeMillis());
        // Set the signature algorithm on the JWS that will integrity protect the payload
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);

        // Set the signing key on the JWS
        HmacKey key = new HmacKey(this.tokenSecret.getBytes(StandardCharsets.UTF_8));
        jws.setKey(key);
        jws.setDoKeyValidation(false); // relaxes the key length requirement
        return jws;

    }

    private Request createRequest(JsonWebSignature jws) throws JoseException {
        String jwsCompactSerialization = jws.getCompactSerialization();
        RequestBody body = RequestBody.create(jwsCompactSerialization, MediaType.parse("text/plain"));

        String requestUrl = "https://api.absolute.com/jws/validate";
        return new Request.Builder().url(requestUrl).post(body).build();
    }


}
