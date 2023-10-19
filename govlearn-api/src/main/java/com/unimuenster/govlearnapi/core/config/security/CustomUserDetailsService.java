package com.unimuenster.govlearnapi.core.config.security;

import com.unimuenster.govlearnapi.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final CustomUserService customUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity byEmail = customUserService.findByEmail(username);

        if (byEmail == null) {
            throw new UsernameNotFoundException(username);
        }

        return toUserDetails(byEmail);
    }

    private UserDetails toUserDetails(UserEntity admin){
        return new CustomUserDetails(admin);
    }
}
