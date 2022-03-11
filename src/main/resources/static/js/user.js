let index  = {
		init: function(){
			$("#btn-save").on("click", ()=>{
				this.save();
			});
			$("#btn-update").on("click", ()=>{
				this.update();
			});
			$("#btn-delete").on("click", ()=>{
			this.delete();
			});
			
		},

save: function(){
		
		let data ={
			
			
			username:$("#username").val(),
			userId:$("#userId").val(),
			password:$("#password").val(),
			email:$("#email").val(),
			phone:$("#phone").val()
		};

		console.log(data);
		
		
		$.ajax({
			type:"POST",
			url:"/auth/joinProc",
			data: JSON.stringify(data),
			contentType : 'application/json; charset=utf-8',
			dataType:"json"
		}).done(function(resp){
			alert("회원가입이 완료되었습니다.");
			location.href="/";
			
		}).fail(function(error){
			alert(JSON.stringify(error));	
		});	
		
		},
update: function(){
		
		let data ={
			id:$("#id").val(),
			username:$("#username").val(),
			userId:$("#userId").val(),
			password:$("#password").val(),
			email:$("#email").val(),
			phone:$("#phone").val()
		};

	
		

		$.ajax({
			type:"PUT",
			url:"/user",
			data: JSON.stringify(data),
			contentType : 'application/json; charset=utf-8',
			dataType:"json"
		}).done(function(resp){
			alert("회원수정이 완료되었습니다.");
			location.href="/";
			
		}).fail(function(error){
			alert(JSON.stringify(error));	
		});	
		
		},
delete: function(){
			let id=$("#id").text();
	
		$.ajax({
			type:"DELETE",
			url:"/api/user/"+id,		
			dataType:"json"
		}).done(function(resp){
			alert("삭제가 완료되었습니다.");
			location.href="/";
			
		}).fail(function(error){
			alert(JSON.stringify(error));	
		});	
		
		},
		
		
		
		
}

index.init();
