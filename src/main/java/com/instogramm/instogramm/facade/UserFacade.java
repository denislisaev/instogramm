package com.instogramm.instogramm.facade;

import com.instogramm.instogramm.dto.UserDTO;
import com.instogramm.instogramm.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {
    public UserDTO userToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        userDTO.setInfo(user.getInfo());

        return userDTO;
    }
}
