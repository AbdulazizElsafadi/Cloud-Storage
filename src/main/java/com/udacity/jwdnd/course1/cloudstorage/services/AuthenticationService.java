package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.UserData;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationService implements AuthenticationProvider {

    private UserMapper userMapper;
    private HashService hashService;

    public AuthenticationService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        System.out.println("we are in authentication. I don't know how!!");
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
//        System.out.println("username in authenticate:" + username);
//        System.out.println("password in authenticate:" + password);


        UserData user = userMapper.getUserByUsername(username);
        if (user != null) {
//            System.out.println("we are insdie 1st condition");
            String encodedSalt = user.getSalt();
//            System.out.println("encoded salt: " + encodedSalt);
            String hashedPassword = hashService.getHashedValue(password, encodedSalt);
            if (user.getPassword().equals(hashedPassword)) {
//                System.out.println("we are inside condition");

                UsernamePasswordAuthenticationToken authenticatedToken = new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
                authenticatedToken.setDetails(user);
                return authenticatedToken;

            }
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}