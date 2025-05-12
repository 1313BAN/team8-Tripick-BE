<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>회원가입 완료</title>
</head>
<body>
    <h2>${message}</h2>
    <p>이름: ${username}</p>
    <p>이메일: ${email}</p>
    <p>전화번호: ${phone}</p>
    <form action="${pageContext.request.contextPath}/main" method="get">
        <input type="hidden" name="action" value="index" />
        <button type="submit">홈으로 돌아가기</button>
    </form>
</body>
</html>
