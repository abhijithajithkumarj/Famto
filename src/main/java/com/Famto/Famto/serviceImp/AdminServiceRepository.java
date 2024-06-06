package com.Famto.Famto.serviceImp;

import com.Famto.Famto.entity.Admin;
import com.Famto.Famto.entity.Role;
import com.Famto.Famto.repo.AdminRepository;
import com.Famto.Famto.service.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceRepository implements AdminService {


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin saveAdmin(Admin admin) {
        Admin adminData=modelMapper.map(admin,Admin.class);
        adminData.setRole(Role.ADMIN);
        return adminRepository.save(adminData);
    }

}
