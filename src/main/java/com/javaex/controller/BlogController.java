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
import com.javaex.vo.CommentVo;
import com.javaex.vo.JsonResultVo;
import com.javaex.vo.PostVo;
import com.javaex.vo.UserVo;

@Controller
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	// 블로그 메인
	@RequestMapping(value="/{id}", method= { RequestMethod.GET, RequestMethod.POST})
	public String blogMain(@PathVariable(value="id") String id,
			@RequestParam(value="cateNo", required=false, defaultValue="0") int cateNo,
			@RequestParam(value="postNo", required=false, defaultValue="0") int postNo,
			@RequestParam(value="crtPage", required=false, defaultValue="1") int crtPage, Model model) {
		System.out.println("BlogController.blogMain()");
		System.out.println("id : " + id);
		System.out.println("cateNo : " + cateNo);
		System.out.println("postNo : " + postNo);
		System.out.println("crtPage : " + crtPage);
		
		BlogVo blogVo = blogService.blogDetail(id);
		List<CategoryVo> categoryList = blogService.cateList(id);
		
		Map<String, Object> postPageMap = null;
		if(cateNo == 0) {
			postPageMap = blogService.postListPaging(categoryList.get(0).getCateNo(), crtPage);
		} else {
			postPageMap = blogService.postListPaging(cateNo, crtPage);
		}
		
		List<PostVo> postList = (List<PostVo>) postPageMap.get("postList");
		PostVo postVo = null;
		if(postNo == 0) {
			if(postList.size() != 0) {
				postVo = postList.get(0);
			}
		} else {
			postVo = blogService.postDetail(postNo);
		}
		
		model.addAttribute("blogVo", blogVo);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("postPageMap", postPageMap);
		model.addAttribute("postVo", postVo);
		
		return "blog/blog-main";
	}
	
	// 코멘트 목록 ajax
	@ResponseBody
	@RequestMapping(value="/{no}/blog/commentList", method= { RequestMethod.GET, RequestMethod.POST})
	public JsonResultVo blogCommentList(@PathVariable(value="no") int postNo) {
		System.out.println("BlogController.blogCommentList()");
		System.out.println("postNo : " + postNo);
		
		List<CommentVo> commentList = blogService.commentList(postNo);
		
		JsonResultVo jsonResultVo = new JsonResultVo();
		jsonResultVo.success(commentList);
		
		return jsonResultVo;
	}
	
	// 코멘트 추가 ajax
	@ResponseBody
	@RequestMapping(value="/{no}/blog/commentAdd", method= { RequestMethod.GET, RequestMethod.POST})
	public JsonResultVo blogCommentAdd(@PathVariable(value="no") int postNo,
			@ModelAttribute CommentVo commentVo, HttpSession session) {
		System.out.println("BlogController.blogCommentAdd()");
		
		PostVo postVo = new PostVo();
		postVo.setPostNo(postNo);
		commentVo.setPostNo(postVo);
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		commentVo.setUserNo(authUser);
		
		CommentVo vo = blogService.commentAdd(commentVo);
		System.out.println("vo : " + vo);
		
		JsonResultVo jsonResultVo = new JsonResultVo();
		if(vo != null) {
			System.out.println("성공 : ");
			jsonResultVo.success(vo);
			
			return jsonResultVo;
		} else {
			System.out.println("실패 : ");
			jsonResultVo.fail("commentAdd 실패");
			
			return jsonResultVo;
		}
	}
	
	// 코멘트 삭제 ajax
	@ResponseBody
	@RequestMapping(value="/{no}/blog/commentDelete", method= { RequestMethod.GET, RequestMethod.POST})
	public JsonResultVo blogCommentDelete(@PathVariable(value="no") int cmtNo) {
		System.out.println("BlogController.adminCategoryDelete()");
		System.out.println("cmtNo : " + cmtNo);
		
		int count = blogService.commentDelete(cmtNo);
		
		JsonResultVo jsonResultVo = new JsonResultVo();
		if(count != 0) {
			jsonResultVo.success("");
			
			return jsonResultVo;
		} else {
			jsonResultVo.fail("commentDelete 실패");
			
			return jsonResultVo;
		}
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
			@RequestParam(value="categoryNo") int cateNo, @ModelAttribute PostVo postVo) {
		System.out.println("BlogController.adminWrite()");
		
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setCateNo(cateNo);
		postVo.setCateNo(categoryVo);
		
		blogService.postInsert(postVo);

		return "redirect:/" + id + "/admin/writeForm";
	}

}