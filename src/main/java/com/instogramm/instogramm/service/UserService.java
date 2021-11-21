package com.instogramm.instogramm.service;

import com.instogramm.instogramm.entity.User;
import com.instogramm.instogramm.entity.enums.ERole;
import com.instogramm.instogramm.exceptions.UserAlreadyException;
import com.instogramm.instogramm.payload.request.SignUpRequest;
import com.instogramm.instogramm.repository.UserRepository;
import com.instogramm.instogramm.sucurity.jwt.JWTProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public static final Logger LOG = LoggerFactory.getLogger(JWTProvider.class);


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User createUser(SignUpRequest userIn){
        User user = new User();
        user.setEmail(userIn.getEmail());
        user.setFirstname(userIn.getFirstname());
        user.setLastname(userIn.getLastname());
        user.setUsername(userIn.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userIn.getPassword()));
        user.getRoles().add(ERole.ROLE_USER);

        try {
            LOG.info("Saving user {}", userIn.getEmail());
            return userRepository.save(user);
        } catch (Exception e) {
            LOG.error("Error registration {}", e.getMessage());
            throw new UserAlreadyException("The user " + user.getEmail() + "already exist!");
        }
    }
}
