<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JBlog_blog-main</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>
</head>
<body>
	<div id="wrap">

		<!-- 개인블로그 해더 -->
		<c:import url="/WEB-INF/views/includes/blog-header.jsp"></c:import>
		
		<div id="content" class="clearfix">
			<div id="profilecate_area">
				<div id="profile">
					<c:if test="${empty blogVo.logoFile }">
						<!-- 기본이미지 -->
						<img id="proImg" src="${pageContext.request.contextPath }/assets/images/sarang_profile.jpg">
					</c:if>
					<c:if test="${!empty blogVo.logoFile }">
						<!-- 사용자업로드 이미지 -->
						<img id="proImg" src="${pageContext.request.contextPath }/upload/${blogVo.logoFile }">
					</c:if>
					
					<div id="nick">${blogVo.id.userName }(${blogVo.id.id })님</div>
				</div>
				<div id="cate">
					<div class="text-left">
						<strong>카테고리</strong>
					</div>
					<ul id="cateList" class="text-left">
						<c:forEach items="${categoryList }" var="categoryVo">
	      					<li><a href="${pageContext.request.contextPath }/${blogVo.id.id }?cateNo=${categoryVo.cateNo }">${categoryVo.cateName }</a></li>
	      				</c:forEach>
					</ul>
				</div>
			</div>
			<!-- profilecate_area -->
			
			<div id="post_area">
				<c:if test="${!empty postVo }">
					<!-- 글이 있는 경우 -->
					<div id="postBox" class="clearfix">
						<div id="postTitle" class="text-left"><strong>${postVo.postTitle }</strong></div>
						<div id="postDate" class="text-left"><strong>${postVo.regDate }</strong></div>
						<div id="postNick">${blogVo.id.userName }(${blogVo.id.id })님</div>
					</div>
					<!-- //postBox -->
				
					<div id="post">
						${postVo.postContent }
						
						<br><br><hr>
					
						<div id="comments" style="border: 1px solid #B7B7B7">
							<c:if test="${!empty authUser }">
								<!-- 로그인 후 -->
								<table id="commentAdd">
									<colgroup>
										<col style="width: 100px;">
										<col style="width: 200px;">
										<col>
										<col style="width: 100px;">
									</colgroup>
									<tbody>
										<tr>
											<td>${authUser.userName }</td>
											<td><input type="text" name="cmtContent" value="" style="width: 500px; height: 25px; margin-right: 5px;"></td>
											<td><button id="btnAddCmt" type="submit">등록</button></td>
										</tr>
									</tbody>
								</table>
								<!-- //commentAdd -->
				 			</c:if>
							
							<br>
							
							<table id="admin-cate-list">
								<colgroup>
									<col style="width: 100px;">
									<col style="width: 400px;">
									<col style="width: 100px;">
									<col>
									<col style="width: 100px;">
								</colgroup>
					      		<thead>
						      		<tr>
						      			<th>작성자</th>
						      			<th>댓글내용</th>
						      			<th>작성날짜</th>
						      			<th>삭제</th>      			
						      		</tr>
					      		</thead>
					      		<tbody id="commentList"></tbody>
							</table>
							<!-- //commentList -->
						</div>
						<!-- //comments -->
					</div>
					<!-- //post -->
				</c:if>
				<c:if test="${empty postVo }">
					<!-- 글이 없는 경우 -->
					<div id="postBox" class="clearfix">
						<div id="postTitle" class="text-left"><strong>등록된 글이 없습니다.</strong></div>
						<div id="postDate" class="text-left"><strong></strong></div>
						<div id="postNick"></div>
					</div>
				    
					<div id="post"></div>
				</c:if>
				
				<div id="list">
					<div id="listTitle" class="text-left"><strong>카테고리의 글</strong></div>
					<table>
						<colgroup>
							<col style="">
							<col style="width: 20%;">
						</colgroup>
						
						<c:forEach items="${postPageMap.postList }" var="vo">
	      					<tr>
	      						<c:choose>
									<c:when test="${vo.postNo == param.postNo }">
										<td class="text-left"><a href="${pageContext.request.contextPath }/${blogVo.id.id }?cateNo=${postVo.cateNo.cateNo }&postNo=${vo.postNo }&crtPage=${param.crtPage }" style="font-weight: bold;">${vo.postTitle }</a></td>
									</c:when>
									<c:otherwise>
										<td class="text-left"><a href="${pageContext.request.contextPath }/${blogVo.id.id }?cateNo=${postVo.cateNo.cateNo }&postNo=${vo.postNo }&crtPage=${param.crtPage }">${vo.postTitle }</a></td>
									</c:otherwise>
								</c:choose>
								<td class="text-right">${vo.regDate }</td>
							</tr>
	      				</c:forEach>
					</table>
					<div id="paging">
						<ul style="display: flex; justify-content: center;">
							<c:if test="${postPageMap.prev }">
								<li><a href="${pageContext.request.contextPath }/${blogVo.id.id }?cateNo=${postVo.cateNo.cateNo }&postNo=${postVo.postNo }&crtPage=${postPageMap.startPageBtnNo - 1}">◀</a></li>
							</c:if>
							<c:forEach begin="${postPageMap.startPageBtnNo }" end="${postPageMap.endPageBtnNo }" step="1" var="page">
								<c:choose>
									<c:when test="${param.crtPage == page }">
										<li style="font-weight: bold;"><a href="${pageContext.request.contextPath }/${blogVo.id.id }?cateNo=${postVo.cateNo.cateNo }&postNo=${postVo.postNo }&crtPage=${page}">${page }</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="${pageContext.request.contextPath }/${blogVo.id.id }?cateNo=${postVo.cateNo.cateNo }&postNo=${postVo.postNo }&crtPage=${page}">${page }</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:if test="${postPageMap.next }">
								<li><a href="${pageContext.request.contextPath }/${blogVo.id.id }?cateNo=${postVo.cateNo.cateNo }&postNo=${postVo.postNo }&crtPage=${postPageMap.endPageBtnNo + 1}">▶</a></li>
							</c:if>
						</ul>
						<div class="clear"></div>
					</div>
				</div>
				<!-- //list -->
			</div>
			<!-- //post_area -->
		</div>	
		<!-- //content -->
		<div class=></div>
		<c:import url="/WEB-INF/views/includes/blog-footer.jsp"></c:import>
		
	</div>
	<!-- //wrap -->
