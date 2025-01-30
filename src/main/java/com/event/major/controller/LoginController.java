package com.event.major.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.event.major.global.GlobalData;
import com.event.major.model.Role;
import com.event.major.model.User;
import com.event.major.repository.RoleRepository;
import com.event.major.repository.UserRepository;


@Controller
public class LoginController {
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/login")
    public String login(){
        GlobalData.cart.clear();
        return "login";

    }

    @GetMapping("/register")
    public String registerGet(){
        return "register";

    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute("user") User user, HttpServletRequest request , HttpServletResponse response)throws IOException{


        Optional<User> existingUser = userRepository.findUserByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            response.sendError(HttpServletResponse.SC_CONFLICT, "User with this email already exists.");
            return null; // Return null to prevent further processing
        }

         String password = user.getPassword();
         user.setPassword(bCryptPasswordEncoder.encode(password));
          List<Role> roles = new ArrayList<>();
        roleRepository.findById(2).ifPresent(roles::add); // Avoid `get()` without a check
        user.setRoles(roles);

        // Save the user in the database
        userRepository.save(user);

        // Programmatic login
        try {
            request.login(user.getEmail(), password);
        } catch (ServletException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Login failed after registration.");
            
            return null;
        }
        return "redirect:/";

    }
}
