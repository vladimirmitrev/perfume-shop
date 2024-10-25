package com.app.perfumeshop.model.mapper;

import com.app.perfumeshop.model.dto.UserRegisterDTO;
import com.app.perfumeshop.model.dto.user.UserViewDTO;
import com.app.perfumeshop.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userDtoToUserEntity(UserRegisterDTO userRegisterDTO);
    @Mapping(source = "userRoles", target = "roles")
    UserViewDTO userEntityToUserDto(User user);
}
