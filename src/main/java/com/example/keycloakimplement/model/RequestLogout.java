package com.example.keycloakimplement.model;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestLogout {

    private String refresh_token;
    private String client_id;
    private String client_secret;
}
