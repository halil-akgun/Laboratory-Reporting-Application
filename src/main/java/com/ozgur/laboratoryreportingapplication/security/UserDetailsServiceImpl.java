package com.ozgur.laboratoryreportingapplication.security;

import com.ozgur.laboratoryreportingapplication.entity.User;
import com.ozgur.laboratoryreportingapplication.exception.ResourceNotFoundException;
import com.ozgur.laboratoryreportingapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

//   My purpose in this class: To give my User objects to the Security layer and
//   turn them into UserDetails. In short, I will introduce my own Users to the security layer.

    @Autowired
    UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException("user not found with username: " + username));

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    List.of(new SimpleGrantedAuthority(user.getUserRole().getRoleType().name()))
//                  we convert the role we created to the data type that security wants (SimpleGrantedAuthority)
            );
        } else {
            throw new UsernameNotFoundException("USer not found with username : " + username);
        }
    }
}