package com.selfcoder.user.service;

import java.util.List;

import com.selfcoder.user.form.UserLoginForm;
import com.selfcoder.user.form.UserRegistrationForm;
import com.selfcoder.user.form.UserUpdateForm;
import com.selfcoder.user.util.APIResponseDTO;

public interface UserService {

	APIResponseDTO registerUser(UserRegistrationForm registrationForm);

	APIResponseDTO login(UserLoginForm loginForm);

	APIResponseDTO getUserById(Long id);

	APIResponseDTO deleteUserById(List<Long> ids);

	APIResponseDTO updateUser(Long id, UserUpdateForm userUpdateForm);

	APIResponseDTO getUserByUserName(String username);

}
