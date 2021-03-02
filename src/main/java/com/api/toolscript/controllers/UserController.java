package com.api.toolscript.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.api.toolscript.models.User;
import com.api.toolscript.payload.response.MessageResponse;
import com.api.toolscript.repository.UserRepository;

public class UserController {

	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(path="/user/{id_user}")
	public @ResponseBody Optional<User> getUserById(@PathVariable Integer id_user){
		return userRepository.findById(id_user);
	}
	
	@GetMapping(path="/users")
	public @ResponseBody Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@PutMapping(path="/user/changePassword")
	public void changePassword (@RequestBody User user) {
		User res = userRepository.findById(user.getId_user()).get();
		res.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(res);
		
	}
	
	@PutMapping(path="/user/changeUsername")
	public ResponseEntity<?> changeUsername (@RequestBody User user) {
		if (userRepository.existsByUsername(user.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}
		
		User res = userRepository.findById(user.getId_user()).get();
		res.setUsername(user.getUsername());
		userRepository.save(res);
		return ResponseEntity.ok(new MessageResponse("Username successfully changed !"));
	}
	
	@PutMapping(path="/user/changeMail")
	public ResponseEntity<?> changeMail (@RequestBody User user){
		if (userRepository.existsByEmail(user.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}
		
		User res = userRepository.findByMail(user.getEmail()).get();
		res.setEmail(user.getEmail());
		userRepository.save(res);
		return ResponseEntity.ok(new MessageResponse("Email successfully changed !"));
	}
		
	
	
}
