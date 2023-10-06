package com.javaex.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BlogVo;

@Repository
public class BlogDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	// 블로그 생성
	public int blogInsert(BlogVo blogVo) {
		System.out.println("BlogDao.blogInsert()");
		
		int count = sqlSession.insert("blog.blogInsert", blogVo);
		
		return count;
	}

	// 블로그 메인
	public BlogVo blogDetail(String id) {
		System.out.println("BlogDao.blogDetail()");
		
		BlogVo blogVo = sqlSession.selectOne("blog.blogDetail", id);
		
		return blogVo;
	}

	// ----- 블로그 관리 ------------------------------
	
	// 블로그 관리 - 기본 수정
	public int basicUpdate(BlogVo blogVo) {
		System.out.println("BlogDao.basicUpdate()");
		
		int count = sqlSession.update("blog.basicUpdate", blogVo);
		
		return count;
	}
	
}
