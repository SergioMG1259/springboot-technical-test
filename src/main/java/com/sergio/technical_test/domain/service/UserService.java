package com.sergio.technical_test.domain.service;

import com.sergio.technical_test.domain.model.entity.User;
import com.sergio.technical_test.dto.PasswordUpdateDTO;
import com.sergio.technical_test.dto.UserRequestDTO;

public interface UserService {
    User create(UserRequestDTO userRequestDTO);
    User update(Long id, UserRequestDTO userRequestDTO);
    void updatePassword(Long id, PasswordUpdateDTO passwordUpdateDTO);
}
