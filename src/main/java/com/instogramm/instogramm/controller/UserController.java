package com.instogramm.instogramm.controller;

import com.instogramm.instogramm.dto.UserDTO;
import com.instogramm.instogramm.entity.User;
import com.instogramm.instogramm.facade.UserFacade;
import com.instogramm.instogramm.service.UserService;
import com.instogramm.instogramm.validators.ResponseErrorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    private UserFacade userFacade;
    private UserService userService;
    private ResponseErrorValidator responseErrorValidator;

    @Autowired
    public UserController(UserFacade userFacade, UserService userService, ResponseErrorValidator responseErrorValidator) {
        this.userFacade = userFacade;
        this.userService = userService;
        this.responseErrorValidator = responseErrorValidator;
    }

    @GetMapping("/")
    public ResponseEntity<UserDTO> getCurrentUser (Principal principal){
        User user = userService.getUserByPrincipal(principal);
        UserDTO userDTO = userFacade.userToUserDTO(user);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable("userId") String userId){
        User user = userService.getUserById(Long.parseLong(userId));
        UserDTO userDTO = userFacade.userToUserDTO(user);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult, Principal principal){
        ResponseEntity<Object> listErrors = responseErrorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listErrors)) return  listErrors;

        User user = userService.updateUser(userDTO, principal);
        UserDTO userUpdated = userFacade.userToUserDTO(user);

        return  new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }
}
