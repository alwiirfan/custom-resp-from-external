package com.alwi.rest_template.util;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BearerTokenInterceptor implements ClientHttpRequestInterceptor {

    private String token;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        HttpHeaders headers = request.getHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        return execution.execute(request, body);
    }

}
