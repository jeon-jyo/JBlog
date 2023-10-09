package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.dao.BlogDao;
import com.javaex.vo.BlogVo;
import com.javaex.vo.CategoryVo;
import com.javaex.vo.CommentVo;
import com.javaex.vo.PostVo;

@Service
public class BlogService {
	
	@Autowired
	private BlogDao blogDao;
	
	// 블로그 검색 목록 + 페이징
	public Map<String, Object> blogListPaging(int crtPage, String keyword) {
		System.out.println("BlogService.blogListPaging()");
		System.out.println("crtPage : " + crtPage);
		System.out.println("keyword : " + keyword);

		// 글목록 계산 //////////////////////////////
		
		int listCnt = 10;
		crtPage = (crtPage > 0) ? crtPage : 1;
		int startRNum = (crtPage-1) * listCnt + 1;
		int endRNum = (startRNum + listCnt) - 1;
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		listMap.put("startRNum", startRNum);
		listMap.put("endRNum", endRNum);
		listMap.put("keyword", keyword);
		List<BlogVo> blogList = blogDao.blogListPaging(listMap);
		
		// 페이징 계산 //////////////////////////////
		
		int pageBtnCount = 5;
		int endPageBtnNo = (int)Math.ceil(crtPage/(double)pageBtnCount)*pageBtnCount;
		int startPageBtnNo = (endPageBtnNo-pageBtnCount)+1;
		
		// 전체 블로그갯수
		int totalCnt = blogDao.selectBlogCnt(keyword);
		System.out.println("totalCnt : " + totalCnt);
		
		// 화살표 유무 //////////////////////////////
		
		boolean next = false;
		if(listCnt * endPageBtnNo < totalCnt) {
			next = true;
		} else {
			endPageBtnNo = (int)Math.ceil(totalCnt/(double)listCnt);
		}
		
		boolean prev = false;
		if(startPageBtnNo != 1) {
			prev = true;
		}
		
		Map<String, Object> blogPageMap = new HashMap<String, Object>();
		blogPageMap.put("startPageBtnNo", startPageBtnNo);
		blogPageMap.put("endPageBtnNo", endPageBtnNo);
		blogPageMap.put("prev", prev);
		blogPageMap.put("next", next);
		blogPageMap.put("blogList", blogList);
		blogPageMap.put("keyword", keyword);

		return blogPageMap;
	}

	// 블로그 메인 + 헤더
	public BlogVo blogDetail(String id) {
		System.out.println("BlogService.blogDetail()");
		
		BlogVo blogVo = blogDao.blogDetail(id);
		
		return blogVo;
	}

	// 카테고리 목록
	public List<CategoryVo> cateList(String id) {
		System.out.println("BlogService.cateList()");
		
		List<CategoryVo> categoryList = blogDao.cateList(id);
		
		return categoryList;
	}
	
	// 포스트 목록 + 페이징
	public Map<String, Object> postListPaging(int cateNo, int crtPage) {
		System.out.println("BlogService.postListPaging()");
		
		// 글목록 계산 //////////////////////////////

		// 페이지당 글갯수	- 한 페이지에 출력되는 글갯수
		int listCnt = 5;
		
		// 현재 페이지		- 파라미터로 받는다
		crtPage = (crtPage > 0) ? crtPage : 1;
		
		// 시작 글번호
		int startRNum = (crtPage-1) * listCnt + 1;
		
		// 끝 글번호
		int endRNum = (startRNum + listCnt) - 1;
		
		Map<String, Object> listMap = new HashMap<String, Object>();
		listMap.put("startRNum", startRNum);
		listMap.put("endRNum", endRNum);
		listMap.put("cateNo", cateNo);
		List<PostVo> postList = blogDao.postListPaging(listMap);
		
		// 페이징 계산 //////////////////////////////
		
		// 페이지당 버튼 갯수
		int pageBtnCount = 5;
		
		// 마지막 버튼 번호
		int endPageBtnNo = (int)Math.ceil(crtPage/(double)pageBtnCount)*pageBtnCount;
		
		// 시작 버튼 번호
		int startPageBtnNo = (endPageBtnNo-pageBtnCount)+1;
		
		// 전체 글갯수
		int totalCnt = blogDao.selectTotalCnt(cateNo);
		System.out.println("totalCnt : " + totalCnt);
		
		// 다음화살표 유무
		boolean next = false;
		if(listCnt * endPageBtnNo < totalCnt) {
			next = true;
		} else {
			// 다음버튼이 없을 때(false) - endPageBtnNo 다시 계산
			endPageBtnNo = (int)Math.ceil(totalCnt/(double)listCnt);
		}
		
		// 이전화살표 유무
		boolean prev = false;
		if(startPageBtnNo != 1) {
			prev = true;
		}
		
		Map<String, Object> postPageMap = new HashMap<String, Object>();
		postPageMap.put("startPageBtnNo", startPageBtnNo);
		postPageMap.put("endPageBtnNo", endPageBtnNo);
		postPageMap.put("prev", prev);
		postPageMap.put("next", next);
		postPageMap.put("postList", postList);

		return postPageMap;
	}
	
