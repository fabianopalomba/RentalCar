package com.example.RentalCar.Service;

import com.example.RentalCar.Entity.Role;
import com.example.RentalCar.Entity.RoleName;
import com.example.RentalCar.Entity.User;
import com.example.RentalCar.Repository.UserRepository;
import com.example.RentalCar.Security.Jwt.JwtProvider;
import com.example.RentalCar.Security.Jwt.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public List<User> findAll() {
        return userRepository.findAll();
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
        if(userRepository.existsById(userfinal.getId())) {
            User userold = userRepository.getById(userfinal.getId());
            userold.setBirthday(userfinal.getBirthday());
            userold.setPassword(userfinal.getPassword());
            userold.setName(userfinal.getName());
            userold.setSurname(userfinal.getSurname());
            Set<Role> roles = new HashSet<>(userfinal.getRoles());
            userold.setRoles(roles);
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


}
