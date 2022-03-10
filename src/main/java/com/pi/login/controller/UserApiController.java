package com.pi.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
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

	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
	
	userService.회원가입(user);
			return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	@DeleteMapping("/api/user/{id}")
	public ResponseDto<Integer>deleteById(@PathVariable int id){
		userService.회원삭제하기(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
		
	@PutMapping("/user")
	public ResponseDto<Integer>update(@RequestBody User user){
		userService.회원수정(user);
	
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
		
	}
	
//	@PostMapping("/api/user/login")
//	public ResponseDto<Integer>login(@RequestBody User user){
//		System.out.println("UserApiController:login호출됨,");
//		
//		User principal = userService.로그인(user);
//		if(principal!=null) {
//			session.setAttribute("principal",principal);
//		
//		}
//		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
//	}
}
