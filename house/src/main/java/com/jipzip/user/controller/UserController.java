// package com.jipzip.user.controller;

// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.GetMapping;

// import com.jipzip.user.model.service.UserService;

// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import jakarta.servlet.http.HttpSession;

// @Controller("/user")
// public class UserController {
	
// 	private UserService userservice;

// 	public UserController(UserService userservice) {
// 		super();
// 		this.userservice = userservice;
// 	}
	
// 	// 로그아웃
// 	@GetMapping("/logout")
// 	private String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
// 		session.invalidate();
// 		return "redirect:/index";
// 	}

	

	
	
// }
