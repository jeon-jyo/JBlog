package com.javaex.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.UserVo;

@Repository
public class UserDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	// 중복체크 ajax
	public int checkId(UserVo userVo) {
		System.out.println("UserDao.checkId()");
		
		int count = sqlSession.selectOne("user.checkId", userVo);
		
		return count;
	}
	
	// 회원가입
	public int userInsert(UserVo userVo) {
		System.out.println("UserDao.userInsert()");
		
		int count = sqlSession.insert("user.userInsert", userVo);
		
		return count;
	}
	
	// 로그인
	public UserVo userLogin(UserVo userVo) {
		System.out.println("UserDao.userLogin()");
		
		UserVo authUser = sqlSession.selectOne("user.selectAuthUser", userVo);
		
		return authUser;
	}
	
}
