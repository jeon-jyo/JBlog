package com.javaex.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BlogVo;
import com.javaex.vo.CategoryVo;

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

	// 블로그 메인 + 블로그 관리 - 기본
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
	
	// 블로그 관리 - 카테고리 리스트 + 포스트 수 ajax
	public List<Map<String, Object>> categoryList(String id) {
		System.out.println("BlogDao.categoryList()");
		
		List<Map<String, Object>> cateMapList = sqlSession.selectList("blog.categoryList", id);
		
		return cateMapList;
	}

	// 블로그 관리 - 카테고리 추가 ajax
	public int selectKey(CategoryVo categoryVo) {
		System.out.println("BlogDao.selectKey()");
		
		int count = sqlSession.insert("blog.selectKey", categoryVo);
		
		return count;
	}
	
	// 카테고리 정보
	public Map<String, Object> selectCategory(int cateNo) {
		System.out.println("BlogDao.selectCategory()");
		
		Map<String, Object> cateMap = sqlSession.selectOne("blog.selectCategory", cateNo);
		
		return cateMap;
	}

	// 블로그 관리 - 카테고리 삭제 ajax
	public int categoryDelete(CategoryVo categoryVo) {
		System.out.println("BlogDao.categoryDelete()");
		
		int count = sqlSession.delete("blog.categoryDelete", categoryVo);
		
		return count;
	}

}
