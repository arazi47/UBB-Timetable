package com.razi.ubbtt.services;

import com.razi.ubbtt.domain.Role;
import com.razi.ubbtt.domain.User;
import com.razi.ubbtt.repositories.RoleRepository;
import com.razi.ubbtt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    private List<String> adminUsernames = new ArrayList<>(Arrays.asList("test1", "test2"));

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);

        Role role;
        if (adminUsernames.contains(user.getUsername())) {
            role = roleRepository.findByRole("ADMIN");
        } else {
            role = roleRepository.findByRole("USER");
        }

        user.setRoles(new HashSet<>(Arrays.asList(role)));
        return userRepository.save(user);
    }

}