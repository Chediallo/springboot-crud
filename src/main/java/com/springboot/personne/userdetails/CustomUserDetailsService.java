/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.springboot.personne.userdetails;

import com.springboot.personne.entity.User;
import com.springboot.personne.repository.UserRepository;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mohamed
 */

@Service
public class CustomUserDetailsService implements UserDetailsService{
    
    
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
       
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with username or email %s not found :", usernameOrEmail)));
        
        Set<GrantedAuthority> authoritys = user.getRoles()
                .stream().map((roles) -> new SimpleGrantedAuthority(roles.getNom()))
                .collect(Collectors.toSet());
        
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authoritys);
    }
    
}
