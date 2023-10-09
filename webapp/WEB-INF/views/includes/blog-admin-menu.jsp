<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.12.4.js"></script>

			<ul id="admin-menu" class="clearfix">
				<li class="tabbtn"><a href="${pageContext.request.contextPath }/${authUser.id}/admin/basic">기본설정</a></li>
				<li class="tabbtn"><a href="${pageContext.request.contextPath }/${authUser.id}/admin/category">카테고리</a></li>
				<li class="tabbtn"><a href="${pageContext.request.contextPath }/${authUser.id}/admin/writeForm">글작성</a></li>
			</ul>
			<!-- //admin-menu -->
			<!-- <c:import url="/WEB-INF/views/includes/blog-admin-menu.jsp"></c:import> -->
			
<script type="text/javascript" defer>

	window.onload = function() {
		
	    const adminMenu = document.querySelectorAll("#admin-menu li");
	
	    let menuIndex = 0;
	
	    adminMenu[menuIndex].classList.add("selected");
	
	    adminMenu.forEach(function (item, index) {
	
	        item.addEventListener("click", function () {
	
	        	adminMenu[menuIndex].classList.remove("selected");
	        	menuIndex = index;
	        	adminMenu[menuIndex].classList.add("selected");
	
	        })
	
	    })
	}
	
</script>