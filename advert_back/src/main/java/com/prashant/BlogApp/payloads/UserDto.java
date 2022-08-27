package com.prashant.BlogApp.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

public class UserDto {

    private int id;

    @NotEmpty
    @Size(min=4, message="Username must be of min of 4 characters!!")
    private String name;

    @Email(message = "Email is not valid!!")
    private String email;

    @NotEmpty
    @Size(min=3, max=12, message = "Password should be betweeen 3 to 12 characters long")
    private String password;

    @NotEmpty
    private String about;    
}
