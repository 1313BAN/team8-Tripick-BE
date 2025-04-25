<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Custom CSS -->

<div class="navbar navbar-light bg-light">
  <div class="container-fluid header-container">
    <!-- 왼쪽 로고 -->
    <a href="index.jsp" class="header-brand d-flex align-items-center">
      <img
        src="./assets/img/header_4.png"
        alt="Album Logo"
        class="header-logo"
      />
    </a>

    <!-- 오른쪽 네비게이션 바 -->
    <div class="header-category">
      <a href="${pageContext.request.contextPath}/attraction/search">지역별여행지</a>
      <a href="#">나의여행계획</a>
      <a href="#">핫플자랑하기</a>
      <a href="${pageContext.request.contextPath}/board?action=list">여행정보공유</a>

      <!-- ✅ 로그인 전 -->
      <c:if test="${empty sessionScope.loginUser}">
        <a type="button" data-bs-toggle="modal" data-bs-target="#exampleModal"
          >회원가입</a
        >
        <a type="button" data-bs-toggle="modal" data-bs-target="#signinModal"
          >로그인</a
        >
      </c:if>

      <!-- ✅ 로그인 후 -->
      <c:if test="${not empty sessionScope.loginUser}">
        <span id="userNameDisplay">${sessionScope.loginUser.username}님</span>
        <a href="${pageContext.request.contextPath}/main?action=mypage">마이페이지</a>
        <a href="<%=request.getContextPath() %>/main?action=logout">로그아웃</a>
      </c:if>

      <!-- Modal -->
      <div
        class="modal fade"
        id="exampleModal"
        tabindex="-1"
        aria-labelledby="exampleModalLabel"
        aria-hidden="true"
      >
        <div class="modal-dialog">
          <div class="modal-content">
            <form
              action="<%=request.getContextPath()%>/main?action=join"
              method="post"
            >
              <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel">
                  <b>회원가입</b>
                </h1>
                <button
                  type="button"
                  class="btn-close"
                  data-bs-dismiss="modal"
                  aria-label="Close"
                ></button>
              </div>

              <div class="modal-body">
                <div class="mb-3">
                  <label for="username" class="form-label">이름 :</label>
                  <input
                    type="text"
                    class="form-control"
                    id="username"
                    name="username"
                    placeholder="이름"
                    required
                  />
                </div>

                <div class="mb-3">
                  <label for="email" class="form-label">이메일 :</label>
                  <input
                    type="email"
                    class="form-control"
                    id="email"
                    name="email"
                    placeholder="example@domain.com"
                    required
                  />
                </div>

                <div class="mb-3">
                  <label for="password" class="form-label">비밀번호 :</label>
                  <input
                    type="password"
                    class="form-control"
                    id="password"
                    name="password"
                    placeholder="비밀번호"
                    required
                  />
                </div>

                <div class="mb-3">
                  <label for="passwordCheck" class="form-label"
                    >비밀번호 확인 :</label
                  >
                  <input
                    type="password"
                    class="form-control"
                    id="passwordCheck"
                    placeholder="비밀번호 확인"
                    required
                  />
                </div>

                <div class="mb-3">
                  <label for="phone" class="form-label">전화번호 :</label>
                  <input
                    type="text"
                    class="form-control"
                    id="phone"
                    name="phone"
                    placeholder="전화번호"
                    required
                  />
                </div>
              </div>

              <div class="modal-footer">
                <button type="submit" class="btn btn-outline-primary">
                  회원가입
                </button>
                <button type="reset" class="btn btn-outline-success">
                  초기화
                </button>
                <button
                  type="button"
                  class="btn btn-outline-secondary"
                  data-bs-dismiss="modal"
                >
                  닫기
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>

      <!-- 로그인 모달 -->
      <div
        class="modal fade"
        id="signinModal"
        tabindex="-1"
        aria-labelledby="signinModalLabel"
        aria-hidden="true"
      >
        <div class="modal-dialog">
          <div class="modal-content">
            <form
              action="<%=request.getContextPath()%>/main?action=login"
              method="post"
            >
              <div class="modal-header">
                <h1 class="modal-title fs-5"><b>로그인</b></h1>
                <button
                  type="button"
                  class="btn-close"
                  data-bs-dismiss="modal"
                  aria-label="Close"
                ></button>
              </div>

              <div class="modal-body">
                <div class="mb-3">
                  <label for="emailInput" class="form-label">이메일</label>
                  <input
                    type="email"
                    class="form-control"
                    id="emailInput"
                    name="email"
                    placeholder="이메일"
                    required
                  />
                </div>
                <div class="mb-3">
                  <label for="passwordInput" class="form-label">비밀번호</label>
                  <input
                    type="password"
                    class="form-control"
                    id="passwordInput"
                    name="password"
                    placeholder="비밀번호"
                    required
                  />
                </div>
              </div>

              <div class="modal-footer">
                <button type="submit" class="btn btn-outline-primary">
                  로그인
                </button>
                <button type="button" class="btn btn-outline-success">
                  아이디 찾기
                </button>
                <button type="button" class="btn btn-outline-secondary">
                  비밀번호 찾기
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
