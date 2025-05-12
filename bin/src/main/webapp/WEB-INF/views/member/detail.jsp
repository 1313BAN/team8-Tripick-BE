<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>회원 정보 상세보기</title>

  <!-- CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/index.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/footer.css" />
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>

  <!-- JS -->
  <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>

  <%-- Header Include --%>
  <jsp:include page="/WEB-INF/views/components/header.jsp" />

  <div class="container mt-5">
    <h2 class="mb-4">👤 회원 정보 상세</h2>
    <table class="table table-bordered">
      <tr>
        <th>회원번호 (ID)</th>
        <td>${member.id}</td>
      </tr>
      <tr>
        <th>이름</th>
        <td>${member.username}</td>
      </tr>
      <tr>
        <th>이메일</th>
        <td>${member.email}</td>
      </tr>
      <tr>
        <th>전화번호</th>
        <td>${member.phone}</td>
      </tr>
      <tr>
        <th>가입일</th>
        <td>${member.createdAt}</td>
      </tr>
    </table>

    <div class="mt-4">
      <a href="${pageContext.request.contextPath}/" class="btn btn-primary">홈으로</a>
      <a href="${pageContext.request.contextPath}/member/modifyForm?email=${member.email}" class="btn btn-warning">회원정보 수정</a>
      <form action="${pageContext.request.contextPath}/member/delete" method="post" style="display:inline;">
        <input type="hidden" name="email" value="${member.email}" />
        <button type="submit" class="btn btn-danger" onclick="return confirm('정말 삭제하시겠습니까?');">회원 탈퇴</button>
      </form>
    </div>
  </div>

  <%-- Footer Include --%>
  <jsp:include page="/WEB-INF/views/components/footer.jsp" />

</body>
</html>
