package com.sergio.technical_test.domain.service;

import com.sergio.technical_test.domain.model.entity.Person;
import com.sergio.technical_test.domain.model.entity.User;
import com.sergio.technical_test.dto.LoginRequestDTO;
import com.sergio.technical_test.dto.LoginResponseDTO;
import com.sergio.technical_test.dto.PasswordUpdateDTO;
import com.sergio.technical_test.dto.UserCreateDTO;

public interface UserService {
    User create(UserCreateDTO userCreateDTO, Person person);
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
}
