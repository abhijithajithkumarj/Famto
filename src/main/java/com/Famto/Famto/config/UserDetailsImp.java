package com.Famto.Famto.config;


import com.Famto.Famto.entity.Admin;
import com.Famto.Famto.entity.User;
import com.Famto.Famto.repo.AdminRepository;
import com.Famto.Famto.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class UserDetailsImp implements UserDetailsService {


    private final UserRepository repository;


    private final AdminRepository adminRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Optional<Admin> admin=adminRepository.findByUsername(username);

        if (admin.isPresent()){
            return admin.get();
        }

        Optional<User> user=repository.findByUsername(username);
        if (user.isPresent()){
            return user.get();
        }
        throw new UsernameNotFoundException("User not sound "+ user);

    }



}
