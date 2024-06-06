package com.Famto.Famto;

import com.Famto.Famto.entity.Admin;
import com.Famto.Famto.entity.Role;
import com.Famto.Famto.entity.User;
import com.Famto.Famto.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class FamtoApplication  {



	@Autowired
	private UserRepository userRepository;


	public static void main(String[] args) {
		SpringApplication.run(FamtoApplication.class, args);
	}


}
