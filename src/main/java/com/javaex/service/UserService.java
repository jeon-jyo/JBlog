package com.javaex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BlogDao;
import com.javaex.dao.UserDao;
import com.javaex.vo.BlogVo;
import com.javaex.vo.CategoryVo;
import com.javaex.vo.UserVo;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private BlogDao blogDao;
	
	// 중복체크 ajax
	public boolean checkId(UserVo userVo) {
		System.out.println("UserService.checkId()");
		
		int count = userDao.checkId(userVo);
		if(count == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	// 회원가입
	public void userInsert(UserVo userVo) {
		System.out.println("UserService.userInsert()");
		
		// 회원가입
		int count = userDao.userInsert(userVo);
		if(count == 1) {
			System.out.println("등록 성공");
			
			BlogVo blogVo = new BlogVo();
			blogVo.setId(userVo);
			blogVo.setBlogTitle(userVo.getUserName() + "의 블로그입니다.");
			
			// 블로그 생성
			int insertCnt = blogDao.blogInsert(blogVo);
			if(insertCnt == 1) {
				System.out.println("생성 완료");
				
				CategoryVo categoryVo = new CategoryVo();
				categoryVo.setId(blogVo);
				categoryVo.setCateName("미분류");
				categoryVo.setDescription("기본으로 만들어지는 카테고리 입니다.");
				
				// 기본 카테고리 생성
				int cnt = blogDao.categoryInsert(categoryVo);
				if(cnt == 1) {
					System.out.println("생성 모두 완료");
					
				} else {
					System.out.println("생성 실패");
				}
			} else {
				System.out.println("생성 실패");
			}
		} else {
			System.out.println("등록 실패");
		}
	}
	
	// 로그인
	public UserVo userLogin(UserVo userVo) {
		System.out.println("UserService.userLogin()");
		
		UserVo authUser = userDao.userLogin(userVo);
		
		return authUser;
	}
	
}
