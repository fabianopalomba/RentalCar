package com.example.RentalCar.Controller;

import com.example.RentalCar.Entity.User;
import com.example.RentalCar.Security.Jwt.JwtResponse;
import com.example.RentalCar.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    //Admin
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User userpassed){
        try {
            if (userService.existsByUsername(userpassed.getUsername())) {
                return new ResponseEntity<>(("Fail -> Username is already taken!"),
                        HttpStatus.BAD_REQUEST);
            }
            User user = userService.save(userpassed);
            if (user!=null) return new ResponseEntity<>(HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getuserlist")
    public ResponseEntity<List<User>> getuserlist(){
        try {
            List<User> users = userService.getuserlist();
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteuserbyid/{id}")
    public ResponseEntity<HttpStatus> deleteuserbyid(@PathVariable Long id){
        try{
            userService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Customer
    @PostMapping("/signin/{username}")
    public ResponseEntity<JwtResponse> login(@PathVariable String username, @RequestBody String password){
        try{
            return ResponseEntity.ok(userService.login(username, password));
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getuserbyid/{id}")
    public ResponseEntity<User> getuserbyid(@PathVariable Long id){
        try{
            User user = userService.getById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getuserbyusername/{username}")
    public ResponseEntity<User> getuserbyusername(@PathVariable String username){
        try{
            User user = userService.getByUsername(username);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/edituser")
    public ResponseEntity<HttpStatus> edituser(@RequestBody User userfinal){
        try{
            User user = userService.editUser(userfinal);
            if (user != null) return new ResponseEntity<>(HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/makeadmin")
    public ResponseEntity<HttpStatus> makeadmin(@RequestBody String username){
        try{
            if (userService.makeAdmin(username)){
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/validatetoken")
    public ResponseEntity<String> validatetoken(@RequestBody String token){
        return new ResponseEntity<>(userService.validateToken(token) , HttpStatus.OK);
    }
}
