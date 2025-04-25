package com.ssafy.live.model.service;

import com.ssafy.live.model.dao.BoardDao;
import com.ssafy.live.model.dto.BoardDto;
import com.ssafy.live.util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BoardService {
    private BoardDao boardDao = BoardDao.getInstance();

    public boolean addBoard(BoardDto board) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);
            int result = boardDao.insertBoard(board, conn);
            conn.commit();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<BoardDto> getBoardList() throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            return boardDao.selectAll(conn);
        }
    }

    public boolean deleteBoard(int id) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);
            int result = boardDao.deleteBoard(id, conn);
            conn.commit();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public BoardDto getBoardById(int id) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            return boardDao.selectById(id, conn);
        }
    }
    
}
