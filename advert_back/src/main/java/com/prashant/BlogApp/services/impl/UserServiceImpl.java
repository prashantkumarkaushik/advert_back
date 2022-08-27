package com.prashant.BlogApp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prashant.BlogApp.config.AppContants;
import com.prashant.BlogApp.entities.Role;
import com.prashant.BlogApp.entities.User;
import com.prashant.BlogApp.payloads.UserDto;
import com.prashant.BlogApp.repositories.RoleRepo;
import com.prashant.BlogApp.repositories.UserRepo;
import com.prashant.BlogApp.services.UserService;
import com.prashant.BlogApp.exceptions.*;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
       User user = this.userRepo.findById(userId)
            .orElseThrow(()-> new ResourceNotFoundException("User", " id ", userId));

            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setAbout(userDto.getAbout());
        User updatedUser = this.userRepo.save(user);
        return this.userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
       User user = this.userRepo.findById(userId)
            .orElseThrow(()-> new ResourceNotFoundException("User", " id ", userId));

        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
       User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId)); 
       this.userRepo.delete(user);
    }

    public User dtoToUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class); 
        // User user = new User(); 

        // user.setId(userDto.getId());
        // user.setName(userDto.getName());
        // user.setEmail(userDto.getEmail());
        // user.setAbout(userDto.getAbout());
        // user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return user;
    }

    public UserDto userToDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleRepo.findById(AppContants.NORMAL_USER).get();

        user.getRoles().add(role);
        User newUser = userRepo.save(user);

        return this.modelMapper.map(newUser, UserDto.class);
    }
}
