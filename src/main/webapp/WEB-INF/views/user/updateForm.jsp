<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ include file="../layout/header.jsp"%>

<div class="container">

	<form>
		<input type="hidden" id="id" value="${principal.user.id}" />
		<div class="form-group">
			<label for="username">UserId</label> <input type="text" value="${principal.user.userId}" class="form-control" placeholder="Enter UserId" id="userId" readonly>
		</div>

		<c:if test="${empty principal.user.oauth}">
		<div class="form-group">
				<label for="password">username</label> <input type="text" value="${principal.user.username}"class="form-control" placeholder="Enter username" id="username">
			</div>
			<div class="form-group">
				<label for="password">Password</label> <input type="password" class="form-control" placeholder="Enter password" id="password">
			</div>
			<div class="form-group">
				<label for="phone">phone</label> <input type="text" value="${principal.user.userTell}" class="form-control" placeholder="Enter phone" id="userTell">
			</div>

			<div class="form-group">
				<label for="email">Email</label> <input type="email" value="${principal.user.userEmail}" class="form-control" placeholder="Enter email" id="userEmail">
			</div>

		</c:if>





	</form>
	<button id="btn-update" class="btn btn-primary">UserDetail</button>


</div>
<script src="/js/user.js"></script>
<%@include file="../layout/footer.jsp"%>



