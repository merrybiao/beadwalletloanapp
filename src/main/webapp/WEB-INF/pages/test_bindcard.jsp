<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%String basePath=request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>

</head>
<body>
<form action="<%=basePath%>/third/interface/bindCard.do" method="post">
    <input name="appId" type="text" value="ZDCWKL07USIO9OE"/><br/>
	<br/>
	<input name="request" type="text" value="qJUlTI6kvKw/3nxPSHUlN9GcKufNpVQDPhqzEpHsxYIzkZE+7SKGtt2xD4/Rr/o9ne8dI6CYKawThoO5JA0i4QrY46V4hQp9kknr0EFMNFaaYG2CHC8k5o6pb7WcxAPgdRt0SWA1oABDCkBn2rPdVkttOZZAH+CveUFmQnv4Gc6y6jCd/h4/g+5yuKGJAihNGXL5gsvJnRbtLspEUlahwkgZwdLS7tx9gg3k5jFQYxqY1HC5uMXaYGGf1RkcX6DoBMLvQTEp8i/Ft86BF0UPVZ8FdizpigOD0xOPUgRiwLk="/>
	<input type="submit" value="提交"/>
</form>

</body>
</html>