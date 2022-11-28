package com.example.keycloakimplement.controller;

import com.example.keycloakimplement.model.RequestLogin;
import com.example.keycloakimplement.model.RequestLogout;
import com.example.keycloakimplement.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    private TestService restService;

    @PostMapping(value = "/login")
    public Map login(@RequestBody RequestLogin requestLogin) {
        return restService.login(requestLogin);
    }

    @PostMapping(value = "/logout")
    public String logout(@RequestBody RequestLogout requestLogout) throws Exception {
        restService.logout(requestLogout);
        return "logout";
    }

    @GetMapping("/dashboard")
    public String dashboard(@RequestHeader("Authorization") String authHeader) throws Exception {
        List<String> roles = restService.getRoles(authHeader);
        if(!roles.contains("beneficiary"))
            throw new Exception("Not a beneficiary role");
        return "Welcome To Dashboard Page";
    }

    @GetMapping("/profile")
    public String profile(@RequestHeader("Authorization") String authHeader) throws Exception {
        List<String> roles = restService.getRoles(authHeader);
        if(!roles.contains("beneficiary"))
            throw new Exception("Not a beneficiary role");
        return "Welcome To Profile Page";
    }

    @GetMapping("/request")
    public String request(@RequestHeader("Authorization") String authHeader) throws Exception {
        List<String> roles = restService.getRoles(authHeader);
        if(!roles.contains("beneficiary"))
            throw new Exception("Not a beneficiary role");
        return "Welcome To Request Page";
    }
}
