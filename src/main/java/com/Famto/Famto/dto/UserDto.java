package com.Famto.Famto.dto;


import com.Famto.Famto.entity.Role;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String username;
    private String password;
    private String email;
    private String shopName;
    private String phoneNumber;
    private String category;
    private String ownerName;
    private String adminId;

    private Role role;
}
