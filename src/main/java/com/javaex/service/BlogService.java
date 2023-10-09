package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.dao.BlogDao;
import com.javaex.vo.BlogVo;
import com.javaex.vo.CategoryVo;
import com.javaex.vo.PostVo;

@Service
public class BlogService {
	
	@Autowired
	private BlogDao blogDao;

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
	
	// 포스트 목록
	public List<PostVo> postList(int cateNo) {
		System.out.println("BlogService.postList()");
		
		List<PostVo> postList = blogDao.postList(cateNo);
		
		return postList;
	}

	// 포스트 상세
	public PostVo postDetail(int cateNo) {
		System.out.println("BlogService.postDetail()");
		
		PostVo postVo = blogDao.postDetail(cateNo);
		
		return postVo;
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
			
			// 카테고리 정보
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
		
		int count = blogDao.postInsert(postVo);
		if(count == 1) {
			System.out.println("등록 성공");
		} else {
			System.out.println("등록 실패");
		}
	}

}
