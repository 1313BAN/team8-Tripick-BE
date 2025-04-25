package com.ssafy.live.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.live.model.dto.HotPlaceDto;

public class HotPlaceDao {

    private static final String SQL_INSERT = "INSERT INTO hot_place (user_id, title, description, address, latitude, longitude, image_url) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE hot_place SET title = ?, description = ?, address = ?, latitude = ?, longitude = ?, image_url = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM hot_place WHERE id = ?";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM hot_place WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM hot_place";
    private static final String SQL_SELECT_BY_USER = "SELECT * FROM hot_place WHERE user_id = ?";

    // 핫플레이스 등록
    public int insertHotPlace(Connection conn, HotPlaceDto hotPlace) throws SQLException {
        int generatedId = -1;
        System.out.println("[DAO] Inserting hot place: " + hotPlace);
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, hotPlace.getUserId());
            pstmt.setString(2, hotPlace.getTitle());
            pstmt.setString(3, hotPlace.getDescription());
            pstmt.setString(4, hotPlace.getAddress());
            pstmt.setBigDecimal(5, hotPlace.getLatitude());
            pstmt.setBigDecimal(6, hotPlace.getLongitude());
            pstmt.setString(7, hotPlace.getImageUrl());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Insert failed, no rows affected.");
            }
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                    hotPlace.setId(generatedId);
                    System.out.println("[DAO] Generated hot place id: " + generatedId);
                }
            }
        }
        return generatedId;
    }

    // 핫플레이스 수정
    public int updateHotPlace(Connection conn, HotPlaceDto hotPlace) throws SQLException {
        System.out.println("[DAO] Updating hot place: " + hotPlace);
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE)) {
            pstmt.setString(1, hotPlace.getTitle());
            pstmt.setString(2, hotPlace.getDescription());
            pstmt.setString(3, hotPlace.getAddress());
            pstmt.setBigDecimal(4, hotPlace.getLatitude());
            pstmt.setBigDecimal(5, hotPlace.getLongitude());
            pstmt.setString(6, hotPlace.getImageUrl());
            pstmt.setInt(7, hotPlace.getId());
            return pstmt.executeUpdate();
        }
    }

    // 핫플레이스 삭제
    public int deleteHotPlace(Connection conn, int id) throws SQLException {
        System.out.println("[DAO] Deleting hot place with id: " + id);
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_DELETE)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        }
    }

    // 단건 조회
    public HotPlaceDto getHotPlaceById(Connection conn, int id) throws SQLException {
        System.out.println("[DAO] Retrieving hot place with id: " + id);
        HotPlaceDto hotPlace = null;
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    hotPlace = mapToHotPlace(rs);
                }
            }
        }
        return hotPlace;
    }

    // 전체 조회
    public List<HotPlaceDto> getAllHotPlaces(Connection conn) throws SQLException {
        System.out.println("[DAO] Retrieving all hot places");
        List<HotPlaceDto> list = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                HotPlaceDto hotPlace = mapToHotPlace(rs);
                list.add(hotPlace);
            }
        }
        return list;
    }

    // 특정 사용자에 대한 핫플레이스 조회
    public List<HotPlaceDto> getHotPlacesByUser(Connection conn, int userId) throws SQLException {
        System.out.println("[DAO] Retrieving hot places for user id: " + userId);
        List<HotPlaceDto> list = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_BY_USER)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    HotPlaceDto hotPlace = mapToHotPlace(rs);
                    list.add(hotPlace);
                }
            }
        }
        return list;
    }

    // ResultSet -> HotPlaceDto 매핑 메소드
    private HotPlaceDto mapToHotPlace(ResultSet rs) throws SQLException {
        HotPlaceDto hotPlace = new HotPlaceDto();
        hotPlace.setId(rs.getInt("id"));
        hotPlace.setUserId(rs.getInt("user_id"));
        hotPlace.setTitle(rs.getString("title"));
        hotPlace.setDescription(rs.getString("description"));
        hotPlace.setAddress(rs.getString("address"));
        hotPlace.setLatitude(rs.getBigDecimal("latitude"));
        hotPlace.setLongitude(rs.getBigDecimal("longitude"));
        hotPlace.setImageUrl(rs.getString("image_url"));
        hotPlace.setCreatedAt(rs.getTimestamp("created_at"));
        hotPlace.setUpdatedAt(rs.getTimestamp("updated_at"));
        return hotPlace;
    }
}
