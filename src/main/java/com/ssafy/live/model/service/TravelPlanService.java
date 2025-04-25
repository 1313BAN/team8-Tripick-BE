package com.ssafy.live.model.service;

import java.sql.Connection;
import java.util.List;

import com.ssafy.live.model.dao.TravelPlanAttractionDao;
import com.ssafy.live.model.dao.TravelPlanDao;
import com.ssafy.live.model.dto.TravelPlanAttractionDto;
import com.ssafy.live.model.dto.TravelPlanDto;
import com.ssafy.live.util.DBUtil;

public class TravelPlanService {
    private TravelPlanDao travelPlanDao = new TravelPlanDao();
    private TravelPlanAttractionDao travelPlanAttractionDao = new TravelPlanAttractionDao();

    /**
     * 여행계획과 그에 속한 관광지 매핑들을 한 트랜잭션 내에서 저장합니다.
     * 만약 둘 중 하나라도 실패하면 전체 롤백됩니다.
     *
     * @param travelPlan 여행계획 정보 (내부에 관광지 매핑 목록 포함)
     * @return 생성된 여행계획의 plan_id
     * @throws Exception
     */
    public int createTravelPlanWithAttractions(TravelPlanDto travelPlan) throws Exception {
        System.out.println("[Service] Starting creation of travel plan with attractions.");
        try (Connection conn = DBUtil.getConnection()) {
            try {
                conn.setAutoCommit(false);
                // 1. 여행계획 등록
                int planId = travelPlanDao.insertTravelPlan(conn, travelPlan);
                System.out.println("[Service] Travel plan created with id: " + planId);
                
                // 2. 여행계획에 포함된 관광지 매핑 등록 (존재하는 경우)
                List<TravelPlanAttractionDto> attractions = travelPlan.getAttractions();
                if (attractions != null) {
                    for (TravelPlanAttractionDto dto : attractions) {
                        // 등록 전에 plan_id를 설정해준다.
                        dto.setPlanId(planId);
                        travelPlanAttractionDao.insertTravelPlanAttraction(conn, dto);
                        System.out.println("[Service] Inserted attraction mapping: " + dto);
                    }
                }
                conn.commit();
                System.out.println("[Service] Transaction committed successfully.");
                return planId;
            } catch (Exception e) {
                conn.rollback();
                System.out.println("[Service] Transaction rolled back due to an error.");
                throw e;
            }
        }
    }
    
    /**
     * 여행계획 내용(예: title, description, 날짜 등)만 업데이트합니다.
     * (관광지 매핑 수정은 별도로 처리할 수 있습니다.)
     */
    public boolean updateTravelPlan(TravelPlanDto travelPlan) throws Exception {
        System.out.println("[Service] Updating travel plan: " + travelPlan);
        try (Connection conn = DBUtil.getConnection()) {
            try {
                conn.setAutoCommit(false);
                boolean success = travelPlanDao.updateTravelPlan(conn, travelPlan) > 0;
                conn.commit();
                System.out.println("[Service] Travel plan updated successfully.");
                return success;
            } catch (Exception e) {
                conn.rollback();
                System.out.println("[Service] Update transaction rolled back.");
                throw e;
            }
        }
    }
    
    /**
     * 여행계획 삭제 (ON DELETE CASCADE가 설정되어 있다면, 연결된 관광지 매핑도 같이 삭제됨)
     */
    public boolean deleteTravelPlan(int planId) throws Exception {
        System.out.println("[Service] Deleting travel plan with id: " + planId);
        try (Connection conn = DBUtil.getConnection()) {
            try {
                conn.setAutoCommit(false);
                boolean success = travelPlanDao.deleteTravelPlan(conn, planId) > 0;
                conn.commit();
                System.out.println("[Service] Travel plan deleted successfully.");
                return success;
            } catch (Exception e) {
                conn.rollback();
                System.out.println("[Service] Delete transaction rolled back.");
                throw e;
            }
        }
    }
    
    /**
     * 단건 조회: plan_id로 여행계획 정보를 가져옵니다.
     */
    public TravelPlanDto getTravelPlanById(int planId) throws Exception {
        System.out.println("[Service] Retrieving travel plan with id: " + planId);
        try (Connection conn = DBUtil.getConnection()) {
            return travelPlanDao.getTravelPlanById(conn, planId);
        }
    }
    
    /**
     * 전체 여행계획 조회
     */
    public List<TravelPlanDto> getAllTravelPlans() throws Exception {
        System.out.println("[Service] Retrieving all travel plans.");
        try (Connection conn = DBUtil.getConnection()) {
            return travelPlanDao.getAllTravelPlans(conn);
        }
    }
    
    /**
     * 특정 사용자(user_id)에 대한 여행계획 조회
     */
    public List<TravelPlanDto> getTravelPlansByUserId(int userId) throws Exception {
        System.out.println("[Service] Retrieving travel plans for user id: " + userId);
        try (Connection conn = DBUtil.getConnection()) {
            return travelPlanDao.getTravelPlansByUserId(conn, userId);
        }
    }
}
