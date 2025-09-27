package com.sergio.technical_test.service;

import com.sergio.technical_test.domain.model.entity.User;
import com.sergio.technical_test.domain.persistance.UserRepository;
import com.sergio.technical_test.domain.service.UserService;
import com.sergio.technical_test.dto.PasswordUpdateDTO;
import com.sergio.technical_test.dto.UserRequestDTO;
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
    public User create(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByUserName(userRequestDTO.getUserName())) {
            throw new BadRequestException("Username already exists");
        }
        User user = userMapper.toEntity(userRequestDTO);
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    @Transactional()
    public User update(Long id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        if (userRepository.existsByUserNameAndIdNot(userRequestDTO.getUserName(), id)) {
            throw new BadRequestException("Username already exists");
        }

        user.setUserName(userRequestDTO.getUserName());

        return userRepository.save(user);
    }

    @Override
    @Transactional()
    public void updatePassword(Long id, PasswordUpdateDTO passwordUpdateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        user.setPassword(passwordUpdateDTO.getPassword());
        userRepository.save(user);
    }
}
