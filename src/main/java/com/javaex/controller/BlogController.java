package com.javaex.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.service.BlogService;
import com.javaex.vo.BlogVo;
import com.javaex.vo.CategoryVo;
import com.javaex.vo.JsonResultVo;
import com.javaex.vo.PostVo;
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
		List<CategoryVo> categoryList = blogService.cateList(id);
		List<PostVo> postList = blogService.postList(categoryList.get(0).getCateNo());
		PostVo postVo = null;
		if(postList.size() != 0) {
			postVo = postList.get(0);
		}
		
		model.addAttribute("blogVo", blogVo);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("postList", postList);
		model.addAttribute("postVo", postVo);
		
		return "blog/blog-main";
	}

	// 블로그 메인 - 카테고리 선택
	@RequestMapping(value="/blog/categoty", method= { RequestMethod.GET, RequestMethod.POST})
	public String blogCategory(@PathVariable(value="id") String id, Model model) {
		System.out.println("BlogController.blogCategory()");
		System.out.println("id : " + id);
		
		BlogVo blogVo = blogService.blogDetail(id);
		List<CategoryVo> categoryList = blogService.cateList(id);
		List<PostVo> postList = blogService.postList(categoryList.get(0).getCateNo());
		PostVo postVo = null;
		if(postList.size() != 0) {
			postVo = postList.get(0);
		}
		
		model.addAttribute("blogVo", blogVo);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("postList", postList);
		model.addAttribute("postVo", postVo);
		
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
	
	// 블로그 관리 - 카테고리
	@RequestMapping(value="/{id}/admin/category", method= { RequestMethod.GET, RequestMethod.POST})
	public String adminCategory(@PathVariable(value="id") String id,  Model model) {
		System.out.println("BlogController.adminCategory()");
		System.out.println("id : " + id);
		
		BlogVo blogVo = blogService.blogDetail(id);
		
		model.addAttribute("blogVo", blogVo);
		
		return "blog/admin/blog-admin-cate";
	}
	
	// 블로그 관리 - 카테고리 목록 + 포스트 수 ajax
	@ResponseBody
	@RequestMapping(value="/{id}/admin/categoryList", method= { RequestMethod.GET, RequestMethod.POST})
	public JsonResultVo adminCategoryList(@PathVariable(value="id") String id) {
		System.out.println("BlogController.adminCategoryList()");
		System.out.println("id : " + id);
		
		List<Map<String, Object>> cateMapList = blogService.categoryList(id);
		
		JsonResultVo jsonResultVo = new JsonResultVo();
		jsonResultVo.success(cateMapList);
		
		return jsonResultVo;
	}
	
	// 블로그 관리 - 카테고리 추가 ajax
	@ResponseBody
	@RequestMapping(value="/admin/categoryAdd", method= { RequestMethod.GET, RequestMethod.POST})
	public JsonResultVo adminCategoryAdd(@ModelAttribute CategoryVo categoryVo, HttpSession session) {
		System.out.println("BlogController.adminCategoryAdd()");
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		BlogVo blogVo = new BlogVo();
		blogVo.setId(authUser);
		categoryVo.setId(blogVo);
		
		Map<String, Object> cateMap = blogService.categoryAdd(categoryVo);
		
		JsonResultVo jsonResultVo = new JsonResultVo();
		if(cateMap != null) {
			jsonResultVo.success(cateMap);
			
			return jsonResultVo;
		} else {
			jsonResultVo.fail("categoryAdd 실패");
			
			return jsonResultVo;
		}
	}
	
	// 블로그 관리 - 카테고리 삭제 ajax
	@ResponseBody
	@RequestMapping(value="/{id}/admin/categoryDelete", method= { RequestMethod.GET, RequestMethod.POST})
	public JsonResultVo adminCategoryDelete(@PathVariable(value="id") String id, @RequestParam(value="cateNo") int cateNo) {
		System.out.println("BlogController.adminCategoryDelete()");
		
		List<Map<String, Object>> cateMapList = blogService.categoryDelete(cateNo, id);
		
		JsonResultVo jsonResultVo = new JsonResultVo();
		if(cateMapList != null) {
			jsonResultVo.success(cateMapList);
			
			return jsonResultVo;
		} else {
			jsonResultVo.fail("categoryDelete 실패");
			
			return jsonResultVo;
		}
	}
	
	// 블로그 관리 - 글작성폼
	@RequestMapping(value="/{id}/admin/writeForm", method= { RequestMethod.GET, RequestMethod.POST})
	public String adminWriteForm(@PathVariable(value="id") String id, Model model) {
		System.out.println("BlogController.adminWriteForm()");
		System.out.println("id : " + id);
		
		BlogVo blogVo = blogService.blogDetail(id);
		List<CategoryVo> categoryList = blogService.cateList(id);
		
		model.addAttribute("blogVo", blogVo);
		model.addAttribute("categoryList", categoryList);
		
		return "blog/admin/blog-admin-write";
	}
	
	// 블로그 관리 - 포스트 등록
	@RequestMapping(value="/{id}/admin/write", method= { RequestMethod.GET, RequestMethod.POST})
	public String adminWrite(@PathVariable(value="id") String id,
			@RequestParam(value="no") int no, @ModelAttribute PostVo postVo) {
		System.out.println("BlogController.adminWrite()");
		
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setCateNo(no);
		postVo.setCateNo(categoryVo);
		
		blogService.postInsert(postVo);

		return "redirect:/" + id + "/admin/writeForm";
	}

}