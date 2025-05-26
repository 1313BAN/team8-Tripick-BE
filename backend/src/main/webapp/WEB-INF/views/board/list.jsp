<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>📌 여행정보공유 게시판</h2>

<!-- 검색 폼 -->
<form method="get" action="${pageContext.request.contextPath}/board" class="mb-3 d-flex">
    <input type="hidden" name="action" value="list" />
    <input type="text" name="keyword" class="form-control me-2" placeholder="제목 검색"
           value="${keyword}" />
    <button type="submit" class="btn btn-outline-primary">검색</button>
</form>

<!-- 글쓰기 버튼 -->
<c:if test="${not empty sessionScope.loginUser}">
    <a href="${pageContext.request.contextPath}/board?action=write" class="btn btn-primary mb-3">글쓰기</a>
</c:if>

<!-- 게시글 테이블 -->
<table class="table table-bordered">
    <thead>
        <tr>
            <th>제목</th>
            <th>작성자</th>
            <th>작성일</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="board" items="${boards}">
            <tr>
                <td>
                    <a href="${pageContext.request.contextPath}/board?action=view&id=${board.id}">
                        ${board.title}
                    </a>
                </td>
                <td>${board.writerName}</td>
                <td>${board.createdAt}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<!-- 페이지네이션 -->
<div class="d-flex justify-content-center mt-4">
    <nav>
        <ul class="pagination">
            <c:if test="${page > 1}">
                <li class="page-item">
                    <a class="page-link"
                       href="${pageContext.request.contextPath}/board?action=list&keyword=${keyword}&page=${page - 1}">이전</a>
                </li>
            </c:if>
            <c:forEach begin="1" end="${totalPages}" var="i">
                <li class="page-item ${i == page ? 'active' : ''}">
                    <a class="page-link"
                       href="${pageContext.request.contextPath}/board?action=list&keyword=${keyword}&page=${i}">${i}</a>
                </li>
            </c:forEach>
            <c:if test="${page < totalPages}">
                <li class="page-item">
                    <a class="page-link"
                       href="${pageContext.request.contextPath}/board?action=list&keyword=${keyword}&page=${page + 1}">다음</a>
                </li>
            </c:if>
        </ul>
    </nav>
</div>

