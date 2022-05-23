package com.devsuperior.movieflix.entities.dto;

import com.devsuperior.movieflix.services.validation.UserInsertValid;

@UserInsertValid
public class UserInsertDto extends UserDto {

    private String password;

    public UserInsertDto() {
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
