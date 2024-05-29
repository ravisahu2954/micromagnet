package com.selfcoder.user.serviceImpl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.selfcoder.user.dto.UserDTO;
import com.selfcoder.user.entity.Role;
import com.selfcoder.user.entity.User;
import com.selfcoder.user.form.CreateRoleForm;
import com.selfcoder.user.form.UserUpdateForm;
import com.selfcoder.user.repository.RoleRepository;
import com.selfcoder.user.repository.UserRepository;
import com.selfcoder.user.service.AdminService;
import com.selfcoder.user.util.APIResponseDTO;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final RoleRepository roleRepository;

	private final UserRepository userRepository;

	@Override
	public APIResponseDTO getAllUsers(Integer page, Integer size) {
		log.info("******fetching all users*******");

		// Create a Sort object to sort by the 'createdOn' field in descending order
		Sort sort = Sort.by(Sort.Direction.DESC, "createdOn");

		// Create a PageRequest object with sorting
		PageRequest pageRequest = PageRequest.of(page, size, sort);

		// Retrieve the page of users with sorting
		Page<User> usersPage = userRepository.findAll(pageRequest);

		// Now you can do something with the usersPage, like converting it to DTOs
		log.info("****Users fetched successfully****");
		APIResponseDTO apiResponseDTO = APIResponseDTO.builder().data(usersPage).success(true)
				.status(HttpStatus.OK.value()).build();
		return apiResponseDTO;
	}

	@Override
	public APIResponseDTO getUserById(Long id) {
		log.info("******fetching user by ID: {}*******", id);
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			// Convert the User object to DTO
			UserDTO userDTO = convertToDTO(user);
			log.info("*********User fetched successfully*******");

			return APIResponseDTO.builder().data(userDTO).success(true).message("User fetched successfully")
					.status(HttpStatus.OK.value()).build();

		} else {
			return APIResponseDTO.builder().success(true).message("User not found for ID: " + id)
					.status(HttpStatus.NOT_FOUND.value()).build();

		}
	}

	private UserDTO convertToDTO(User user) {
		UserDTO userDto = new UserDTO();
		userDto.setId(user.getId());
		userDto.setEmail(user.getEmail());
		userDto.setMobileNumber(user.getMobileNumber());
		userDto.setRoles(user.getRoles());
		return userDto;
	}

	@Override
	public APIResponseDTO deleteUserById(List<Long> ids) {
		log.info("******deleting users by IDs: {}*******", ids);
		userRepository.deleteAllByIdIn(ids);
		log.info("*********Users deleted successfully*******");

		return APIResponseDTO.builder().message("Users deleted successfully").success(true)
				.status(HttpStatus.OK.value()).build();

	}

	@Override
	public APIResponseDTO updateUser(Long id, UserUpdateForm userUpdateForm) {
		log.info("******updating user with ID: {}*******", id);
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			// Update user fields with the values from userUpdateForm
			// For example:
			// user.setName(userUpdateForm.getName());
			// user.setEmail(userUpdateForm.getEmail());
			userRepository.save(user);
			log.info("*********User saved successfully*******");

			return APIResponseDTO.builder().message("User updated successfully").success(true)
					.status(HttpStatus.OK.value()).build();

		} else {
			return APIResponseDTO.builder().success(true).message("User not found for ID: " + id)
					.status(HttpStatus.NOT_FOUND.value()).build();
		}
	}

	@Override
	public APIResponseDTO getUserByUserName(String username) {
		log.info("******fetching user by username: {}*******", username);
		Optional<User> optionalUser = userRepository.findByUsername(username);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			// Convert the User object to DTO
			UserDTO userDTO = convertToDTO(user);
			log.info("*********User fetched successfully*******");

			return APIResponseDTO.builder().data(userDTO).success(true).message("User fetched successfully")
					.status(HttpStatus.OK.value()).build();

		} else {
			return APIResponseDTO.builder().success(true).message("User not found for username: " + username)
					.status(HttpStatus.NOT_FOUND.value()).build();

		}
	}

	@Transactional
	@Override
	public APIResponseDTO addRole(@Valid CreateRoleForm createRoleForm) {
		log.info("*********Adding role*******");

		Role newRole = new Role();
		newRole.setRoleName(createRoleForm.getRoleName());
		roleRepository.save(newRole);

		log.info("*********New role added*******");

		return APIResponseDTO.builder().message("New role added successfully").status(HttpStatus.OK.value())
				.timeStamp(Instant.now()).build();

	}

}
