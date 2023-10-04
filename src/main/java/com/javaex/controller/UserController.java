package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.UserService;
import com.javaex.vo.JsonResultVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	// 회원가입폼
	@RequestMapping(value="/joinForm", method= { RequestMethod.GET, RequestMethod.POST})
	public String joinForm() {
		System.out.println("UserController.joinForm()");
		
		return "user/joinForm";
	}

	// 중복체크 ajax
	@ResponseBody
	@RequestMapping(value="/checkId", method= { RequestMethod.GET, RequestMethod.POST})
	public JsonResultVo checkId(@ModelAttribute UserVo userVo) {
		System.out.println("UserController.checkId()");
		System.out.println("id : " + userVo.getId());
		
		boolean check = userService.checkId(userVo);
		System.out.println("checkId : " + check);
		
		JsonResultVo jsonResultVo = new JsonResultVo();
		jsonResultVo.success(check);
		
		return jsonResultVo;
	}
	
	// 회원가입
	@RequestMapping(value="/join", method= { RequestMethod.GET, RequestMethod.POST})
	public String join(@ModelAttribute UserVo userVo) {
		System.out.println("UserController.join()");
		
		userService.userInsert(userVo);

		return "user/joinSuccess";
	}
	
	// 로그인폼
	@RequestMapping(value="/loginForm", method= { RequestMethod.GET, RequestMethod.POST})
	public String loginForm() {
		System.out.println("UserController.loginForm()");
		
		return "user/loginForm";
	}
	
	// 로그인
	@RequestMapping(value="/login", method= { RequestMethod.GET, RequestMethod.POST})
	public String login(@ModelAttribute UserVo userVo, HttpSession session, Model model) {
		System.out.println("UserController.login()");
		
		UserVo authUser = userService.userLogin(userVo);
		
		if(authUser != null) {
			System.out.println("로그인 성공");
			System.out.println("authUser : " + authUser);
			
			session.setAttribute("authUser", authUser);
			
			return "redirect:/";

		} else {
			System.out.println("로그인 실패");
			
			model.addAttribute("result", "fail");
			
			return "redirect:/user/loginForm";
		}
	}
	
	// 로그아웃
	@RequestMapping(value="/logout", method= { RequestMethod.GET, RequestMethod.POST})
	public String logout(HttpSession session) {
		System.out.println("UserController.logout()");
		
		session.invalidate();
		
		return "redirect:/";
	}
	
}