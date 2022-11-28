package com.example.keycloakimplement.model;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestLogin {

    private String username;
    private String password;
    private String client_id;
    private String grant_type;
    private String client_secret;
}
