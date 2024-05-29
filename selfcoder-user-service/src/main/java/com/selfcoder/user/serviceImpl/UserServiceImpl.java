package com.selfcoder.user.serviceImpl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.selfcoder.user.entity.Role;
import com.selfcoder.user.entity.User;
import com.selfcoder.user.form.UserLoginForm;
import com.selfcoder.user.form.UserRegistrationForm;
import com.selfcoder.user.form.UserUpdateForm;
import com.selfcoder.user.repository.RoleRepository;
import com.selfcoder.user.repository.UserRepository;
import com.selfcoder.user.service.UserService;
import com.selfcoder.user.util.APIResponseDTO;
import com.selfcoder.user.util.JwtTokenProvider;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final JwtTokenProvider jwtTokenProvider;

	private final RoleRepository roleRepository;

//	private final ModelMapper modelMapper;

	@Transactional
	@Override
	public APIResponseDTO registerUser(UserRegistrationForm registrationForm) {
		log.info("*********Registering user*******");
		User newUser = new User();
		newUser.setEmail(registrationForm.getEmail());
		newUser.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
		newUser.setMobileNumber(registrationForm.getMobileNumber());

		List<Role> roles = roleRepository.findAllById(registrationForm.getRoleIds());
		newUser.setRoles(roles);
		userRepository.save(newUser);
		log.info("*********User Registered*******");

		return APIResponseDTO.builder().message("User Registered successfully").status(HttpStatus.OK.value())
				.timeStamp(Instant.now()).build();

	}

	@Transactional
	@Override
	public APIResponseDTO login(UserLoginForm loginForm) {
		log.info("*********signing user*******");

		Optional<User> byEmail = userRepository.findByEmail(loginForm.getEmail());
		if (!byEmail.isPresent())
			new UsernameNotFoundException("User not found");
		User user = byEmail.get();
		System.out.println(passwordEncoder.matches(loginForm.getPassword(), user.getPassword()));
		if (passwordEncoder.matches(loginForm.getPassword(), user.getPassword())) {
			String token = jwtTokenProvider.createToken(loginForm.getEmail(), user.getRoles());
			log.info("*********User logged in*******");

			return APIResponseDTO.builder().data(token).status(HttpStatus.OK.value()).success(true).build();

		} else {
			log.info("*********User logged in failed*******");

			throw new BadCredentialsException("Invalid email/password supplied");
		}

	}

	@Override
	public APIResponseDTO getUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public APIResponseDTO deleteUserById(List<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public APIResponseDTO updateUser(Long id, UserUpdateForm userUpdateForm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public APIResponseDTO getUserByUserName(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
