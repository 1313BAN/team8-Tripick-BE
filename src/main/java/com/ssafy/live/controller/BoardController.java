package com.ssafy.live.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ssafy.live.model.dto.BoardDto;
import com.ssafy.live.model.dto.MemberDto;
import com.ssafy.live.model.service.BoardService;
import com.ssafy.live.model.service.MemberService;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/board")
public class BoardController extends HttpServlet {
    private BoardService boardService = new BoardService();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try {
            switch (action) {
            case "list" -> {
                String keyword = Optional.ofNullable(req.getParameter("keyword")).orElse("");
                int page = Optional.ofNullable(req.getParameter("page"))
                            .map(Integer::parseInt).orElse(1);
                int size = 10; // 한 페이지에 10개

                // 전체 게시글 목록을 DB에서 모두 조회 (필터링은 자바 단에서 수행)
                List<BoardDto> allBoards = boardService.getBoardList(); // 전체 데이터 조회
                // 검색어를 포함하는 제목의 게시글만 필터링 (KMP 알고리즘 활용)
                List<BoardDto> filtered = new ArrayList<>();
                for (BoardDto b : allBoards) {
                    if (matchesByKMP(b.getTitle(), keyword)) {  // 제목에 keyword가 포함되는지 검사
                        filtered.add(b);
                    }
                }
                // 필터링된 게시글의 총 개수
                int total = filtered.size();

                // 현재 페이지에 표시할 게시글의 시작/끝 인덱스 계산
                int fromIndex = Math.min((page - 1) * size, total);
                int toIndex = Math.min(fromIndex + size, total);

                // 페이지네이션 처리된 게시글 리스트 생성
                List<BoardDto> paged = filtered.subList(fromIndex, toIndex);

                // JSP에서 사용할 데이터 설정
                req.setAttribute("boards", paged);                // 현재 페이지에 해당하는 게시글 목록
                req.setAttribute("page", page);                   // 현재 페이지 번호
                req.setAttribute("totalPages", (int) Math.ceil((double) total / size)); // 전체 페이지 수
                req.setAttribute("keyword", keyword);             // 검색어 유지

                // 게시글 리스트 페이지로 포워딩
                req.getRequestDispatcher("/board/list.jsp").forward(req, resp);
            }
                case "write" -> {
                    req.getRequestDispatcher("/board/write.jsp").forward(req, resp);
                }
                case "regist" -> {
                    MemberDto user = (MemberDto) req.getSession().getAttribute("loginUser");
                    if (user == null) {
                        resp.sendRedirect(req.getContextPath() + "/main?action=login");
                        return;
                    }
                    String title = req.getParameter("title");
                    String content = req.getParameter("content");
                    BoardDto board = new BoardDto(user.getId(), title, content);
                    boardService.addBoard(board);
                    resp.sendRedirect(req.getContextPath() + "/board?action=list");
                }
                case "view" -> {
                    int id = Integer.parseInt(req.getParameter("id"));
                    BoardDto board = boardService.getBoardById(id);
                    if (board != null) {
                        req.setAttribute("board", board);
                        req.getRequestDispatcher("/board/view.jsp").forward(req, resp);
                    } else {
                        resp.sendError(404, "게시글을 찾을 수 없습니다.");
                    }
                }
                default -> resp.sendError(404);
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }

    public boolean matchesByKMP(String text, String pattern) {
        if (pattern.isEmpty()) return true;
        int[] lps = computeLPS(pattern);
        int i = 0, j = 0;
        while (i < text.length()) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++; j++;
                if (j == pattern.length()) return true;
            } else if (j > 0) {
                j = lps[j - 1];
            } else {
                i++;
            }
        }
        return false;
    }

    private int[] computeLPS(String pattern) {
        int[] lps = new int[pattern.length()];
        int len = 0;
        for (int i = 1; i < pattern.length(); ) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                lps[i++] = ++len;
            } else if (len > 0) {
                len = lps[len - 1];
            } else {
                lps[i++] = 0;
            }
        }
        return lps;
    }
}
