package com.vmware.acmecatalog.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthorizeResource implements HandlerInterceptor {

    private final RestTemplate restTemplate;
    @Value("${acmeServiceSettings.userServiceUrl}")
    private String userServiceUrl;

    public AuthorizeResource(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws SecurityException {

        if (handler instanceof HandlerMethod) {
            if (((HandlerMethod) handler).getMethod().getName().equals("createProduct")) {
                var accessTokenKey = "Authorization";
                var accessToken = request.getHeader(accessTokenKey);
                if (accessToken != null) {
                    accessToken = accessToken.replace("Bearer ", "");
                    return verifyToken(accessToken);
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    private Boolean verifyToken(String accessToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Map<String, String> map = new HashMap<>();
        map.put("access_token", accessToken);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<VerifyTokenResponse> response =
                this.restTemplate.postForEntity(userServiceUrl, entity, VerifyTokenResponse.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return true;
        } else {
            return false;
        }
    }

}
