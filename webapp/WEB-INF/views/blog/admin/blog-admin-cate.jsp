<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JBlog_blog-admin-cate</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.12.4.js"></script>
</head>
<body>
	<div id="wrap">
		
		<!-- 개인블로그 해더 -->
		<c:import url="/WEB-INF/views/includes/blog-header.jsp"></c:import>

		<div id="content">
			<c:import url="/WEB-INF/views/includes/blog-admin-menu.jsp"></c:import>
			
			<div id="admin-content">
				<table id="admin-cate-list">
					<colgroup>
						<col style="width: 50px;">
						<col style="width: 200px;">
						<col style="width: 100px;">
						<col>
						<col style="width: 50px;">
					</colgroup>
		      		<thead>
			      		<tr>
			      			<th>번호</th>
			      			<th>카테고리명</th>
			      			<th>포스트 수</th>
			      			<th>설명</th>
			      			<th>삭제</th>      			
			      		</tr>
		      		</thead>
		      		<tbody id="cateList">
		      			<!-- 리스트 영역 -->
					</tbody>
				</table>
      	
		      	<table id="admin-cate-add" >
		      		<colgroup>
						<col style="width: 100px;">
						<col style="">
					</colgroup>
		      		<tr>
		      			<td class="t">카테고리명</td>
		      			<td><input type="text" name="name" value=""></td>
		      		</tr>
		      		<tr>
		      			<td class="t">설명</td>
		      			<td><input type="text" name="desc"></td>
		      		</tr>
		      	</table> 
			
				<div id="btnArea">
					<input type="hidden" name="hiddenCnt" value="0">
		      		<button id="btnAddCate" class="btn_l" type="submit">카테고리추가</button>
		      	</div>
			</div>
			<!-- //admin-content -->
		</div>	
		<!-- //content -->
		
		<!-- 개인블로그 푸터 -->
		<c:import url="/WEB-INF/views/includes/blog-footer.jsp"></c:import>
	
	</div>
	<!-- //wrap -->
</body>
<script type="text/javascript">

	// DOM 완성
	$(document).ready(function() {
		console.log("ready()");
		fetchList();
	})
	
	// 카테고리 추가
	$("#btnAddCate").on("click", function() {
		console.log("ubtnAddCate");
		
		let name = $('input[name=name]').val();
		let desc = $('input[name=desc]').val();
		
		let categoryVo = {
			cateName: name,
			description: desc
		}
		
		if(name == "") {
			alert("카테고리명을 입력해주세요.");
		} else {
			$.ajax({
				url : "${pageContext.request.contextPath}/admin/categoryAdd/",
				type : "post",
				data: categoryVo,
				
				dataType : "json",
				success : function(jsonResult) {
					if(jsonResult.result  == "success") {
						console.log("success");
						
						render(jsonResult.data, "up");
						
					} else if(jsonResult.result  == "fail") {
						console.log("fail");
						console.log("failMsg : " + jsonResult.data);
					}
					$('input[name=name]').val("");
					$('input[name=desc]').val("");
				},
				error : function(XHR, status, error) {
					console.error(status + " : " + error);
				}
			});
		}
	})
	
	// 카테고리 삭제
	$("#cateList").on("click", ".btnCateDel",function() {
		console.log("cateList btnCateDel");
		
		let $this = $(this);
		let cateNo = $this.data("cateno");
		let postNo = $this.data("postno");
		
		if(postNo != 0) {
			alert("삭제할 수 없습니다.");
		} else {
			$.ajax({
				url : "${pageContext.request.contextPath}/admin/categoryDelete/",
				type : "get",
				data: {cateNo: cateNo},
				
				dataType : "json",
				success : function(jsonResult) {
					if(jsonResult.result  == "success") {
						console.log("success");
						
						$("#t" + cateNo).remove();
						
					} else if(jsonResult.result  == "fail") {
						console.log("fail");
						console.log("failMsg : " + jsonResult.data);
					}
				},
				error : function(XHR, status, error) {
					console.error(status + " : " + error);
				}
			});
		}
	})
	
	// 카테고리 리스트
	function fetchList() {
		console.log("fetchList()");
		
		$.ajax({
			url : "${pageContext.request.contextPath}/${authUser.id}/admin/categoryList",
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
	function render(cateMap, dir) {
		console.log("render()");
		
		let str = '';
		str += '<tr id="t' + cateMap.categoryVo.cateNo + '">';
		str += '	<td>' + cateMap.rn + '</td>';
		str += '	<td>' + cateMap.categoryVo.cateName + '</td>';
		str += '	<td>' + cateMap.postCnt + '</td>';
		str += '	<td>' + cateMap.categoryVo.description + '</td>';
		str += '	<td class="text-center">';
		str += '		<img class="btnCateDel" data-cateno="' + cateMap.categoryVo.cateNo
						+ '" data-postno="' + cateMap.postCnt
						+ '" src="${pageContext.request.contextPath}/assets/images/delete.jpg"';
		str += '	<td>';
		str += '</tr>';
		
		if(dir == "up") {
			$("#cateList").prepend(str);
		} else if(dir == "down") {
			$("#cateList").append(str);
		} else {
			console.log("잘못입력");
		}
	}
	
</script>
</html>