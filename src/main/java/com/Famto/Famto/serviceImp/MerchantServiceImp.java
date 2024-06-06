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
        Optional<User> adminId = userRepository.findById(UUID.fromString(user.getAdminId()));

        if (adminId.isPresent() && adminId.get().getRole()==Role.ADMIN) {
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            User merchant = User
                    .builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .role(Role.MERCHANTS)
                    .ownerName(user.getOwnerName())
                    .shopName(user.getShopName())
                    .password(encryptedPassword)
                    .build();

                User savedUser = userRepository.save(merchant);
                return modelMapper.map(savedUser, UserDto.class);

        } else {
            throw new UsernameNotFoundException("User with ID " + user.getAdminId() + " not found");
        }
    }




}
