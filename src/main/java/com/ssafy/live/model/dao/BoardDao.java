package com.ssafy.live.model.dao;

import com.ssafy.live.model.dto.BoardDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardDao {
    private static BoardDao instance = new BoardDao();
    private BoardDao() {}
    public static BoardDao getInstance() { return instance; }

    // 게시글 등록
    private static final String SQL_INSERT =
        "INSERT INTO boards (member_id, title, content) VALUES (?, ?, ?)";

    // 전체 게시글 목록 (작성자 이름까지)
    private static final String SQL_SELECT_ALL =
        "SELECT b.id, b.title, b.content, b.created_at, b.member_id, m.username AS writer_name " +
        "FROM boards b JOIN members m ON b.member_id = m.id " +
        "ORDER BY b.created_at DESC";

    // 게시글 삭제
    private static final String SQL_DELETE =
        "DELETE FROM boards WHERE id = ?";

    public int insertBoard(BoardDto board, Connection conn) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, board.getMemberId());
            pstmt.setString(2, board.getTitle());
            pstmt.setString(3, board.getContent());
            return pstmt.executeUpdate();
        }
    }

    public List<BoardDto> selectAll(Connection conn) throws SQLException {
        List<BoardDto> list = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                BoardDto board = new BoardDto();
                board.setId(rs.getInt("id"));
                board.setTitle(rs.getString("title"));
                board.setContent(rs.getString("content"));
                board.setCreatedAt(rs.getTimestamp("created_at"));
                board.setMemberId(rs.getInt("member_id"));
                board.setWriterName(rs.getString("writer_name")); // username from join
                list.add(board);
            }
        }

        return list;
    }

    public int deleteBoard(int id, Connection conn) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_DELETE)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        }
    }
    
    public BoardDto selectById(int id, Connection conn) throws SQLException {
        String sql = "SELECT b.id, b.title, b.content, b.created_at, b.member_id, m.username AS writer_name " +
                     "FROM boards b JOIN members m ON b.member_id = m.id WHERE b.id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    BoardDto board = new BoardDto();
                    board.setId(rs.getInt("id"));
                    board.setTitle(rs.getString("title"));
                    board.setContent(rs.getString("content"));
                    board.setCreatedAt(rs.getTimestamp("created_at"));
                    board.setMemberId(rs.getInt("member_id"));
                    board.setWriterName(rs.getString("writer_name"));
                    return board;
                }
            }
        }
        return null;
    }

}