</body>
<script type="text/javascript">

	// DOM 완성
	$(document).ready(function() {
		console.log("ready()");
		
		if(${!empty postVo }) {
			fetchList();
		}
	})
	
	// 코멘트 추가
	$("#btnAddCmt").on("click", function() {
		console.log("btnAddCmt");
		
		let cmtContent = $('input[name=cmtContent]').val();
		
		if(cmtContent == "") {
			alert("댓글 내용을 입력해주세요.");
		} else {
			$.ajax({
				url : "${pageContext.request.contextPath}/${postVo.postNo}/blog/commentAdd/",
				type : "post",
				data: {cmtContent: cmtContent},
				
				dataType : "json",
				success : function(jsonResult) {
					if(jsonResult.result  == "success") {
						console.log("success");
						
						render(jsonResult.data, "up");
						
					} else if(jsonResult.result  == "fail") {
						console.log("fail");
						console.log("failMsg : " + jsonResult.data);
					}
					$('input[name=cmtContent]').val("");
				},
				error : function(XHR, status, error) {
					console.error(status + " : " + error);
				}
			});
		}
	})
	
	// 코멘트 삭제
	$("#commentList").on("click", ".btnCmtDel",function() {
		console.log("commentList btnCateDel");
		
		let $this = $(this);
		let cmtNo = $this.data("cmtno");
		console.log("cmtNo : " + cmtNo);
		
		$.ajax({
			url : "${pageContext.request.contextPath}/" + cmtNo + "/blog/commentDelete/",
			type : "get",
			
			dataType : "json",
			success : function(jsonResult) {
				if(jsonResult.result  == "success") {
					console.log("success");
					
					$("#t" + cmtNo).remove();
					
				} else if(jsonResult.result  == "fail") {
					console.log("fail");
					console.log("failMsg : " + jsonResult.data);
				}
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	})
	
	// 코멘트 리스트
	function fetchList() {
		console.log("fetchList()");
		
		$.ajax({
			url : "${pageContext.request.contextPath}/${postVo.postNo}/blog/commentList",
			type : "get",

			dataType : "json",
			success : function(jsonResult) {
				if(jsonResult.result  == "success") {
					console.log("success");
					
					for(let i = 0; i < jsonResult.data.length; i++) {
						render(jsonResult.data[i], "down");
					}
				}
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	}
	
	// 리스트 그리기
	function render(commentVo, dir) {
		console.log("render()");
		
		let authUser = ${authUser.userNo};

		let str = '';
		str += '<tr id="t' + commentVo.cmtNo + '" >';
		str += '	<td>' + commentVo.userNo.userName + '</td>';
		str += '	<td>' + commentVo.cmtContent + '</td>';
		str += '	<td>' + commentVo.regDate + '</td>';

		if(authUser == commentVo.userNo.userNo) {
			str += '	<td class="text-center">';
			str += '		<img class="btnCmtDel" data-cmtno="' + commentVo.cmtNo
							+ '" src="${pageContext.request.contextPath}/assets/images/delete.jpg"';
			str += '	<td>';
		}
		
		str += '</tr>';
		
		if(dir == "up") {
			$("#commentList").prepend(str);
		} else if(dir == "down") {
			$("#commentList").append(str);
		} else {
			console.log("잘못입력");
		}
	}
	
</script>
</html>