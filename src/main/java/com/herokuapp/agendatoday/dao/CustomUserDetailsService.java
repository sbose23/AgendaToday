package com.herokuapp.agendatoday.dao;

import com.herokuapp.agendatoday.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepo;

    public CustomUserDetailsService(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User myUser = userRepo.findByUsername(username);
        if (myUser == null){
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(myUser);
    }
}
