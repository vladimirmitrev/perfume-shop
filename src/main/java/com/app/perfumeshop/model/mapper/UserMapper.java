package com.app.perfumeshop.model.mapper;

import com.app.perfumeshop.model.dto.UserRegisterDTO;
import com.app.perfumeshop.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userDtoToUserEntity(UserRegisterDTO userRegisterDTO);

//    User userEntityToUserDto(User user);

}
