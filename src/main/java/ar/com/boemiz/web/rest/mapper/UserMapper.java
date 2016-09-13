/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.boemiz.web.rest.mapper;

import ar.com.boemiz.domain.User;
import ar.com.boemiz.web.rest.dto.UserDTO;
import org.mapstruct.Mapper;

/**
 *
 * @author Francisco Blanco <blanco.jose.francisco@gmail.com>
 */
@Mapper(componentModel = "spring", uses = { })
public interface UserMapper {
    
    UserDTO userToUserDTO(User user);

    User userDTOToUser(UserDTO userDTO);
}
