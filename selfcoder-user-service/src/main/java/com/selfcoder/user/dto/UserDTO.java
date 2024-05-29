package com.selfcoder.user.dto;

import java.util.List;

import com.selfcoder.user.entity.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	
	private Long id;

	private String email;

	private String mobileNumber;

	private List<Role> roles;


}
