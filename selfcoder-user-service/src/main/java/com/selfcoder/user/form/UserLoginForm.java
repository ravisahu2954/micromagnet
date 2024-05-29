package com.selfcoder.user.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginForm {

	@NotNull(message = "Email cannot be null")
	@NotEmpty(message = "Email cannot be empty")
	@Email(message = "Email should be valid")
	private String email;

	@NotNull(message = "Password cannot be null")
	@NotEmpty(message = "Password cannot be empty")
	private String password;

}
