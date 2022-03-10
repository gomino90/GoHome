<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ include file="../layout/header.jsp"%>

<div class="container">

	<h1>회원 탈퇴 페이지</h1>
	<hr>
	<br>
	<br> <b><font size="6" color="gray">내 정보</font></b> <br>
	<br>
	<br>

	<form name="deleteform" method="post" onsubmit="return confirm" onsubmit="return checkValue()">

		<table>
			<tr>
				<td bgcolor="skyblue">비밀번호</td>
				<td><input type="password" name="password" maxlength="50"></td>
			</tr>
		</table>

		<br> <input type="button" value="취소" onclick="javascript:window.location='/'">
			<button id="btn-delete"class="btn btn-primary">delete</button>
			
	</form>


</div>
<script src="/js/user.js"></script>
<%@include file="../layout/footer.jsp"%>

