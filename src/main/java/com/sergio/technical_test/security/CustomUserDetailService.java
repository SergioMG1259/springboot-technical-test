package com.sergio.technical_test.security;

import com.sergio.technical_test.domain.model.entity.User;
import com.sergio.technical_test.domain.model.enums.Role;
import com.sergio.technical_test.domain.persistance.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserWithRole result = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Bad credentials"));

        User user = result.getUser();
        Role role = result.getRole();
        String email = result.getEmail();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.toString());

        return new UserPrincipal(user.getId(),
                user.getUserName(),
                email,
                user.getPassword(),
                Collections.singletonList(authority));
    }
}
