<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.wrapper{
	width:100%;
	height:500px;
}
.regist-area{
	width:20%;
	height:500px;
	background:yellow;
}
.list-area{
	width:60%;
	height:500px;
	overflow:scroll;
}
.detail-area{
	width:20%;
	height:500px;
	background:blue;
}
.regist-area, .list-area , .detail-area{
	float:left;
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
$(function(){
	getList();
	
	$("form[name='regist-form']").find("button").click(function(){
		regist();
	});
		
	$($("button")[1]).click(function(){
		edit();
	});
	
	$($("button")[2]).click(function(){
		del();
	});
	
});

//비동기 요청!!!
function regist(){
	
	$.ajax({
		url:"/admin/member/regist",
		type:"post",
		data:{
			id:$($("form[name='regist-form']").find("input[name='id']")).val(),
			pass:$($("form[name='regist-form']").find("input[name='pass']")).val(),
			name:$($("form[name='regist-form']").find("input[name='name']")).val()
		},
		success:function(result){
			//목록 갱신!!
			var obj=JSON.parse(result);
			alert("등록 후 결과 "+obj.result);
			
			if(obj.result==1){
				getList();
			}
		}, 
		error:function(result){
			alert("regist error: "+result);
		}
	});
}

//비동기 목록 요청!!
function getList(){

	$.ajax({
		url:"/admin/member/list",
		type:"get",
		contentType:"application/x-www-form-urlencoded;charset=utf-8",
		success:function(result){
			var jsonArray=JSON.parse(decodeURIComponent(result));
			renderList(jsonArray);						
		},
		error:function(result){
			alert("error : "+result);
		}
	});
}

function getDetail(obj){
	/* db 연동 안하고..
	$($("form[name='detail-form']").find("input[name='id']")).val(obj.id);	
	$($("form[name='detail-form']").find("input[name='pass']")).val(obj.pass);	
	$($("form[name='detail-form']").find("input[name='name']")).val(obj.name);
	*/
	$.ajax({
		url:"/admin/member/detail",
		type:"get",
		data:{
			member_id:obj.member_id
		},
		success:function(result){
			var json=JSON.parse(result);
			$($("form[name='detail-form']").find("input[name='id']")).val(json.id);	
			$($("form[name='detail-form']").find("input[name='pass']")).val(json.pass);	
			$($("form[name='detail-form']").find("input[name='name']")).val(json.name);
			$($("form[name='detail-form']").find("input[name='member_id']")).val(json.member_id);
		},
		error:function(result){
			
		}		
	});		
}

//목록 출력
function renderList(array){
	
	$(".list-area").html("");
	
	$(".list-area").append("<table width='100%' border='1px'>");

	$(".list-area").append("<tr>");
	$(".list-area").append("<td width='10%'>SEQ</td>");
	$(".list-area").append("<td width='30%'>아이디</td>");
	$(".list-area").append("<td width='30%'>비번</td>");
	$(".list-area").append("<td width='30%'>이름</td>");
	$(".list-area").append("</tr>");
	for(var i=0;i<array.length;i++){
		var obj=array[i];
		$(".list-area").append("<tr>");
		$(".list-area").append("<td width='10%'>"+obj.member_id+"</td>");
		$(".list-area").append("<td width='30%'>"+obj.id+"</td>");
		$(".list-area").append("<td width='30%'>"+obj.pass+"</td>");
		$(".list-area").append("<td width='30%'><a href='javascript:getDetail("+JSON.stringify(obj)+")'>"+obj.name+"</a></td>");
		$(".list-area").append("</tr>");
	}
	$(".list-area").append("</table>");
}

function edit(){
	if(!confirm("수정하시겠습니까?")){
		return;
	}
	$.ajax({
		url:"/admin/member/edit",
		type:"post",
		data:{
			id:$($("form[name='detail-form']").find("input[name='id']")).val(),
			pass:$($("form[name='detail-form']").find("input[name='pass']")).val(),
			name:$($("form[name='detail-form']").find("input[name='name']")).val(),
			member_id:$($("form[name='detail-form']").find("input[name='member_id']")).val()
		},
		success:function(result){
			var json=JSON.parse(result);
			if(json.result==1){
				alert("수정완료");
				getList();
			}else{
				alert("수정실패");
			}			
		},
		error:function(){
			
		}
	});
}
function del(){
	
	if(!confirm("삭제하시겠습니까?")){
		return;
	}

	alert($($("form[name='detail-form']").find("input[name='member_id']")).val());
	
	$.ajax({
		url:"/admin/member/del",
		type:"post",
		data:{
			member_id:$($("form[name='detail-form']").find("input[name='member_id']")).val()
		},
		success:function(result){
			var json=JSON.parse(result);
			if(json.result==1){
				alert("삭제완료");
				getList();

				$("form[name='detail-form']").trigger("reset");
				
			}else{
				alert("삭제실패");
			}			
		},
		error:function(){
			
		}
	});
}

</script>
</head>
<body>
	<div class="wrapper">
		<div class="regist-area">
			<form name="regist-form">
				<input type="text" name="id" placeholder="아이디입력"/>
				<input type="text" name="pass" placeholder="비번입력"/>
				<input type="text" name="name" placeholder="이름입력"/>
				</p>
				<button type="button">등록</button>
			</form>
		</div>
		<div class="list-area"></div>
		<div class="detail-area">
			<form name="detail-form">
				<input type="hidden" name="member_id"/>
				<input type="text" name="id" placeholder="아이디입력"/>
				<input type="text" name="pass" placeholder="비번입력"/>
				<input type="text" name="name" placeholder="이름입력"/>
				</p>
				<button type="button">수정</button>
				<button type="button">삭제</button>
			</form>
		</div>
	</div>
</body>
</html>







