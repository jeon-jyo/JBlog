package com.javaex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.service.BlogService;
import com.javaex.vo.BlogVo;

@Controller
public class MainController {

	@Autowired
	private BlogService blogService;
	
	// 메인
	@RequestMapping(value="", method= { RequestMethod.GET, RequestMethod.POST})
	public String main() {
		System.out.println("MainController.main()");
		
		return "main/index";
	}
	
	// 블로그 메인
	@RequestMapping(value="/{id}", method= { RequestMethod.GET, RequestMethod.POST})
	public String blogMain(@PathVariable(value="id") String id, Model model) {
		System.out.println("MainController.blogMain()");
		System.out.println("id : " + id);
		
		BlogVo blogVo = blogService.blogDetail(id);
		System.out.println("blogVo : " + blogVo);
		
		model.addAttribute("blogVo", blogVo);
		
		return "blog/blog-main";
	}
	
}