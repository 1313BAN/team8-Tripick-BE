<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>📄 게시글 상세 보기</h2>

<table class="table">
    <tr>
        <th>제목</th>
        <td>${board.title}</td>
    </tr>
    <tr>
        <th>작성자</th>
        <td>${board.writerName}</td>
    </tr>
    <tr>
        <th>작성일</th>
        <td>${board.createdAt}</td>
    </tr>
    <tr>
        <th>내용</th>
        <td style="white-space: pre-line;">${board.content}</td>
    </tr>
</table>

<a href="${pageContext.request.contextPath}/board?action=list" class="btn btn-secondary">목록으로</a>
