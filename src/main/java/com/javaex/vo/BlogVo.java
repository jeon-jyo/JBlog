package com.javaex.vo;

public class BlogVo {

	private UserVo id;
	private String blogTitle;
	private String logoFile;
	
	public BlogVo() {}
	
	public BlogVo(UserVo id, String blogTitle, String logoFile) {
		this.id = id;
		this.blogTitle = blogTitle;
		this.logoFile = logoFile;
	}
	
	public UserVo getId() {
		return id;
	}
	public void setId(UserVo id) {
		this.id = id;
	}
	public String getBlogTitle() {
		return blogTitle;
	}
	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}
	public String getLogoFile() {
		return logoFile;
	}
	public void setLogoFile(String logoFile) {
		this.logoFile = logoFile;
	}

	@Override
	public String toString() {
		return "BlogVo [id=" + id + ", blogTitle=" + blogTitle + ", logoFile=" + logoFile + "]";
	}
	
}
