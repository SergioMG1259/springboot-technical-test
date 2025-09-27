package com.sergio.technical_test.mapping;

import com.sergio.technical_test.domain.model.entity.User;
import com.sergio.technical_test.dto.UserCreateDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User toEntity(UserCreateDTO userRequestDTO) {
        return this.modelMapper.map(userRequestDTO, User.class);
    }
}
