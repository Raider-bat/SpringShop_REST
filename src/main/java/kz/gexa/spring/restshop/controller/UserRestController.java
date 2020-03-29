package kz.gexa.spring.restshop.controller;


import kz.gexa.spring.restshop.dto.UpdateUserPassDto;
import kz.gexa.spring.restshop.dto.UserDto;
import kz.gexa.spring.restshop.entity.user.User;

import kz.gexa.spring.restshop.exception.ApiError;
import kz.gexa.spring.restshop.repository.user.UserRepo;
import kz.gexa.spring.restshop.sevice.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/users")
public class UserRestController {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserRestController.class.getName());

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<User> userList = userRepo.findAll();
        List<UserDto> userDtoList= new ArrayList<>();

        if (userList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        userList.forEach(user -> {
            UserDto userDto =  UserDto.fromUser(user);
            userDtoList.add(userDto);
        });
        LOGGER.info("Выдача всех пользователей");
        return new ResponseEntity<>(userDtoList,HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        Optional<User> user = userService.getUserById(id);
        if (!user.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserDto userDto = UserDto.fromUser(user.get());
        LOGGER.info("Выдача пользователя: "+ userDto.toString());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto){
        try {
            userService.createUser(userDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("update")
    public ResponseEntity<?> updateUserPassByName(@RequestBody UpdateUserPassDto userPassDto){
        try {
            userService.updateUserPasswordByName(userPassDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") Long id){
        try {
            userService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getMessage());
            return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
        }
    }

}