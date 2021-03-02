package com.example.RentalCar.Service;

import com.example.RentalCar.Entity.Role;
import com.example.RentalCar.Entity.RoleName;
import com.example.RentalCar.Entity.User;
import com.example.RentalCar.Repository.UserRepository;
import com.example.RentalCar.Security.Jwt.JwtProvider;
import com.example.RentalCar.Security.Jwt.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public User save(User userpassed) {
        if (userRepository.existsByUsername(userpassed.getUsername())) {
            return null;
        }
        User user = new User(userpassed.getName(), userpassed.getSurname(), userpassed.getUsername(),
                encoder.encode(userpassed.getPassword()), userpassed.getBirthday());
        Set<Role> roles = new HashSet<>();
        Role userRole = roleService.findByName(RoleName.ROLE_CUSTOMER);
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
        return userRepository.save(user);
    }

    @Override
    public List<User> getuserlist() {
        Role role = roleService.findByName(RoleName.ROLE_CUSTOMER);
        return userRepository.findAllByRoles(role);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public User editUser(User userfinal) {
        if(userRepository.existsByUsername(userfinal.getUsername())) {
            User userold = userRepository.getByUsername(userfinal.getUsername());
            userold.setBirthday(userfinal.getBirthday());
            userold.setName(userfinal.getName());
            userold.setSurname(userfinal.getSurname());
            if (!userold.getPassword().equals(userfinal.getPassword())){
                userold.setPassword(encoder.encode(userfinal.getPassword()));
            }
            userRepository.save(userold);
            return userold;
        }
        return null;
    }

    @Override
    public JwtResponse login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities());
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean makeAdmin(String username) {
        if (userRepository.existsByUsername(username)) {
            User user = userRepository.getByUsername(username);
            Set<Role> roles = new HashSet<>();
            Role userRole = roleService.findByName(RoleName.ROLE_ADMIN);
            roles.add(userRole);
            user.setRoles(roles);
            userRepository.save(user);
            return true;
        }
        else return false;
    }

    @Override
    public String validateToken(String token) {
        return jwtProvider.getUserNameFromJwtToken(token);
    }
}
