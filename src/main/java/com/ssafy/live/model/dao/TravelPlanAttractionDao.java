package com.ssafy.live.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.live.model.dto.TravelPlanAttractionDto;

public class TravelPlanAttractionDao {

    private static final String SQL_INSERT = "INSERT INTO travel_plan_attraction (plan_id, attraction_id, sequence) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE travel_plan_attraction SET attraction_id = ?, sequence = ? WHERE plan_attraction_id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM travel_plan_attraction WHERE plan_attraction_id = ?";
    private static final String SQL_DELETE_BY_PLAN = "DELETE FROM travel_plan_attraction WHERE plan_id = ?";
    private static final String SQL_SELECT_BY_PLAN = "SELECT plan_attraction_id, plan_id, attraction_id, sequence FROM travel_plan_attraction WHERE plan_id = ?";
    private static final String SQL_SELECT_BY_ID = "SELECT plan_attraction_id, plan_id, attraction_id, sequence FROM travel_plan_attraction WHERE plan_attraction_id = ?";

    // 등록 (INSERT)
    public int insertTravelPlanAttraction(Connection conn, TravelPlanAttractionDto dto) throws SQLException {
        int generatedId = -1;
        System.out.println("[DAO] Inserting travel plan attraction mapping: " + dto);
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, dto.getPlanId());
            pstmt.setInt(2, dto.getAttractionId());
            pstmt.setInt(3, dto.getSequence());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Insert failed, no rows affected.");
            }
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                    dto.setPlanAttractionId(generatedId);
                    System.out.println("[DAO] Generated travel plan attraction mapping id: " + generatedId);
                }
            }
        }
        return generatedId;
    }

    // 수정 (UPDATE)
    public int updateTravelPlanAttraction(Connection conn, TravelPlanAttractionDto dto) throws SQLException {
        System.out.println("[DAO] Updating travel plan attraction mapping: " + dto);
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE)) {
            pstmt.setInt(1, dto.getAttractionId());
            pstmt.setInt(2, dto.getSequence());
            pstmt.setInt(3, dto.getPlanAttractionId());
            return pstmt.executeUpdate();
        }
    }

    // 특정 매핑 레코드 삭제 (DELETE by plan_attraction_id)
    public int deleteTravelPlanAttractionById(Connection conn, int planAttractionId) throws SQLException {
        System.out.println("[DAO] Deleting travel plan attraction mapping with id: " + planAttractionId);
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_DELETE_BY_ID)) {
            pstmt.setInt(1, planAttractionId);
            return pstmt.executeUpdate();
        }
    }

    // 특정 여행계획에 속한 모든 매핑 삭제 (DELETE by plan_id)
    public int deleteTravelPlanAttractionsByPlanId(Connection conn, int planId) throws SQLException {
        System.out.println("[DAO] Deleting all travel plan attraction mappings for plan id: " + planId);
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_DELETE_BY_PLAN)) {
            pstmt.setInt(1, planId);
            return pstmt.executeUpdate();
        }
    }

    // 여행계획에 속한 관광지 목록 조회 (SELECT by plan_id)
    public List<TravelPlanAttractionDto> getTravelPlanAttractionsByPlanId(Connection conn, int planId) throws SQLException {
        System.out.println("[DAO] Retrieving travel plan attraction mappings for plan id: " + planId);
        List<TravelPlanAttractionDto> list = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_BY_PLAN)) {
            pstmt.setInt(1, planId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TravelPlanAttractionDto dto = mapToTravelPlanAttraction(rs);
                    list.add(dto);
                }
            }
        }
        return list;
    }

    // 단건 조회 (SELECT by plan_attraction_id)
    public TravelPlanAttractionDto getTravelPlanAttractionById(Connection conn, int planAttractionId) throws SQLException {
        System.out.println("[DAO] Retrieving travel plan attraction mapping with id: " + planAttractionId);
        TravelPlanAttractionDto dto = null;
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            pstmt.setInt(1, planAttractionId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    dto = mapToTravelPlanAttraction(rs);
                }
            }
        }
        return dto;
    }

    // ResultSet -> TravelPlanAttractionDto 매핑 메소드
    private TravelPlanAttractionDto mapToTravelPlanAttraction(ResultSet rs) throws SQLException {
        TravelPlanAttractionDto dto = new TravelPlanAttractionDto();
        dto.setPlanAttractionId(rs.getInt("plan_attraction_id"));
        dto.setPlanId(rs.getInt("plan_id"));
        dto.setAttractionId(rs.getInt("attraction_id"));
        dto.setSequence(rs.getInt("sequence"));
        return dto;
    }
}
