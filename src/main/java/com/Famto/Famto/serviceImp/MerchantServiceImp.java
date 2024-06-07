package com.Famto.Famto.serviceImp;

import com.Famto.Famto.dto.UserDto;
import com.Famto.Famto.entity.Admin;
import com.Famto.Famto.entity.Role;
import com.Famto.Famto.entity.User;
import com.Famto.Famto.exception.*;
import com.Famto.Famto.repo.AdminRepository;
import com.Famto.Famto.repo.UserRepository;
import com.Famto.Famto.service.MerchantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
        try {
            Optional<Admin> adminId = adminRepository.findById(UUID.fromString(user.getAdminId()));
            if (adminId.isPresent() && adminId.get().getRole() == Role.ADMIN) {
                String encryptedPassword = passwordEncoder.encode(user.getPassword());
                User merchant = User.builder()
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
        } catch (IllegalArgumentException e) {
            throw new InvalidUUIDFormatException("Invalid UUID format", e);
        } catch (Exception e) {
            throw new MerchantSaveException("An error occurred while saving the merchant", e);

        }
    }

    @Override
    public List<User> userList(String adminId) {
        try {
            if (adminId != null && !adminId.isEmpty()) {
                Optional<Admin> adminOptional = adminRepository.findById(UUID.fromString(adminId));
                if (adminOptional.isPresent()) {

                    return userRepository.findAll();
                } else {
                    throw new AdminNotFoundException("Admin with ID " + adminId + " not found");
                }
            } else {

                return userRepository.findAll()
                        .stream()
                        .filter(user -> !user.isActive())
                        .collect(Collectors.toList());
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidUUIDFormatException("Invalid admin ID format", e);
        } catch (Exception e) {
            throw new UserListFetchException("An error occurred while fetching user list", e);
        }
    }

    @Override
    public boolean deleteMerchant(String id) {
        try {
            Optional<User> user = userRepository.findById(UUID.fromString(id));
            if (user.isPresent()) {
                User user1 = user.get();
                user1.setDelete(true);
                userRepository.save(user1);
                return true;
            } else {
                return false;
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidUUIDFormatException("Invalid UUID format", e);
        } catch (Exception e) {
            throw new MerchantDeleteException("An error occurred while deleting the merchant", e);
        }
    }


}
