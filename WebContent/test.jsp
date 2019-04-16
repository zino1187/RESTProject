<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
$(function(){
	$("button").click(function(){
		send();
	});
});

function send(){
	var json={
		id:"zino"
	};
	
	var str=JSON.stringify(json);
	
	alert(str);
	
	$.ajax({
		url:"/rest/members",
		type:"post",
		data:str,
		success:function(result){
			//alert(result);			
		}
	});
}
</script>
</head>
<body>
<form>
	<input type="text" name="id" value="zino"/>
	<button type="button">전송</button>
</form>
</body>
</html>