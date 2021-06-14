package com.example.demo.user.service;

import com.example.demo.core.CRUDService;
import com.example.demo.user.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends CRUDService<User>, UserDetailsService {

}