	// 포스트 상세
	public PostVo postDetail(int postNo) {
		System.out.println("BlogService.postDetail()");
		
		PostVo postVo = blogDao.postDetail(postNo);
		
		return postVo;
	}
	
	// 코멘트 목록 ajax
	public List<CommentVo> commentList(int postNo) {
		System.out.println("BlogService.commentList()");
		
		List<CommentVo> commentList = blogDao.commentList(postNo);
		System.out.println("commentList.size() : " + commentList.size());
		
		return commentList;
	}
	
	// 코멘트 추가 ajax
	public CommentVo commentAdd(CommentVo commentVo) {
		System.out.println("BlogService.commentAdd()");
		
		int count = blogDao.selectCmtKey(commentVo);
		System.out.println("commentVo : " + commentVo);
		if(count != 0) {
			System.out.println("등록 성공");
			
			// 코멘트 정보 ajax
			CommentVo vo = blogDao.selectComment(commentVo);
			System.out.println("vo : " + vo);
			
			return vo;
		} else {
			System.out.println("등록 실패");
			
			return null;
		}
	}
	
	// 코멘트 삭제 ajax
	public int commentDelete(int cmtNo) {
		System.out.println("BlogService.commentDelete()");
		
		int count = blogDao.commentDelete(cmtNo);
		if(count == 1) {
			System.out.println("삭제 성공");
			
		} else {
			System.out.println("삭제 실패");
		}
		return count;
	}
	
	// ----- 블로그 관리 ------------------------------
	
	// 블로그 관리 - 기본 수정
	public void basicUpdate(BlogVo blogVo, MultipartFile file) {
		System.out.println("BlogService.basicUpdate()");
		System.out.println("file.isEmpty() : " + file.isEmpty());
		
		if(!file.isEmpty()) {
			// 파일 정보
			String saveDir = "C:\\Jiho\\HiMedia\\JavaStudy\\jBlogUpload";
			
			String orgName = file.getOriginalFilename();
			String exName = orgName.substring(orgName.lastIndexOf("."));
			
			String saveName = System.currentTimeMillis()
					+ UUID.randomUUID().toString()
					+ exName;
			
			String filePath = saveDir + "\\" + saveName;
			
			blogVo.setLogoFile(saveName);
			System.out.println("blogVo : " + blogVo);
			
			// DB 연결
			int count = blogDao.basicUpdate(blogVo);
			if(count == 1) {
				System.out.println("수정 성공");
				
				// 서버 파일 저장
				try {
					byte[] fileDate;
					fileDate = file.getBytes();
					
					OutputStream os = new FileOutputStream(filePath);
					BufferedOutputStream bos = new BufferedOutputStream(os);
					
					bos.write(fileDate);
					bos.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("수정 실패");
			}
		} else {
			
			BlogVo vo = blogDao.blogDetail(blogVo.getId().getId());
			blogVo.setLogoFile(vo.getLogoFile());
			System.out.println("blogVo : " + blogVo);
			
			// DB 연결
			int count = blogDao.basicUpdate(blogVo);
			if(count == 1) {
				System.out.println("수정 성공");
			} else {
				System.out.println("수정 실패");
			}
		}
	}
	
	// 블로그 관리 - 카테고리 목록 + 포스트 수 ajax
	public List<Map<String, Object>> categoryList(String id) {
		System.out.println("BlogService.categoryList()");
		
		List<Map<String, Object>> cateMapList = blogDao.categoryList(id);
		System.out.println("cateMapList.size() : " + cateMapList.size());
		
		return cateMapList;
	}
	
	// 블로그 관리 - 카테고리 추가 ajax
	public Map<String, Object> categoryAdd(CategoryVo categoryVo) {
		System.out.println("BlogService.categoryAdd()");
		
		int count = blogDao.selectKey(categoryVo);
		if(count != 0) {
			System.out.println("등록 성공");
			
			// 카테고리 정보 ajax
			Map<String, Object> cateMap = blogDao.selectCategory(categoryVo);
			
			return cateMap;
		} else {
			System.out.println("등록 실패");
			
			return null;
		}
	}
	
	// 블로그 관리 - 카테고리 삭제 ajax
	public List<Map<String, Object>> categoryDelete(int cateNo, String id) {
		System.out.println("BlogService.categoryDelete()");
		
		int count = blogDao.categoryDelete(cateNo);
		if(count == 1) {
			System.out.println("삭제 성공");
			
			List<Map<String, Object>> cateMapList = blogDao.categoryList(id);
			System.out.println("cateMapList.size() : " + cateMapList.size());;
			
			return cateMapList;
		} else {
			System.out.println("삭제 실패");
			
			return null;
		}
	}

	// 블로그 관리 - 포스트 등록
	public void postInsert(PostVo postVo) {
		System.out.println("BlogService.postInsert()");
		
		/*
		for(int i = 1; i <= 78; i++) {
			postVo.setPostTitle(i + "번째 글 제목");
			postVo.setPostContent(i + "번째 글 내용");
			
			blogDao.postInsert(postVo);
		}
		*/
		
		int count = blogDao.postInsert(postVo);
		if(count == 1) {
			System.out.println("등록 성공");
		} else {
			System.out.println("등록 실패");
		}
	}

}
