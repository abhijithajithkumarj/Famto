package com.Famto.Famto.config;


import com.Famto.Famto.entity.User;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {


    private String pass;
    private String role;
    private String user;


}
