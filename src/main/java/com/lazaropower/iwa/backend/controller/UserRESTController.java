package com.lazaropower.iwa.backend.controller;

import com.lazaropower.iwa.backend.model.User;
import com.lazaropower.iwa.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/restApi/users")
public class UserRESTController {

    private UserRepository userRepository;

    @Autowired
    public UserRESTController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET/*, produces = "application/xml"*/)
    //@GetMapping
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    //@PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        userRepository.save(user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    //@DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
        User user = userRepository.findById(id).get();
        if (user == null) {
            System.out.println("User not found!");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        userRepository.deleteById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    //@PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("id") long id){
        user.setId(id);
        userRepository.save(user);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
}
