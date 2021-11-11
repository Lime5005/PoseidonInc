package com.nnk.springboot.services.auth;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
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
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User myUser = userRepository.findByUsername(username);
        if (myUser == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        //??? GrantedAuthority
//        return new org.springframework.security.core.userdetails.User(myUser.getUsername(), myUser.getPassword(), new ArrayList<>());
        UserDetails user = org.springframework.security.core.userdetails.User.withUsername(myUser.getUsername())
                .password(myUser.getPassword())
                .authorities(getAuthorities(myUser)).build();

        return user;

    }

    private Collection<GrantedAuthority> getAuthorities(User myUser) {
        Collection<GrantedAuthority> authorities = new ArrayList<>(2);
        if (myUser.getRole().equals("ADMIN")) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        } else if (myUser.getRole().equals("USER")) {
            authorities.add(new SimpleGrantedAuthority("USER"));
        }
        return authorities;
    }

}
