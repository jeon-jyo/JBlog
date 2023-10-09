package com.javaex.vo;

public class CommentVo {

	private int cmtNo;
	private PostVo postNo;
	private UserVo userNo;
	private String cmtContent;
	private String regDate;
	
	public CommentVo() {}
	
	public CommentVo(int cmtNo, PostVo postNo, UserVo userNo, String cmtContent, String regDate) {
		this.cmtNo = cmtNo;
		this.postNo = postNo;
		this.userNo = userNo;
		this.cmtContent = cmtContent;
		this.regDate = regDate;
	}
	
	public int getCmtNo() {
		return cmtNo;
	}
	public void setCmtNo(int cmtNo) {
		this.cmtNo = cmtNo;
	}
	public PostVo getPostNo() {
		return postNo;
	}
	public void setPostNo(PostVo postNo) {
		this.postNo = postNo;
	}
	public UserVo getUserNo() {
		return userNo;
	}
	public void setUserNo(UserVo userNo) {
		this.userNo = userNo;
	}
	public String getCmtContent() {
		return cmtContent;
	}
	public void setCmtContent(String cmtContent) {
		this.cmtContent = cmtContent;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
	@Override
	public String toString() {
		return "CommentVo [cmtNo=" + cmtNo + ", postNo=" + postNo + ", userNo=" + userNo + ", cmtContent=" + cmtContent
				+ ", regDate=" + regDate + "]";
	}
	
}
