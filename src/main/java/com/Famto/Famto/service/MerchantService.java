package com.Famto.Famto.service;

import com.Famto.Famto.dto.UserDto;
import com.Famto.Famto.entity.User;

import java.util.List;

public interface MerchantService {

    UserDto saveMerchant(UserDto user);


    List<User> userList (String id);


    boolean deleteMerchant(String id);

}
