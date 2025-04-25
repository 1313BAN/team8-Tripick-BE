<%@ page contentType="text/html;charset=UTF-8" %>

<h2>📝 여행정보공유 - 새 글 작성</h2>

<form action="${pageContext.request.contextPath}/board?action=regist" method="post">
    <div class="mb-3">
        <label for="title" class="form-label">제목</label>
        <input type="text" class="form-control" id="title" name="title" required />
    </div>

    <div class="mb-3">
        <label for="content" class="form-label">내용</label>
        <textarea class="form-control" id="content" name="content" rows="5" required></textarea>
    </div>

    <button type="submit" class="btn btn-primary">등록</button>
    <a href="${pageContext.request.contextPath}/board?action=list" class="btn btn-secondary">취소</a>
</form>
