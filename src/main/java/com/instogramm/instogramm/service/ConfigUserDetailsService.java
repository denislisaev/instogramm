package com.instogramm.instogramm.service;

import com.instogramm.instogramm.entity.User;
import com.instogramm.instogramm.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConfigUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public ConfigUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Ищем пользователя в бд
    public UserDetails loadUserByUsername (String username){
        User user = userRepository.findUserByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found " + username));
        return initUser(user);
    }

    //Конвертация в Spring Sequrity
    public static User initUser(User user){
        List<GrantedAuthority> authorityList = user.getRoles().stream()
                .map(eRole -> new SimpleGrantedAuthority(eRole.name()))
                .collect(Collectors.toList());

        //Наделяем пользователя полномочиями
        return new User(user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                authorityList);
    }

    public User loadUserById(Long id){
        return userRepository.findUserById(id).orElseThrow(null);
    }
}
