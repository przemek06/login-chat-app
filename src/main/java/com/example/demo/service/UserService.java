package com.example.demo.service;

import com.example.demo.entity.UserEntity;
import com.example.demo.model.AuthenticationRequest;
import com.example.demo.model.UserDetailsImpl;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;
    public ResponseEntity<String> save(UserEntity userEntity){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null) return new ResponseEntity<>("You are logged in!", HttpStatus.FORBIDDEN);
        userEntity.setRoles("USER");
        userEntity.setPassword(encoder.encode(userEntity.getPassword()));
        userEntity.setActive(true);
        if(userRepository.findByUsername(userEntity.getUsername()).isPresent()){
            return new ResponseEntity<>("There is already an user with this username!", HttpStatus.BAD_REQUEST);
        } else {
            try {
                userRepository.save(userEntity);
                return new ResponseEntity<>("Registered!!", HttpStatus.OK);
            } catch (Exception exception){
                return new ResponseEntity<>("Username or password in wrong format.", HttpStatus.BAD_REQUEST);
            }
        }
    }

    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("Authorization", "" );
        response.addCookie(cookie);
        return ResponseEntity.ok().build();

    }

    public ResponseEntity<?> createAuthenticationToken(AuthenticationRequest authenticationRequest,
                                                       HttpServletResponse response){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Bad credentials");
        }

        final UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = JwtUtil.generateToken(userDetails);

        Cookie cookie = new Cookie("Authorization", "Bearer_" + jwt);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }
}
