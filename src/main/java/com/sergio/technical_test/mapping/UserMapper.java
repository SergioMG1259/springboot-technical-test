package com.sergio.technical_test.mapping;

import com.sergio.technical_test.domain.model.entity.User;
import com.sergio.technical_test.dto.UserRequestDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User toEntity(UserRequestDTO userRequestDTO) {
        return this.modelMapper.map(userRequestDTO, User.class);
    }
}
