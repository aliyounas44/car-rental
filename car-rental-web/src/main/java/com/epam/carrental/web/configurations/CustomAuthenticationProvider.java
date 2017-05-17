package com.epam.carrental.web.configurations;

import com.epam.bench.carrental.commons.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

public class CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    CustomerService customerService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        if(customerService.getCustomer().stream().filter(customerDTO -> customerDTO.getEmailAddress().equals(s)).findFirst().isPresent()){
            return new User(s,usernamePasswordAuthenticationToken.getCredentials().toString(),true,true,true,true,new ArrayList<>());
        }
        return new User(null, null, false, false, false, false, new ArrayList<>());
    }
}
