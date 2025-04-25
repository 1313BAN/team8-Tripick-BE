package com.ssafy.live.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ssafy.live.model.dao.HotPlaceDao;
import com.ssafy.live.model.dto.HotPlaceDto;
import com.ssafy.live.util.DBUtil;

public class HotPlaceService {
    private HotPlaceDao hotPlaceDao = new HotPlaceDao();

    // 핫플레이스 등록
    public int addHotPlace(HotPlaceDto hotPlace) throws SQLException {
        System.out.println("[Service] Adding hot place: " + hotPlace);
        try (Connection conn = DBUtil.getConnection()) {
            try {
                conn.setAutoCommit(false);
                int result = hotPlaceDao.insertHotPlace(conn, hotPlace);
                conn.commit();
                System.out.println("[Service] Hot place added successfully: " + hotPlace);
                return result;
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("[Service] Error adding hot place: " + hotPlace);
                throw e;
            }
        }
    }

    // 핫플레이스 수정
    public boolean updateHotPlace(HotPlaceDto hotPlace) throws SQLException {
        System.out.println("[Service] Updating hot place: " + hotPlace);
        try (Connection conn = DBUtil.getConnection()) {
            try {
                conn.setAutoCommit(false);
                int result = hotPlaceDao.updateHotPlace(conn, hotPlace);
                conn.commit();
                System.out.println("[Service] Hot place updated successfully: " + hotPlace);
                return result > 0;
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("[Service] Error updating hot place: " + hotPlace);
                throw e;
            }
        }
    }

    // 핫플레이스 삭제
    public boolean deleteHotPlace(int id) throws SQLException {
        System.out.println("[Service] Deleting hot place with id: " + id);
        try (Connection conn = DBUtil.getConnection()) {
            try {
                conn.setAutoCommit(false);
                int result = hotPlaceDao.deleteHotPlace(conn, id);
                conn.commit();
                System.out.println("[Service] Hot place deleted successfully: id=" + id);
                return result > 0;
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("[Service] Error deleting hot place with id: " + id);
                throw e;
            }
        }
    }

    // 단건 조회
    public HotPlaceDto getHotPlaceById(int id) throws SQLException {
        System.out.println("[Service] Retrieving hot place with id: " + id);
        try (Connection conn = DBUtil.getConnection()) {
            return hotPlaceDao.getHotPlaceById(conn, id);
        }
    }

    // 전체 조회
    public List<HotPlaceDto> getAllHotPlaces() throws SQLException {
        System.out.println("[Service] Retrieving all hot places");
        try (Connection conn = DBUtil.getConnection()) {
            return hotPlaceDao.getAllHotPlaces(conn);
        }
    }

    // 특정 사용자에 대한 핫플레이스 조회
    public List<HotPlaceDto> getHotPlacesByUser(int userId) throws SQLException {
        System.out.println("[Service] Retrieving hot places for user id: " + userId);
        try (Connection conn = DBUtil.getConnection()) {
            return hotPlaceDao.getHotPlacesByUser(conn, userId);
        }
    }
}
