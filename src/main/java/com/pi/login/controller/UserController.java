package com.pi.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pi.login.model.KakaProfile;
import com.pi.login.model.OAuthToken;
import com.pi.login.model.User;
import com.pi.login.service.UserService;


@Controller
public class UserController {
	
	@org.springframework.beans.factory.annotation.Value("${cos.key}")
	private String cosKey;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		
		return "user/joinForm";
		
	}

	@GetMapping("/auth/loginForm")
	public String loinForm() {
		
		return "user/loginForm";
		
	}
	@GetMapping("/user/deleteForm")
	public String deleteForm() {
		
		return "user/deleteForm";
		
	}
	@GetMapping("/user/{id}")
	public String findById(@PathVariable int id,Model model){
		model.addAttribute("user",userService.회원찾자(id));
		return "user/{id}";
	}
	@GetMapping("/auth/kakao/callback")	
	public String kakaoCallback(String code) {
				
		RestTemplate rt = new RestTemplate();
			
		org.springframework.http.HttpHeaders headers= new org.springframework.http.HttpHeaders();		
		headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
		
		MultiValueMap<String,String>params=new LinkedMultiValueMap<>();
		params.add("grant_type","authorization_code");
		params.add("client_id","b29649527cd86bf17ae7e595f7ef8f70");
		params.add("redirect_uri","http://localhost:8000/auth/kakao/callback");
		params.add("code", code);
		
		HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest=
				new HttpEntity<>(params,headers);
		
		ResponseEntity<String>response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class
						
		);
		
		
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
		oauthToken = objectMapper.readValue(response.getBody(),OAuthToken.class);
		} catch (JsonMappingException e) {			
			e.printStackTrace();
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
		}
		

		System.out.println("zzkdkfs"+oauthToken.getAccess_token());
		
		RestTemplate rt2 = new RestTemplate();
		org.springframework.http.HttpHeaders headers2= new org.springframework.http.HttpHeaders();		
		headers2.add("Authorization","Bearer "+oauthToken.getAccess_token());
		headers2.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
	
		
		HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest2=
				new HttpEntity<>(headers2);
		
		ResponseEntity <String>response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoProfileRequest2,
				String.class
						
		);
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaProfile kakaProfile = null;
		try {
			kakaProfile = objectMapper2.readValue(response2.getBody(),KakaProfile.class);
		} catch (JsonMappingException e) {			
			e.printStackTrace();
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
		}
		
		System.out.println("카카오 아이디:"+kakaProfile.getId());
		System.out.println("카카오 이메일:"+kakaProfile.getKakao_account().getEmail());
		
		//
		System.out.println("블로그서버 유저네임"+kakaProfile.getKakao_account().getEmail()+"_"+kakaProfile.getId());
		System.out.println("블로그서버 이메일"+kakaProfile.getKakao_account().getEmail());
	    System.out.println("블로그서버 패스워드:"+cosKey);
		
		User kakaoUser =User.builder()
				.username(kakaProfile.getKakao_account().getEmail()+"_"+kakaProfile.getId())
				.password(cosKey)
				.userEmail(kakaProfile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();

		System.out.println(kakaoUser.getUsername());
		User originuser = userService.회원찾기(kakaoUser.getUsername());		
		if(originuser.getUsername()==null) {
			System.out.println("기존회원이 아니기에 자동회원가입을 진행합닏,ㅏ.,.");
			userService.회원가입(kakaoUser);
			}
			
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(),cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	
		return "redirect:/";
		
	}
		
	@GetMapping("/user/updateForm")
	public String updateForm() {
		
		return "user/updateForm";
		
	}
}
