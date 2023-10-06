package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.service.BlogService;
import com.javaex.vo.BlogVo;
import com.javaex.vo.UserVo;

@Controller
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	// 블로그 메인
	@RequestMapping(value="/{id}", method= { RequestMethod.GET, RequestMethod.POST})
	public String blogMain(@PathVariable(value="id") String id, Model model) {
		System.out.println("BlogController.blogMain()");
		System.out.println("id : " + id);
		
		BlogVo blogVo = blogService.blogDetail(id);
		
		model.addAttribute("blogVo", blogVo);
		
		return "blog/blog-main";
	}
	
	// 블로그 로그아웃
	@RequestMapping(value="/logout/{id}", method= { RequestMethod.GET, RequestMethod.POST})
	public String blogLogout(@PathVariable(value="id") String id, HttpSession session) {
		System.out.println("BlogController.blogLogout()");
		
		session.invalidate();
		
		return "redirect:/" + id;
	}
	
	// ----- 블로그 관리 ------------------------------
	
	// 블로그 관리 - 기본
	@RequestMapping(value="/{id}/admin/basic", method= { RequestMethod.GET, RequestMethod.POST})
	public String adminBasic(@PathVariable(value="id") String id,  Model model) {
		System.out.println("BlogController.adminBasic()");
		System.out.println("id : " + id);
		
		BlogVo blogVo = blogService.blogDetail(id);
		
		model.addAttribute("blogVo", blogVo);
		
		return "blog/admin/blog-admin-basic";
	}
	
	// 블로그 관리 - 기본 수정
	@RequestMapping(value="/admin/basicUpdate", method= { RequestMethod.GET, RequestMethod.POST})
	public String adminBasicUpdate(@ModelAttribute BlogVo blogVo,
			@RequestParam(value="file") MultipartFile file, HttpSession session) {
		System.out.println("BlogController.adminBasicUpdate()");
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		blogVo.setId(authUser);
		
		blogService.basicUpdate(blogVo, file);
		
		return "redirect:/" + authUser.getId() + "/admin/basic";
	}
}