package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.dao.BlogDao;
import com.javaex.vo.BlogVo;

@Service
public class BlogService {
	
	@Autowired
	private BlogDao blogDao;

	// 블로그 메인
	public BlogVo blogDetail(String id) {
		System.out.println("BlogService.blogDetail()");
		
		BlogVo blogVo = blogDao.blogDetail(id);
		
		return blogVo;
	}
	
	// ----- 블로그 관리 ------------------------------
	
	// 블로그 관리 - 기본 수정
	public void basicUpdate(BlogVo blogVo, MultipartFile file) {
		System.out.println("BlogService.blogDetail()");
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
			int count = blogDao.basicUpdate(blogVo);
			if(count == 1) {
				System.out.println("수정 성공");
			} else {
				System.out.println("수정 실패");
			}
		}
	}
	
	
}
