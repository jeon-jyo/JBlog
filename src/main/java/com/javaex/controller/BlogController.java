package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/blog")
public class BlogController {
	
	// 로그아웃
	@RequestMapping(value="/logout/{id}", method= { RequestMethod.GET, RequestMethod.POST})
	public String logout(@PathVariable(value="id") String id, HttpSession session) {
		System.out.println("BlogController.logout()");
		
		session.invalidate();
		
		return "redirect:/" + id;
	}

}