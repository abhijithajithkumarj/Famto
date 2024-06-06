package com.Famto.Famto.serviceImp;

import com.Famto.Famto.dto.UserDto;
import com.Famto.Famto.entity.Admin;
import com.Famto.Famto.entity.Role;
import com.Famto.Famto.entity.User;
import com.Famto.Famto.repo.AdminRepository;
import com.Famto.Famto.repo.UserRepository;
import com.Famto.Famto.service.MerchantService;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class MerchantServiceImp implements MerchantService {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private AdminRepository adminRepository;


    @Override
    public UserDto saveMerchant(UserDto user) {
        Optional<Admin> adminId = adminRepository.findById(UUID.fromString(user.getAdminId()));

        System.out.println(adminId);

        if (adminId.isPresent()) {
            Admin existingUser = adminId.get();
            if (existingUser.getRole() == Role.ADMIN) {

                User userData = modelMapper.map(user, User.class);
                String encryptedPassword = passwordEncoder.encode(user.getPassword());
                userData.setPassword(encryptedPassword);
                userData.setRole(Role.MERCHANTS);
                userData.setAdmin(adminId.get());
                User savedUser = userRepository.save(userData);
                return modelMapper.map(savedUser, UserDto.class);
            } else {
                throw new IllegalArgumentException("User with ID " + user.getAdminId() + " is not an ADMIN");
            }
        } else {
            throw new UsernameNotFoundException("User with ID " + user.getAdminId() + " not found");
        }
    }




}
