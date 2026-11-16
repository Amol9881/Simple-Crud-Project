package com.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.dto.AuthResponse;
import com.crud.dto.LoginRequest;
import com.crud.dto.RegisterRequest;
import com.crud.model.Role;
import com.crud.model.User;
import com.crud.repositary.UserRepository;
import com.crud.security.JwtUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest req) {
		return create(req, Role.USER, "User registered successfully");
	}

	// फक्त शिकण्यासाठी खुला. नंतर खाली सांगितल्याप्रमाणे ADMIN साठीच बंद करा
	@PostMapping("/register-admin")
	public ResponseEntity<String> registerAdmin(@Valid @RequestBody RegisterRequest req) {
		return create(req, Role.ADMIN, "Admin registered successfully");
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {

		// चुकीचा password असेल तर exception येते -> 401
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(req.username().trim(), req.password()));

		User user = userRepository.findByUsername(req.username().trim()).orElseThrow();
		String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());

		return ResponseEntity.ok(new AuthResponse(token, "Bearer", user.getUsername(), user.getRole().name()));
	}

	private ResponseEntity<String> create(RegisterRequest req, Role role, String okMessage) {

		String username = req.username().trim();

		if (userRepository.existsByUsername(username)) {
			return ResponseEntity.badRequest().body("Username already exists");
		}

		User user = new User();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(req.password()));
		user.setRole(role);
		userRepository.save(user);

		return ResponseEntity.ok(okMessage);
	}
}