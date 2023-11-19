package com.doan.config.jwt;


import com.doan.entity.User;
import com.doan.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    final UserRepository userRepository;
    UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> entity = this.userRepository.findByUsername(username);
        Collection<GrantedAuthority> roles;

        if (entity.isPresent()) {

            // fix all user with ROLE_USER
            roles = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
            return new AuthUserDetails(entity.get(), roles);
        } else {
            throw new UsernameNotFoundException("username not found with username: " + username);
        }
    }
}
