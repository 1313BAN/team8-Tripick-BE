package com.ssafy.live.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.live.model.dto.TravelPlanDto;

public class TravelPlanDao {

    private static final String SQL_INSERT = "INSERT INTO travel_plan (user_id, title, description, start_date, end_date) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE travel_plan SET title = ?, description = ?, start_date = ?, end_date = ? WHERE plan_id = ?";
    private static final String SQL_DELETE = "DELETE FROM travel_plan WHERE plan_id = ?";
    private static final String SQL_SELECT_BY_ID = "SELECT plan_id, user_id, title, description, start_date, end_date, created_at, updated_at FROM travel_plan WHERE plan_id = ?";
    private static final String SQL_SELECT_ALL = "SELECT plan_id, user_id, title, description, start_date, end_date, created_at, updated_at FROM travel_plan";
    private static final String SQL_SELECT_BY_USER = "SELECT plan_id, user_id, title, description, start_date, end_date, created_at, updated_at FROM travel_plan WHERE user_id = ?";

    // 여행계획 등록 (INSERT)
    public int insertTravelPlan(Connection conn, TravelPlanDto travelPlan) throws SQLException {
        int generatedId = -1;
        System.out.println("[DAO] Inserting travel plan: " + travelPlan);
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, travelPlan.getUserId());
            pstmt.setString(2, travelPlan.getTitle());
            pstmt.setString(3, travelPlan.getDescription());
            pstmt.setDate(4, travelPlan.getStartDate());
            pstmt.setDate(5, travelPlan.getEndDate());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Insert failed, no rows affected.");
            }
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                    travelPlan.setPlanId(generatedId);
                    System.out.println("[DAO] Generated travel plan id: " + generatedId);
                }
            }
        }
        return generatedId;
    }

    // 여행계획 수정 (UPDATE)
    public int updateTravelPlan(Connection conn, TravelPlanDto travelPlan) throws SQLException {
        System.out.println("[DAO] Updating travel plan: " + travelPlan);
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE)) {
            pstmt.setString(1, travelPlan.getTitle());
            pstmt.setString(2, travelPlan.getDescription());
            pstmt.setDate(3, travelPlan.getStartDate());
            pstmt.setDate(4, travelPlan.getEndDate());
            pstmt.setInt(5, travelPlan.getPlanId());
            return pstmt.executeUpdate();
        }
    }

    // 여행계획 삭제 (DELETE)
    public int deleteTravelPlan(Connection conn, int planId) throws SQLException {
        System.out.println("[DAO] Deleting travel plan with id: " + planId);
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_DELETE)) {
            pstmt.setInt(1, planId);
            return pstmt.executeUpdate();
        }
    }

    // 단건 조회 (SELECT by plan_id)
    public TravelPlanDto getTravelPlanById(Connection conn, int planId) throws SQLException {
        System.out.println("[DAO] Retrieving travel plan with id: " + planId);
        TravelPlanDto travelPlan = null;
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            pstmt.setInt(1, planId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    travelPlan = mapToTravelPlan(rs);
                }
            }
        }
        return travelPlan;
    }

    // 전체 조회 (SELECT all)
    public List<TravelPlanDto> getAllTravelPlans(Connection conn) throws SQLException {
        System.out.println("[DAO] Retrieving all travel plans");
        List<TravelPlanDto> plans = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                TravelPlanDto travelPlan = mapToTravelPlan(rs);
                plans.add(travelPlan);
            }
        }
        return plans;
    }

    // 특정 사용자에 대한 여행계획 조회 (SELECT by user_id)
    public List<TravelPlanDto> getTravelPlansByUserId(Connection conn, int userId) throws SQLException {
        System.out.println("[DAO] Retrieving travel plans for user id: " + userId);
        List<TravelPlanDto> plans = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_BY_USER)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TravelPlanDto travelPlan = mapToTravelPlan(rs);
                    plans.add(travelPlan);
                }
            }
        }
        return plans;
    }
    
    // ResultSet -> TravelPlanDto 매핑 메소드
    private TravelPlanDto mapToTravelPlan(ResultSet rs) throws SQLException {
        TravelPlanDto travelPlan = new TravelPlanDto();
        travelPlan.setPlanId(rs.getInt("plan_id"));
        travelPlan.setUserId(rs.getInt("user_id"));
        travelPlan.setTitle(rs.getString("title"));
        travelPlan.setDescription(rs.getString("description"));
        travelPlan.setStartDate(rs.getDate("start_date"));
        travelPlan.setEndDate(rs.getDate("end_date"));
        travelPlan.setCreatedAt(rs.getTimestamp("created_at"));
        travelPlan.setUpdatedAt(rs.getTimestamp("updated_at"));
        return travelPlan;
    }
}
