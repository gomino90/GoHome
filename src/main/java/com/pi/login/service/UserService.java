package com.pi.login.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pi.login.model.RoleType;
import com.pi.login.model.User;
import com.pi.login.repository.UserRepository;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@org.springframework.transaction.annotation.Transactional(readOnly = true)//정합성(같은데이터)
	public User 회원찾기(String username){
		User user= userRepository.findByUsername(username).orElseGet(()->{
			return new User();
		});
		return user;
	}
//	@org.springframework.transaction.annotation.Transactional
	public void 회원탈퇴 (int id) {
		userRepository.deleteById(id);
			
	}
	@org.springframework.transaction.annotation.Transactional
	public Optional<User> 회원찾자(int id) {
		return userRepository.findById(id);
		
	}
	@org.springframework.transaction.annotation.Transactional
	public void 회원가입(User user) {
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
		}
	@org.springframework.transaction.annotation.Transactional
	public void 회원수정(User user) {
		User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원찾기 실패");
		});
		
		if(persistance.getOauth()==null||persistance.getOauth().equals("")) {
			String rawPassword = user.getPassword();
			String encPassword = encoder.encode(rawPassword);
			persistance.setPassword(encPassword);
			 persistance.setUsername(user.getUsername());
			 persistance.setUserId(user.getUserId());
			 persistance.setEmail(user.getEmail());
		}
	
		
			}
}
