package com.lazaropower.iwa.backend.controller;


import com.lazaropower.iwa.backend.message.request.LoginForm;
import com.lazaropower.iwa.backend.message.request.SignUpForm;
import com.lazaropower.iwa.backend.message.response.JwtResponse;
import com.lazaropower.iwa.backend.message.response.ResponseMessage;
import com.lazaropower.iwa.backend.model.Role;
import com.lazaropower.iwa.backend.model.RoleName;
import com.lazaropower.iwa.backend.model.User;
import com.lazaropower.iwa.backend.repository.RoleRepository;
import com.lazaropower.iwa.backend.repository.UserRepository;
import com.lazaropower.iwa.backend.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/restApi/auth")
public class AuthRESTController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) ((org.springframework.security.core.Authentication) authentication).getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> E-mail is already taken."), HttpStatus.BAD_REQUEST);
        }

        // Create user account
        User user = new User(signUpRequest.getFirstname(), signUpRequest.getSurname(), signUpRequest.getEmail(), passwordEncoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Fail -> Cause: Admin Role not found."));
                    roles.add(adminRole);
                    break;
                case "student":
                    Role studentRole = roleRepository.findByName(RoleName.ROLE_STUDENT)
                            .orElseThrow(() -> new RuntimeException("Fail -> Cause: Student Role not found."));
                    roles.add(studentRole);
                    break;
                case "professor":
                    Role professorRole = roleRepository.findByName(RoleName.ROLE_PROFESSOR)
                            .orElseThrow(() -> new RuntimeException("Fail -> Cause: Professor Role not found."));
                    roles.add(professorRole);
                    break;
                    default:
                        System.out.println("Ha entrado aqui");
                        throw new RuntimeException("Fail -> Cause: Role not found.");
            }
        });

        user.setRoles(roles);
        userRepository.save(user);

        return new ResponseEntity<>(new ResponseMessage("User registered successfully."), HttpStatus.OK);

    }
}
