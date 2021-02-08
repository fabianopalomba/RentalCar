package com.example.RentalCar.Service;

import com.example.RentalCar.Entity.User;
import com.example.RentalCar.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
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
            userold.setAdmin(userfinal.getAdmin());
            userold.setBirthday(userfinal.getBirthday());
            userold.setPassword(userfinal.getPassword());
            userold.setName(userfinal.getName());
            userold.setSurname(userfinal.getSurname());
            userRepository.save(userold);
            return userold;
        }
        return null;
    }

    @Override
    public User login(Long id, String password) {
        if(userRepository.existsById(id)){
            User user = userRepository.getById(id);
            if (user.getPassword().equals(password)){
                return user;
            }
            else return null;
        }
        return null;
    }


}
