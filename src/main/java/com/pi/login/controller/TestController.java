package com.pi.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class TestController {
	
	@GetMapping(value =  "/test")
	public String test(
			HttpServletRequest request,
			HttpServletResponse response
			
			) throws Exception {
		
		System.out.println("test");
		return "";
		
	}
	
}
