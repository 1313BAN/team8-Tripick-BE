package com.ssafy.live.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.live.model.dto.AttractionDto;

public class AttractionDao {

    // SQL 쿼리 상수화
    private static final String SQL_INSERT = "INSERT INTO attractions (content_id, title, content_type_id, area_code, si_gun_gu_code, "
            + "first_image1, first_image2, map_level, latitude, longitude, tel, addr1, addr2, homepage, overview) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_BY_CONTENT_TYPE = "SELECT * FROM attractions WHERE content_type_id = ?";
    private static final String SQL_SELECT_BY_REGION = "SELECT * FROM attractions WHERE si_gun_gu_code = ?";
    private static final String SQL_SELECT_BY_CONTENT_TYPE_AND_REGION = "SELECT * FROM attractions WHERE content_type_id = ? AND si_gun_gu_code = ?";
    private static final String SQL_UPDATE = "UPDATE attractions SET content_id = ?, title = ?, content_type_id = ?, area_code = ?, si_gun_gu_code = ?, "
            + "first_image1 = ?, first_image2 = ?, map_level = ?, latitude = ?, longitude = ?, tel = ?, addr1 = ?, addr2 = ?, "
            + "homepage = ?, overview = ? WHERE no = ?";
    private static final String SQL_DELETE = "DELETE FROM attractions WHERE no = ?";
    private static final String SQL_SELECT_BY_ID = "SELECT no, content_id, title, content_type_id, area_code, si_gun_gu_code, "
            + "first_image1, first_image2, map_level, latitude, longitude, tel, addr1, addr2, homepage, overview "
            + "FROM attractions WHERE no = ?";
    private static final String SQL_SELECT_ALL = "SELECT no, content_id, title, content_type_id, area_code, si_gun_gu_code, "
            + "first_image1, first_image2, map_level, latitude, longitude, tel, addr1, addr2, homepage, overview "
            + "FROM attractions";

