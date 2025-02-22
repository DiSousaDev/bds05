package com.devsuperior.movieflix.services.validation;

import com.devsuperior.movieflix.controllers.exceptions.CustomFieldError;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.entities.dto.UserUpdateDto;
import com.devsuperior.movieflix.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDto> {

    @Autowired
    private UserRepository repository;
    
    @Autowired
    private HttpServletRequest req;

    @Override
    public void initialize(UserUpdateValid ann) {
        ConstraintValidator.super.initialize(ann);
    }

    @Override
    public boolean isValid(UserUpdateDto dto, ConstraintValidatorContext context) {

        List<CustomFieldError> list = new ArrayList<>();
        String[] requestUri = req.getRequestURI().split("/");
        int id = Integer.parseInt(requestUri[2]);

        Optional<User> user = repository.findByEmail(dto.getEmail());
        if(user.isPresent() && id != user.get().getId()) {
            list.add(new CustomFieldError("email", "Email already in use."));
        }


        for (CustomFieldError cfe : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(cfe.getFieldMessage()).addPropertyNode(cfe.getFieldName()).addConstraintViolation();
        }

        return list.isEmpty();
    }
}
