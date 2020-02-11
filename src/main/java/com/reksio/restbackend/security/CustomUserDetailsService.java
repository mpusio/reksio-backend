package com.reksio.restbackend.security;

import com.reksio.restbackend.exception.UserHaveNotRoleException;
import com.reksio.restbackend.exception.UserIsNotActiveException;
import com.reksio.restbackend.collection.user.Role;
import com.reksio.restbackend.collection.user.User;
import com.reksio.restbackend.collection.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.reksio.restbackend.security.SecurityConstants.ROLE_PREFIX;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with email " + email));

        String userEmail = user.getEmail();
        String userPassword = user.getPassword();
        List<Role> userRoles = user.getRoles();

        if (!user.isActive()){
            throw new UserIsNotActiveException("User is not active.");
        }

        if (user.getRoles()==null || user.getRoles().isEmpty()){
            throw new UserHaveNotRoleException("User have not assign roles.");
        }

        return new org.springframework.security.core.userdetails.User(userEmail, userPassword, wrapRoles(userRoles));
    }

    private List<SimpleGrantedAuthority> wrapRoles(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role.name()))
                .collect(Collectors.toList());
    }
}
