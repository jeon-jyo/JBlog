package com.javaex.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BlogVo;
import com.javaex.vo.CategoryVo;
import com.javaex.vo.CommentVo;
import com.javaex.vo.PostVo;

@Repository
public class BlogDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	// 블로그 목록 + 페이징
	public List<BlogVo> blogListPaging(Map<String, Object> listMap) {
		System.out.println("BlogDao.blogListPaging()");
		
		List<BlogVo> blogList = sqlSession.selectList("blog.blogListPaging", listMap);
		
		return blogList;
	}
	
	// 전체 블로그갯수 + 검색
	public int selectBlogCnt(String keyword) {
		System.out.println("BlogDao.selectBlogCnt()");
		
		int totalCount = sqlSession.selectOne("blog.selectBlogCnt", keyword);
		
		return totalCount;
	}
	
	// 블로그 생성
	public int blogInsert(BlogVo blogVo) {
		System.out.println("BlogDao.blogInsert()");
		
		int count = sqlSession.insert("blog.blogInsert", blogVo);
		
		return count;
	}
	
	// 기본 카테고리 생성
	public int categoryInsert(CategoryVo categoryVo) {
		System.out.println("BlogDao.categoryInsert()");
		
		int count = sqlSession.insert("blog.categoryInsert", categoryVo);
		
		return count;
	}

	// 블로그 메인 + 헤더
	public BlogVo blogDetail(String id) {
		System.out.println("BlogDao.blogDetail()");
		
		BlogVo blogVo = sqlSession.selectOne("blog.blogDetail", id);
		
		return blogVo;
	}

	// 카테고리 목록
	public List<CategoryVo> cateList(String id) {
		System.out.println("BlogDao.cateList()");
		
		List<CategoryVo> categoryList = sqlSession.selectList("blog.cateList", id);
		
		return categoryList;
	}
	
	// 포스트 목록 + 페이징
	public List<PostVo> postListPaging(Map<String, Object> listMap) {
		System.out.println("BlogDao.postListPaging()");
		
		List<PostVo> postList = sqlSession.selectList("blog.postListPaging", listMap);
		
		return postList;
	}
	
	// 전체 글갯수
	public int selectTotalCnt(int cateNo) {
		System.out.println("BlogDao.selectTotalCnt()");
		
		int totalCount = sqlSession.selectOne("blog.selectTotalCnt", cateNo);
		
		return totalCount;
	}
	
	// 포스트 상세
	public PostVo postDetail(int postNo) {
		System.out.println("BlogDao.postDetail()");
		
		PostVo postVo = sqlSession.selectOne("blog.postDetail", postNo);
		
		return postVo;
	}
	
	// 코멘트 목록 ajax
	public List<CommentVo> commentList(int postNo) {
		System.out.println("BlogDao.commentList()");
				
		List<CommentVo> commentList = sqlSession.selectList("blog.commentList", postNo);
		
		return commentList;
	}
	
	// 코멘트 추가 ajax
	public int selectCmtKey(CommentVo commentVo) {
		System.out.println("BlogDao.selectCmtKey()");
		
		int count = sqlSession.insert("blog.selectCmtKey", commentVo);
		
		return count;
	}

	// 코멘트 정보 ajax
	public CommentVo selectComment(CommentVo commentVo) {
		System.out.println("BlogDao.selectComment()");
		
		CommentVo vo = sqlSession.selectOne("blog.selectComment", commentVo);
		
		return vo;
	}
	
	// 코멘트 삭제 ajax
	public int commentDelete(int cmtNo) {
		System.out.println("BlogDao.commentDelete()");
		
		int count = sqlSession.delete("blog.commentDelete", cmtNo);
		
		return count;
	}
	
	// ----- 블로그 관리 ------------------------------
	
	// 블로그 관리 - 기본 수정
	public int basicUpdate(BlogVo blogVo) {
		System.out.println("BlogDao.basicUpdate()");
		
		int count = sqlSession.update("blog.basicUpdate", blogVo);
		
		return count;
	}
	
	// 블로그 관리 - 카테고리 목록 + 포스트 수 ajax
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
	
	// 카테고리 정보 ajax
	public Map<String, Object> selectCategory(CategoryVo categoryVo) {
		System.out.println("BlogDao.selectCategory()");
		
		Map<String, Object> cateMap = sqlSession.selectOne("blog.selectCategory", categoryVo);
		
		return cateMap;
	}

	// 블로그 관리 - 카테고리 삭제 ajax
	public int categoryDelete(int cateNo) {
		System.out.println("BlogDao.categoryDelete()");
		
		int count = sqlSession.delete("blog.categoryDelete", cateNo);
		
		return count;
	}

	// 블로그 관리 - 포스트 등록
	public int postInsert(PostVo postVo) {
		System.out.println("BlogDao.postInsert()");
		
		int count = sqlSession.insert("blog.postInsert", postVo);
		
		return count;
	}

}
