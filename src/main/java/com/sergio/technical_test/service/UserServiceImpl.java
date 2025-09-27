package com.sergio.technical_test.service;

import com.sergio.technical_test.domain.model.entity.Person;
import com.sergio.technical_test.domain.model.entity.User;
import com.sergio.technical_test.domain.persistance.UserRepository;
import com.sergio.technical_test.domain.service.UserService;
import com.sergio.technical_test.dto.PasswordUpdateDTO;
import com.sergio.technical_test.dto.UserCreateDTO;
import com.sergio.technical_test.exceptions.BadRequestException;
import com.sergio.technical_test.exceptions.ResourceNotFoundException;
import com.sergio.technical_test.mapping.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional()
    public User create(UserCreateDTO userCreateDTO, Person person) {
        if (userRepository.existsByUserName(userCreateDTO.getUserName())) {
            throw new BadRequestException("Username already exists");
        }
        User user = userMapper.toEntity(userCreateDTO);
        user.setPerson(person);
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    @Transactional()
    public void updatePassword(Long id, PasswordUpdateDTO passwordUpdateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        user.setPassword(passwordUpdateDTO.getPassword());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }
}
