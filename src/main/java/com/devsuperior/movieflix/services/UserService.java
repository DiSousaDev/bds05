package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.entities.dto.UserDto;
import com.devsuperior.movieflix.repositories.RoleRepository;
import com.devsuperior.movieflix.repositories.UserRepository;
import com.devsuperior.movieflix.services.exceptions.DataNotFoundException;
import com.devsuperior.movieflix.services.exceptions.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository repository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> user = repository.findByEmail(email);

        if(user.isEmpty()) {
            logger.error("USER_NOT_FOUND" + email);
            throw new UsernameNotFoundException("EMAIL_NOT_FOUND");
        }
        logger.info("USER_FOUND " + user);
        return user.get();
    }

    @Transactional(readOnly = true)
    public UserDto findLogged() {
        return new UserDto(getUserLogged());
    }

    private User getUserLogged() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return repository.findByEmail(username).orElseThrow(() -> new DataNotFoundException("Usuário não encontrado email: " + username + " entity: " + UserService.class.getName()));
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid User");
        }
    }

}
