package com.sergio.technical_test.security;

import com.sergio.technical_test.domain.model.entity.User;
import com.sergio.technical_test.domain.model.enums.Role;

public interface UserWithRole {
    User getUser();
    Role getRole();
    String getEmail();
}
