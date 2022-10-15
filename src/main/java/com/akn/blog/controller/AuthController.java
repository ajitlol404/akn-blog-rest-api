package com.akn.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akn.blog.entity.Role;
import com.akn.blog.entity.User;
import com.akn.blog.jwt.JwtTokenUtil;
import com.akn.blog.payload.AuthRequest;
import com.akn.blog.payload.AuthResponse;
import com.akn.blog.payload.SignUpDTO;
import com.akn.blog.repository.RoleRepository;
import com.akn.blog.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	// It will create a default user and roles in the system
	@GetMapping("/start")
	public ResponseEntity<String> createDefaultUsersAndRole() {
		long userCount = userRepository.count();
		long roleCount = roleRepository.count();

		if (userCount == 0 && roleCount == 0) {
			// Creating Default Roles
			Role admin = new Role("ROLE_ADMIN");
			Role editor = new Role("ROLE_EDITOR");
			Role customer = new Role("ROLE_CUSTOMER");

			roleRepository.saveAll(List.of(admin, editor, customer));

			// Creating default User
			User user = new User();
			user.setName("akn");
			user.setUsername("akn");
			user.setEmail("akn@mail.com");
			user.setPassword(passwordEncoder.encode("12345678"));
			User savedUser = userRepository.save(user);

			// Assigning role to admin user
			Long userId = savedUser.getId();
			User aUser = userRepository.findById(userId).get();
			user.addRole(new Role(1L, "ROLE_ADMIN"));
			userRepository.save(aUser);

			return ResponseEntity.ok(
					"Default User and Role created, user - akn, username - akn, email - akn@mail.com, password - 12345678");
		} else {
			return new ResponseEntity<>(
					"Default User and Role already exists, try dropping database and then retry hitting start api",
					HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> authenticateUser(@RequestBody AuthRequest authRequest) {

		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authRequest.getUsernameOrEmail(), authRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			// Calling service to create jwt token
			String accessToken = jwtTokenUtil.generateAccessToken(userDetails);

			AuthResponse response = new AuthResponse(accessToken, userDetails.getUsername());
			return ResponseEntity.ok(response);
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

	}

	@PostMapping("/signup")
	public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpDTO signUpDTO) {

		// Check if username exists
		if (userRepository.existsByUsername(signUpDTO.getUsername())) {
			return new ResponseEntity<>("Username already exist", HttpStatus.BAD_REQUEST);
		}

		// Check if email exists
		if (userRepository.existsByEmail(signUpDTO.getEmail())) {
			return new ResponseEntity<>("Email already exist", HttpStatus.BAD_REQUEST);
		}

		User user = new User();
		user.setName(signUpDTO.getName());
		user.setUsername(signUpDTO.getUsername());
		user.setEmail(signUpDTO.getEmail());
		user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));

		User savedUser = userRepository.save(user);

		// Assigning role to newly created user
		Long userId = savedUser.getId();
		User aUser = userRepository.findById(userId).get();
		aUser.addRole(new Role(2L, "ROLE_EDITOR"));
		aUser.addRole(new Role(3L, "ROLE_CUSTOMER"));
		userRepository.save(aUser);

		return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

	}

}
