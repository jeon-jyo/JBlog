package com.javaex.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.BlogService;

@Controller
public class MainController {
	
	@Autowired
	private BlogService blogService;

	// 메인 + 블로그 검색 목록 + 페이징
	@RequestMapping(value="", method= { RequestMethod.GET, RequestMethod.POST})
	public String main(@RequestParam(value="crtPage", required=false, defaultValue="1") int crtPage,
			@RequestParam(value="keyword", required=false, defaultValue="") String keyword, Model model) {
		System.out.println("MainController.main()");
		
		Map<String, Object> blogPageMap =  blogService.blogListPaging(crtPage, keyword);
		
		model.addAttribute("blogPageMap", blogPageMap);
		
		return "main/index";
	}
	
}