package com.project.blog.services.impl;

import com.project.blog.config.AppConstants;
import com.project.blog.entites.Role;
import com.project.blog.entites.User;
import com.project.blog.exceptions.ResourceNotFoundException;
import com.project.blog.payloads.UserDto;
import com.project.blog.repositories.RoleRespository;
import com.project.blog.repositories.UserRepository;
import com.project.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRespository roleRespository;

    @Override
    public UserDto registerUser(UserDto userDto) {
       User user=modelMapper.map(userDto,User.class);
       user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role=roleRespository.findById(AppConstants.NORMAL_USER).get();
        user.getRoles().add(role);
        User newUser=this.userRepository.save(user);
        return modelMapper.map(newUser,UserDto.class);

    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User savedUser=this.userRepository.save(userDtoToEntity(userDto));
        return userEntityToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user=this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","id",userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        User updatedUser=this.userRepository.save(user);
        return userEntityToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user=this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","id",userId));

        return userEntityToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
       List<User> users=this.userRepository.findAll();
       return users.stream().map(user-> userEntityToDto(user)).collect(Collectors.toList());

    }

    @Override
    public void deleteUser(Integer userId) {
        User user=this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","id",userId));
        this.userRepository.delete(user);

    }

    private User userDtoToEntity(UserDto userDto){
        User user= modelMapper.map(userDto,User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;

    }
    private UserDto userEntityToDto(User user){
       return modelMapper.map(user,UserDto.class);
    }

}
