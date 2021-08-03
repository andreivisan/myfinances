package io.programminglife.myfinances.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.programminglife.myfinances.entity.Client;
import io.programminglife.myfinances.payload.JwtAuthenticationResponse;
import io.programminglife.myfinances.payload.LoginRequest;
import io.programminglife.myfinances.payload.SignupRequest;
import io.programminglife.myfinances.security.JwtTokenProvider;
import io.programminglife.myfinances.service.ClientService;
import io.programminglife.myfinances.util.authentication.AuthenticationUtil;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClientService clientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if (clientService.existsByUsername(signupRequest.getUsername())
                || clientService.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().build();
        }

        Client client = AuthenticationUtil.signupRequestToClient(signupRequest);
        client.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        return ResponseEntity.ok().body(clientService.saveClient(client));
    }
    
}