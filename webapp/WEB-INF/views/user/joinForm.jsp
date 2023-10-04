<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JBlog_joinForm</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.12.4.js"></script>
</head>
<body>
	<div id="center-content">
		
		<!-- 메인 해더 -->
		<c:import url="/WEB-INF/views/includes/main-header.jsp"></c:import>

		<div>		
			<form id="joinForm" method="post" action="${pageContext.request.contextPath}/user/join">
				<table>
			      	<colgroup>
						<col style="width: 100px;">
						<col style="width: 170px;">
						<col style="">
					</colgroup>
		      		<tr>
		      			<td><label for="txtId">아이디</label></td>
		      			<td><input id="txtId" type="text" name="id"></td>
		      			<td><button id="btnIdCheck" type="button">아이디체크</button></td>
		      		</tr>
		      		<tr>
		      			<td><input type="hidden" id="hiddenId" value="false"></td>
		      			<td id="tdMsg" colspan="2">아이디 중복체크를 해주세요.</td>
		      		</tr> 
		      		<tr>
		      			<td><label for="txtPassword">패스워드</label></td>
		      			<td><input id="txtPassword" type="password" name="password" value=""></td>   
		      			<td></td>  			
		      		</tr> 
		      		<tr>
		      			<td><label for="txtUserName">이름</label></td>
		      			<td><input id="txtUserName" type="text" name="userName" value=""></td>   
		      			<td></td>  			
		      		</tr>  
		      		<tr>
		      			<td><span>약관동의</span></td>
		      			<td colspan="3">
		      				<input id="chkAgree" type="checkbox" name="agree" value="y">
		      				<label for="chkAgree">서비스 약관에 동의합니다.</label>
		      			</td>   
		      		</tr>   		
		      	</table>
	      		<div id="btnArea">
					<button id="btnJoin" class="btn" type="submit">회원가입</button>
				</div>
			</form>
		</div>
		
		<!-- 메인 푸터  자리-->
		<c:import url="/WEB-INF/views/includes/main-footer.jsp"></c:import>
		
	</div>
</body>
<script type="text/javascript">

	// 회원가입
	$("#joinForm").on("submit", function() {
		console.log("joinForm submit");
		
		let id = $("#txtId").val();
		let hiddenId = $("#hiddenId").val();
		let password = $("#txtPassword").val();
		let userName = $("#txtUserName").val();
		let chkAgree = $("#chkAgree").is(':checked');
		
		if (id == "") {
			alert("아이디를 입력해주세요.");
			return false;
		} else if(hiddenId == "false") {
			alert("아이디 중복체크를 해주세요.");
			return false;
		} else if(password == "") {
			alert("패스워드를 입력해주세요.");
			return false;
		} else if(userName == "") {
			alert("이름을 입력해주세요.");
			return false;
		} else if(!chkAgree) {
			alert("약관에 동의해주세요.");
			return false;
		} else {
			console.log("등록");
			return true;
		}
	})
	
	// 중복체크
	$("#btnIdCheck").on("click", function() {
		console.log("btnIdCheck");
		
		let id = $("#txtId").val();
		
		if(id != "") {
			$.ajax({
				url : "${pageContext.request.contextPath}/user/checkId/",
				type : "post",
				/* contentType : "application/json", */
				data: {id: id},
				
				dataType : "json",
				success : function(jsonResult) {
					if(jsonResult.result  == "success") {
						if(jsonResult.data == true) {
							$("#tdMsg").text("사용할 수 있는 아이디 입니다.");
							$("#hiddenId").val("true");
						} else if(jsonResult.data == false) {
							$("#tdMsg").text("다른 아이디로 가입해 주세요.");
							$("#hiddenId").val("false");
						}
					}
				},
				error : function(XHR, status, error) {
					console.error(status + " : " + error);
				}
			});
		} else {
			alert("아이디를 입력해주세요.");
		}
	})
	
</script>
</html>