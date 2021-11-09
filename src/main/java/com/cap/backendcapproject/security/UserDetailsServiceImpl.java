package com.cap.backendcapproject.security;

import com.cap.backendcapproject.entities.AppUser;
import com.cap.backendcapproject.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        AppUser user = accountService.loadUserByEmail(email);
        if(user == null) throw new UsernameNotFoundException("Invalid user");
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(r->{
            authorities.add(new SimpleGrantedAuthority(r.getRole()));
        });

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
