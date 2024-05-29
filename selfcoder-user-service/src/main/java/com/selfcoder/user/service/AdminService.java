package com.selfcoder.user.service;

import java.util.List;

import com.selfcoder.user.form.CreateRoleForm;
import com.selfcoder.user.form.UserUpdateForm;
import com.selfcoder.user.util.APIResponseDTO;

import jakarta.validation.Valid;

public interface AdminService {
	
	APIResponseDTO getAllUsers(Integer page, Integer size);

	APIResponseDTO getUserById(Long id);

	APIResponseDTO deleteUserById(List<Long> ids);

	APIResponseDTO updateUser(Long id, UserUpdateForm userUpdateForm);

	APIResponseDTO getUserByUserName(String username);

	APIResponseDTO addRole(@Valid CreateRoleForm createRoleForm);


}