    // 등록 (INSERT)
    public int insertAttraction(Connection conn, AttractionDto attraction) throws SQLException {
        int generatedId = -1;
        System.out.println("[DAO] Inserting attraction: " + attraction);
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, attraction.getContentId());
            pstmt.setString(2, attraction.getTitle());
            pstmt.setInt(3, attraction.getContentTypeId());
            pstmt.setInt(4, attraction.getAreaCode());
            pstmt.setInt(5, attraction.getSiGunGuCode());
            pstmt.setString(6, attraction.getFirstImage1());
            pstmt.setString(7, attraction.getFirstImage2());
            pstmt.setInt(8, attraction.getMapLevel());
            pstmt.setBigDecimal(9, attraction.getLatitude());
            pstmt.setBigDecimal(10, attraction.getLongitude());
            pstmt.setString(11, attraction.getTel());
            pstmt.setString(12, attraction.getAddr1());
            pstmt.setString(13, attraction.getAddr2());
            pstmt.setString(14, attraction.getHomepage());
            pstmt.setString(15, attraction.getOverview());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Insert failed, no rows affected.");
            }

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                    attraction.setNo(generatedId);
                    System.out.println("[DAO] Generated attraction no: " + generatedId);
                }
            }
        }
        return generatedId;
    }

    // content_type_id로 조회
    public List<AttractionDto> getAttractionsByContentTypeId(Connection conn, int contentTypeId) throws SQLException {
        System.out.println("[DAO] Retrieving attractions by content_type_id: " + contentTypeId);
        List<AttractionDto> attractionList = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_BY_CONTENT_TYPE)) {
            pstmt.setInt(1, contentTypeId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    AttractionDto attraction = mapToAttraction(rs);
                    attractionList.add(attraction);
                }
            }
        }
        return attractionList;
    }

    // sigungu로 조회
    public List<AttractionDto> getAttractionsByRegion(Connection conn, int sigungu) throws SQLException {
        System.out.println("[DAO] Retrieving attractions by region (si_gun_gu_code): " + sigungu);
        List<AttractionDto> attractionList = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_BY_REGION)) {
            pstmt.setInt(1, sigungu);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    AttractionDto attraction = mapToAttraction(rs);
                    attractionList.add(attraction);
                }
            }
        }
        return attractionList;
    }

    // content_type_id와 sigungu로 조회
    public List<AttractionDto> getAttractionsByContentTypeIdAndRegion(Connection conn, int contentTypeId, int sigungu) throws SQLException {
        System.out.println("[DAO] Retrieving attractions by content_type_id: " + contentTypeId + " and region: " + sigungu);
        List<AttractionDto> attractionList = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_BY_CONTENT_TYPE_AND_REGION)) {
            pstmt.setInt(1, contentTypeId);
            pstmt.setInt(2, sigungu);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    AttractionDto attraction = mapToAttraction(rs);
                    attractionList.add(attraction);
                }
            }
        }
        return attractionList;
    }

    // 수정 (UPDATE)
    public int updateAttraction(Connection conn, AttractionDto attraction) throws SQLException {
        System.out.println("[DAO] Updating attraction: " + attraction);
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE)) {
            pstmt.setInt(1, attraction.getContentId());
            pstmt.setString(2, attraction.getTitle());
            pstmt.setInt(3, attraction.getContentTypeId());
            pstmt.setInt(4, attraction.getAreaCode());
            pstmt.setInt(5, attraction.getSiGunGuCode());
            pstmt.setString(6, attraction.getFirstImage1());
            pstmt.setString(7, attraction.getFirstImage2());
            pstmt.setInt(8, attraction.getMapLevel());
            pstmt.setBigDecimal(9, attraction.getLatitude());
            pstmt.setBigDecimal(10, attraction.getLongitude());
            pstmt.setString(11, attraction.getTel());
            pstmt.setString(12, attraction.getAddr1());
            pstmt.setString(13, attraction.getAddr2());
            pstmt.setString(14, attraction.getHomepage());
            pstmt.setString(15, attraction.getOverview());
            pstmt.setInt(16, attraction.getNo());
            return pstmt.executeUpdate();
        }
    }

    // 삭제 (DELETE)
    public int deleteAttraction(Connection conn, int attractionNo) throws SQLException {
        System.out.println("[DAO] Deleting attraction with no: " + attractionNo);
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_DELETE)) {
            pstmt.setInt(1, attractionNo);
            return pstmt.executeUpdate();
        }
    }

    // 단건 조회 (SELECT by no)
    public AttractionDto getAttractionById(Connection conn, int id) throws SQLException {
        System.out.println("[DAO] Retrieving attraction with no: " + id);
        AttractionDto attraction = null;
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    attraction = mapToAttraction(rs);
                }
            }
        }
        return attraction;
    }

    // 전체 조회 (SELECT all)
    public List<AttractionDto> getAllAttractions(Connection conn) throws SQLException {
        System.out.println("[DAO] Retrieving all attractions");
        List<AttractionDto> attractionList = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                AttractionDto attraction = mapToAttraction(rs);
                attractionList.add(attraction);
            }
        }
        return attractionList;
    }

    // ResultSet -> AttractionDto 매핑 메소드
    private AttractionDto mapToAttraction(ResultSet rs) throws SQLException {
        AttractionDto attraction = new AttractionDto();
        attraction.setNo(rs.getInt("no"));
        attraction.setContentId(rs.getInt("content_id"));
        attraction.setTitle(rs.getString("title"));
        attraction.setContentTypeId(rs.getInt("content_type_id"));
        attraction.setAreaCode(rs.getInt("area_code"));
        attraction.setSiGunGuCode(rs.getInt("si_gun_gu_code"));
        attraction.setFirstImage1(rs.getString("first_image1"));
        attraction.setFirstImage2(rs.getString("first_image2"));
        attraction.setMapLevel(rs.getInt("map_level"));
        attraction.setLatitude(rs.getBigDecimal("latitude"));
        attraction.setLongitude(rs.getBigDecimal("longitude"));
        attraction.setTel(rs.getString("tel"));
        attraction.setAddr1(rs.getString("addr1"));
        attraction.setAddr2(rs.getString("addr2"));
        attraction.setHomepage(rs.getString("homepage"));
        attraction.setOverview(rs.getString("overview"));
        return attraction;
    }
}
