package com.selfcoder.user.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRoleForm {

	@NotNull(message = "roleName cannot be null")
	@NotEmpty(message = "roleName cannot be empty")
	private String roleName;

	
}
