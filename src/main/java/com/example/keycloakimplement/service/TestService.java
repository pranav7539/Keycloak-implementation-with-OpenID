package com.example.keycloakimplement.service;

import com.example.keycloakimplement.model.RequestLogin;
import com.example.keycloakimplement.model.RequestLogout;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TestService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${keycloak.token-uri}")
    private String keycloakTokenUri;

    @Value("${keycloak.logout}")
    private String keycloakLogout;

    @Value("${keycloak.user-info-uri}")
    private String keycloakUserInfo;

    public Map login(RequestLogin requestLogin) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", requestLogin.getUsername());
        map.add("password", requestLogin.getPassword());
        map.add("client_id", requestLogin.getClient_id());
        map.add("grant_type", requestLogin.getGrant_type());
        map.add("client_secret", requestLogin.getClient_secret());

        HttpEntity<MultiValueMap<String, String>> mapHttpEntity = new HttpEntity<>(map, new HttpHeaders());
        return restTemplate.postForObject(keycloakTokenUri, mapHttpEntity, Map.class);
    }

    public void logout(RequestLogout requestLogout) throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id",requestLogout.getClient_id());
        map.add("client_secret",requestLogout.getClient_secret());
        map.add("refresh_token",requestLogout.getRefresh_token());

        HttpEntity<MultiValueMap<String, String>> mapHttpEntity = new HttpEntity<>(map, null);
        restTemplate.postForObject(keycloakLogout, mapHttpEntity, String.class);
    }

    public List<String> getRoles(String token) throws Exception {
        String response = getUserInfo(token);

        Map map = new ObjectMapper().readValue(response, HashMap.class);
        return (List<String>) map.get("roles");
    }

    private String getUserInfo(String token) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", token);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
        return restTemplate.postForObject(keycloakUserInfo, request, String.class);
    }
}
