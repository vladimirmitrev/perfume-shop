package com.app.perfumeshop.service;

import com.app.perfumeshop.exception.ObjectNotFoundException;
import com.app.perfumeshop.model.dto.UserRegisterDTO;
import com.app.perfumeshop.model.dto.user.UserViewDTO;
import com.app.perfumeshop.model.entity.User;
import com.app.perfumeshop.model.entity.UserRole;
import com.app.perfumeshop.model.enums.UserRoleEnum;
import com.app.perfumeshop.model.mapper.UserMapper;
import com.app.perfumeshop.repository.UserRepository;
import com.app.perfumeshop.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserRoleService userRoleService;

    @Value("${perfumeshop.default.admin.pass}")
    public String hiddenAdminPassword;

    @Value("${perfumeshop.default.admin.phone}")
    public String hiddenAdminPhone;

    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, UserRoleService userRoleService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.userRoleService = userRoleService;
    }

    public void init() {
        if (userRoleRepository.count() == 0 && userRepository.count() == 0) {
            UserRole admin = new UserRole()
                    .setUserRole(UserRoleEnum.ADMIN);
            userRoleRepository.save(admin);

            UserRole employee = new UserRole()
                    .setUserRole(UserRoleEnum.EMPLOYEE);
            userRoleRepository.save(employee);

            UserRole customer = new UserRole()
                    .setUserRole(UserRoleEnum.CUSTOMER);
            userRoleRepository.save(customer);

            String encodedPassword = passwordEncoder.encode(hiddenAdminPassword);

            User firstUser = new User()
                    .setFirstName("Vladimir")
                    .setLastName("Mitrev")
                    .setEmail("vladimir_mitrev@hotmail.com")
                    .setUsername("admin")
                    .setPassword(encodedPassword)
                    .setPhoneNumber(hiddenAdminPhone)
                    .setOrders(null);

            firstUser.addRole(admin);

            userRepository.save(firstUser);
        }
    }

    public void registerUser(UserRegisterDTO userRegisterDTO) {

        UserRole userRole = userRoleService.getRole(UserRoleEnum.CUSTOMER);

        User newUser = userMapper.userDtoToUserEntity(userRegisterDTO);
        newUser.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        newUser.addRole(userRole);

        userRepository.saveAndFlush(newUser);
    }

    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email).get();
    }

    public List<UserViewDTO> getAllUser() {

        return userRepository.findAll()
                .stream().map(userMapper::userEntityToUserDto)
                .toList();
    }

    public UserViewDTO findUserById(Long id) {

        return userRepository.findById(id)
                .map(userMapper::userEntityToUserDto)
                .orElseThrow(() -> new ObjectNotFoundException("User with this id " + id + "is not found!"));

    }

    public void removeRole(Long userId) {

        User user = userRepository.findById(userId).get();

        user.getUserRoles().removeIf(userRole -> userRole.getUserRole().name().equals("EMPLOYEE"));

        userRepository.save(user);
    }

    public void addRole(Long userId) {

        User user = userRepository.findById(userId).get();

        user.getUserRoles().add(userRoleRepository.findByUserRole(UserRoleEnum.EMPLOYEE).get());

        userRepository.save(user);
    }
}
