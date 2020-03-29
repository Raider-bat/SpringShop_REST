package kz.gexa.spring.restshop.controller;


import kz.gexa.spring.restshop.dto.AuthenticationRequestDto;
import kz.gexa.spring.restshop.entity.user.Role;
import kz.gexa.spring.restshop.entity.user.User;
import kz.gexa.spring.restshop.exception.ApiError;
import kz.gexa.spring.restshop.jwt.JwtTokenProvider;
import kz.gexa.spring.restshop.repository.user.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthenticationRequestController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepo userRepo;

    public AuthenticationRequestController(AuthenticationManager authenticationManager,
                                           JwtTokenProvider jwtTokenProvider,
                                           UserRepo userRepo) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepo = userRepo;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDto requestDto){
        try{
            String username = requestDto.getUsername();
            Optional<User> userOptional = userRepo.findByUsername(username);
            if (!userOptional.isPresent()){
                String errorMessage = "User with name " + username + " not found";
                ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, errorMessage);
                return new ResponseEntity<>(apiError,HttpStatus.NOT_FOUND);
            }
            authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(username,
                    requestDto.getPassword()));
            List<Role> roles = new ArrayList<>(userOptional.get().getRoles());
            String token = jwtTokenProvider.createToken(username, roles);
            Map<Object,Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);
            return ResponseEntity.ok(response);
        }catch (AuthenticationException e){
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError,HttpStatus.BAD_REQUEST);
        }
    }

}
