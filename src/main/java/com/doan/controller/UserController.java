package com.doan.controller;

import com.doan.config.jwt.AuthUserDetails;
import com.doan.config.jwt.JwtTokenProvider;
import com.doan.config.jwt.UserDetailsServiceImpl;
import com.doan.dto.GetUser;
import com.doan.payload.AddUserRequest;
import com.doan.payload.EditUserRequest;
import com.doan.payload.LoginRequest;
import com.doan.payload.LoginRespon;
import com.doan.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    final JwtTokenProvider tokenProvider;
    final AuthenticationManager authenticationManager;
    final UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserService service;

    UserController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserDetailsServiceImpl userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Login api
     *
     * @param loginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public LoginRespon login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = tokenProvider.generateToken((AuthUserDetails) authentication.getPrincipal());
            return new LoginRespon(accessToken);
        } catch (UsernameNotFoundException | BadCredentialsException ex) {
            errors.put("code", "500");
        }
        return new LoginRespon(errors);
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AddUserRequest addUserRequest) {
        return service.addUser(addUserRequest);
    }

    @PutMapping
    public ResponseEntity<?> editUser(@RequestBody EditUserRequest editUserRequest) {
        return service.editUser(editUserRequest);
    }

    @GetMapping("/{username}")
    public GetUser getUser(@PathVariable String username){
        return service.getAccount(username);
    }
}
