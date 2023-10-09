<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JBlog_index</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css" type="text/css">
</head>
<body>
	<div id="center-content">
		
		<!--메인 해더 자리 -->
		<c:import url="/WEB-INF/views/includes/main-header.jsp"></c:import>
		
		<form id="search-form">
			<fieldset>
				<input type="text" name="keyword" >
				<button id="btnSearch" type="submit" >검색</button>
			</fieldset>
			
			<fieldset>
				<label for="rdo-title">블로그 제목</label> 
				<input id="rdo-title" type="radio" name="kwdOpt" value="optTitle" > 
				
				<label for="rdo-userName">블로거 이름</label> 
				<input id="rdo-userName"" type="radio" name="kwdOpt" value="optName" > 
			</fieldset>
		</form>
		
		<br><br>
		
		<div id="resultList">
			<table>
				<colgroup>
					<col style="">
					<col style="width: 20%;">
					<col style="">
					<col style="width: 20%;">
				</colgroup>
				
				<c:forEach items="${blogPageMap.blogList }" var="blogVo">
					<tr>
						<c:if test="${empty blogVo.logoFile }">
							<!-- 기본이미지 -->
							<img id="proImg" src="${pageContext.request.contextPath }/assets/images/sarang_profile.jpg">
						</c:if>
						<c:if test="${!empty blogVo.logoFile }">
							<!-- 사용자업로드 이미지 -->
							<img id="proImg" src="${pageContext.request.contextPath }/upload/${blogVo.logoFile }">
						</c:if>
						<td class="text-left"><a href="${pageContext.request.contextPath }/${blogVo.id }">${blogVo.blogTitle }</a></td>
						<td class="text-right">${blogVo.id.userName }(${blogVo.id.id })</td>
						<td class="text-right">${blogVo.id.joinDate }</td>
					</tr>
  				</c:forEach>
			</table>
			<div id="paging">
				<ul style="display: flex; justify-content: center;">
					<c:if test="${blogPageMap.prev }">
						<li><a href="${pageContext.request.contextPath }?crtPage=${blogPageMap.startPageBtnNo - 1}&keyword=${blogPageMap.keyword}">◀</a></li>
					</c:if>
					<c:forEach begin="${blogPageMap.startPageBtnNo }" end="${blogPageMap.endPageBtnNo }" step="1" var="page">
						<c:choose>
							<c:when test="${param.crtPage == page }">
								<li style="font-weight: bold;"><a href="${pageContext.request.contextPath }?crtPage=${page}&keyword=${blogPageMap.keyword}">${page }</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="${pageContext.request.contextPath }/?crtPage=${page}&keyword=${blogPageMap.keyword}">${page }</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:if test="${blogPageMap.next }">
						<li><a href="${pageContext.request.contextPath }/?crtPage=${blogPageMap.endPageBtnNo + 1}&keyword=${blogPageMap.keyword}">▶</a></li>
					</c:if>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		
		<!-- 메인 푸터 자리-->
		<c:import url="/WEB-INF/views/includes/main-footer.jsp"></c:import>
		
	</div>
	<!-- //center-content -->
</body>
</html>