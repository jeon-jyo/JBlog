<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JBlog_index</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.12.4.js"></script>
</head>
<body>
	<div id="center-content">
		
		<!--메인 해더 자리 -->
		<c:import url="/WEB-INF/views/includes/main-header.jsp"></c:import>
		
		<form id="search-form" action="${pageContext.request.contextPath}/blog/search" method="get">
			<fieldset>
				<input type="text" name="keyword">
				<button id="btnSearch" type="submit">검색</button>
			</fieldset>
			
			<fieldset>
				<label for="rdo-title">블로그 제목</label> 
				<input id="rdo-title" type="radio" name="kwdOpt" value="optTitle"> 
				
				<label for="rdo-userName">블로거 이름</label> 
				<input id="rdo-userName" type="radio" name="kwdOpt" value="optName"> 
			</fieldset>
		</form>
		
		<br><br>
		
		<c:if test="${!empty blogPageMap }">
			<table id="admin-cate-list" style="margin: 0 auto;">
				<colgroup>
					<col style="width: 100px;">
					<col style="width: 400px;">
					<col style="width: 100px;">
					<col>
					<col style="width: 100px;">
				</colgroup>
	      		<thead>
		      		<tr>
		      			<th>로고</th>
		      			<th>블로그 제목</th>
		      			<th>블로거 이름</th>
		      			<th>가입일</th>      			
		      		</tr>
	      		</thead>
	      		<tbody>
					<c:forEach items="${blogPageMap.blogList }" var="blogVo">
						<tr>
							<c:if test="${empty blogVo.logoFile }">
								<!-- 기본이미지 -->
								<td><img id="proImg" src="${pageContext.request.contextPath }/assets/images/sarang_profile.jpg" style="width: 100px; height:100px"></td>
							</c:if>
							<c:if test="${!empty blogVo.logoFile }">
								<!-- 사용자업로드 이미지 -->
								<td><img id="proImg" src="${pageContext.request.contextPath }/upload/${blogVo.logoFile }" style="width: 100px; height:100px"></td>
							</c:if>
							<td style="text-align:center"><a href="${pageContext.request.contextPath }/${blogVo.id.id }">${blogVo.blogTitle }</a></td>
							<td>${blogVo.id.userName }(${blogVo.id.id })</td>
							<td>${blogVo.id.joinDate }</td>
						</tr>
					</c:forEach>
	      		</tbody>
			</table>
			<!-- //blogList -->
			
			<div id="paging">
				<ul style="display: flex; justify-content: center;">
					<c:if test="${blogPageMap.prev }">
						<li><a href="${pageContext.request.contextPath }/blog/search?crtPage=${blogPageMap.startPageBtnNo - 1}&keyword=${blogPageMap.keyword}">◀</a></li>
					</c:if>
					<c:forEach begin="${blogPageMap.startPageBtnNo }" end="${blogPageMap.endPageBtnNo }" step="1" var="page">
						<c:choose>
							<c:when test="${param.crtPage == page }">
								<li style="font-weight: bold;"><a href="${pageContext.request.contextPath }/blog/search?crtPage=${page}&keyword=${blogPageMap.keyword}">${page }</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="${pageContext.request.contextPath }/blog/search?crtPage=${page}&keyword=${blogPageMap.keyword}">${page }</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:if test="${blogPageMap.next }">
						<li><a href="${pageContext.request.contextPath }/blog/search?crtPage=${blogPageMap.endPageBtnNo + 1}&keyword=${blogPageMap.keyword}">▶</a></li>
					</c:if>
				</ul>
				<div class="clear"></div>
			</div>
		</c:if>
		
		<!-- 메인 푸터 자리-->
		<c:import url="/WEB-INF/views/includes/main-footer.jsp"></c:import>
		
	</div>
	<!-- //center-content -->
</body>
<script type="text/javascript">

	// 검색 키워드 확인
	$("#search-form").on("submit", function() {
		console.log("search-form submit");
		
		let keyword = $('input[name=keyword]').val();
		let rdoTitle = $("#rdo-title").is(':checked');
		let rdoUserName = $("#rdo-userName").is(':checked');
		
		if (keyword == "") {
			alert("검색어를 입력해주세요.");
			return false;
		} else if(!rdoTitle && !rdoUserName) {
			alert("검색할 분류를 선택해주세요.");
			return false;
		} else {
			console.log("검색");
			return true;
		}
	})
	
</script>
</html>