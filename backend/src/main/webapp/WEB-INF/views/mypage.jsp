<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>마이페이지</title>

  <!-- CSS -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/reset-css@4.0.1/reset.min.css" />
  <link rel="stylesheet" href="./assets/css/index.css" />
  <link rel="stylesheet" href="./assets/css/header.css" />
  <link rel="stylesheet" href="./assets/css/footer.css" />
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />

  <!-- jQuery -->
  <script src="https://code.jquery.com/jquery-3.7.1.min.js"
          integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
</head>
<body>

<!-- 공통 헤더 include -->
<jsp:include page="components/header.jsp" />

<!-- 메인 콘텐츠 -->
<main class="container my-5">
  <h2 class="mb-4">내 정보 관리</h2>

  <!-- ✅ 회원 정보 수정 폼 -->
  <form action="${pageContext.request.contextPath}/main?action=update" method="post">
    <div class="mb-3">
      <label for="nameInput" class="form-label">이름</label>
      <input type="text" class="form-control" id="nameInput" name="username"
             value="${sessionScope.loginUser.username}" required />
    </div>
    <div class="mb-3">
      <label for="emailInput" class="form-label">이메일 (수정 불가)</label>
      <input type="email" class="form-control" id="emailInput" name="email"
             value="${sessionScope.loginUser.email}" readonly />
    </div>
    <div class="mb-3">
      <label for="passwordInput" class="form-label">비밀번호 변경</label>
      <input type="password" class="form-control" id="passwordInput" name="password" placeholder="새 비밀번호" />
    </div>
    <div class="mb-3">
      <label for="phoneInput" class="form-label">전화번호</label>
      <input type="text" class="form-control" id="phoneInput" name="phone"
             value="${sessionScope.loginUser.phone}" />
    </div>
    <button type="submit" class="btn btn-primary">수정</button>
  </form>

  <!-- ✅ 회원 탈퇴 폼 -->
  <form action="${pageContext.request.contextPath}/main?action=delete" method="post" class="mt-3">
    <input type="hidden" name="email" value="${sessionScope.loginUser.email}" />
    <button type="submit" class="btn btn-danger" onclick="return confirm('정말 탈퇴하시겠습니까?')">회원 탈퇴</button>
  </form>
</main>

<!-- 공통 푸터 include -->
<jsp:include page="components/footer.jsp" />

<!-- JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
