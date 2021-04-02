package com.api.toolscript.security.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserIdDetailsServiceImpl {
    UserDetails loadUserByUserId(Long id);
}
