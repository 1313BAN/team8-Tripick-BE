<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>회원정보 수정</title>

  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/index.css" />
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/footer.css" />
</head>
<body>

  <%-- Header Include --%>
  <jsp:include page="/WEB-INF/views/components/header.jsp" />

  <div class="container mt-5">
    <h2>✏️ 회원정보 수정</h2>
    <form action="${pageContext.request.contextPath}/member/modify" method="post">

      <input type="hidden" name="id" value="${member.id}" />
      
      <div class="mb-3">
        <label class="form-label">이름</label>
        <input type="text" class="form-control" name="username" value="${member.username}" required />
      </div>

      <div class="mb-3">
        <label class="form-label">이메일</label>
        <input type="email" class="form-control" name="email" value="${member.email}" readonly />
      </div>

      <div class="mb-3">
        <label class="form-label">비밀번호</label>
        <input type="password" class="form-control" name="password" placeholder="새 비밀번호 입력" required />
      </div>

      <div class="mb-3">
        <label class="form-label">전화번호</label>
        <input type="text" class="form-control" name="phone" value="${member.phone}" required />
      </div>

      <button type="submit" class="btn btn-primary">수정 완료</button>
      <a href="${pageContext.request.contextPath}/member/detail?email=${member.email}" class="btn btn-secondary">취소</a>
    </form>
  </div>

  <%-- Footer Include --%>
  <jsp:include page="/WEB-INF/views/components/footer.jsp" />

</body>
</html>
