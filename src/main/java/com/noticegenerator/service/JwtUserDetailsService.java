package com.noticegenerator.service;

import java.util.ArrayList;

import com.noticegenerator.dao.NoticeGeneratorDAO;
import com.noticegenerator.entity.JwtRequest;
import com.noticegenerator.entity.LogIn;
import com.noticegenerator.service.impl.NoticeGerneratorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    NoticeGeneratorService service;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    public JwtUserDetailsService(){
        service=new NoticeGerneratorServiceImpl();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LogIn userInfo=service.loadUserByUserName(email);
        if (userInfo.getEmail().equals(email)) {
            return new User(userInfo.getEmail(), bcryptEncoder.encode(userInfo.getPassword()),
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }
}
