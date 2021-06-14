package com.example.demo.user.config;

import com.example.demo.user.entity.Role;
import com.example.demo.user.entity.User;
import com.example.demo.user.service.RoleService;
import com.example.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Configuration
public class UserInitConfig {

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    @PostConstruct
    private void init() {
        List<Role> roles = roleService.findAll();
        if (roles.isEmpty()) {
            Role admin = new Role();
            admin.setAuthority("ROLE_ADMIN");
            roleService.create(admin);
            roles.add(admin);

            Role user = new Role();
            user.setAuthority("ROLE_USER");
            roleService.create(user);
            roles.add(user);

        }

        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(new BCryptPasswordEncoder().encode("admin"));
            adminUser.setAuthorities(new HashSet<>(roles));
            userService.create(adminUser);

            User user = new User();
            user.setUsername("user");
            user.setPassword(new BCryptPasswordEncoder().encode("user"));
            Role userRole = roles.stream().filter(roleEntity -> roleEntity.getAuthority().equals("ROLE_USER")).findFirst().get();
            user.setAuthorities(new HashSet<>(Collections.singletonList(userRole)));
            userService.create(user);
        }
    }
}
