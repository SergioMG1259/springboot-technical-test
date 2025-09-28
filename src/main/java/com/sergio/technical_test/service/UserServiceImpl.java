package com.sergio.technical_test.service;

import com.sergio.technical_test.domain.model.entity.Person;
import com.sergio.technical_test.domain.model.entity.User;
import com.sergio.technical_test.domain.persistance.UserRepository;
import com.sergio.technical_test.domain.service.UserService;
import com.sergio.technical_test.dto.LoginRequestDTO;
import com.sergio.technical_test.dto.LoginResponseDTO;
import com.sergio.technical_test.dto.PasswordUpdateDTO;
import com.sergio.technical_test.dto.UserCreateDTO;
import com.sergio.technical_test.exceptions.BadRequestException;
import com.sergio.technical_test.exceptions.ResourceNotFoundException;
import com.sergio.technical_test.mapping.UserMapper;
import com.sergio.technical_test.security.TokenProvider;
import com.sergio.technical_test.security.UserPrincipal;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,
                           AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
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

    @Override
    @Transactional()
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        // Autenticar al usuario utilizando AuthenticationManager
        // Activa la lógica del CustomUserDetailService (usa el userDetails de loadUserByUsername)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUserName(), loginRequestDTO.getPassword()));
        // Una vez autenticado, el objeto authentication contiene la información del usuario autenticado
        //UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String token = tokenProvider.createAccessToken(authentication);
        return new LoginResponseDTO(token);
    }
}
