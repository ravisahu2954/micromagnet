package com.selfcoder.user.form;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationForm {

    @NotNull(message = "Email cannot be null")
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Mobile number cannot be null")
    @NotEmpty(message = "Mobile number cannot be empty")
    @Pattern(regexp = "^[0-9]*$", message = "Mobile number should only contain digits")
    private String mobileNumber;

    @NotNull(message = "Password cannot be null")
    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @NotNull(message = "RoleIds cannot be null")
    @NotEmpty(message = "RoleIds cannot be empty")
    private List<Long> roleIds;
}
