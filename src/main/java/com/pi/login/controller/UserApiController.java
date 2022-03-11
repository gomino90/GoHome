package com.pi.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pi.login.dto.ResponseDto;
import com.pi.login.model.User;
import com.pi.login.service.UserService;



@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private BCryptPasswordEncoder encode;

	@GetMapping("/api/user/{id}")
	public ResponseDto<Integer>findById(@PathVariable int id){
		userService.회원찾자(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
	
	userService.회원가입(user);
			return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteById( int id){
		userService.회원탈퇴(id);
		
	}
		
	@PutMapping("/user")
	public ResponseDto<Integer>update(@RequestBody User user){
		userService.회원수정(user);
	
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
		
	}
	
}