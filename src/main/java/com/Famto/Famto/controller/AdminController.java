package com.Famto.Famto.controller;



import com.Famto.Famto.config.JwtService;
import com.Famto.Famto.dto.LoginRequest;
import com.Famto.Famto.dto.UserDto;
import com.Famto.Famto.entity.Admin;
import com.Famto.Famto.entity.User;
import com.Famto.Famto.service.AdminService;
import com.Famto.Famto.service.MerchantService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private JwtService jwtService;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MerchantService merchantService;



    @Autowired
    private AdminService adminService;



    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto user){
        return ResponseEntity.ok().body(merchantService.saveMerchant(user));
    }


    @PostMapping("/register-admin")
    public ResponseEntity<Admin> adminRegister(@RequestBody Admin admin){
        System.out.println(admin.getUsername());
        return  ResponseEntity.ok().body(adminService.saveAdmin(admin));
    }


    @PostMapping("/login")
    public String authAndToken(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(loginRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }


    @GetMapping("/getAllUser")
    public ResponseEntity<List<User>> getAllUser(){
        return ResponseEntity.ok().body(merchantService.userList());
    }





}
