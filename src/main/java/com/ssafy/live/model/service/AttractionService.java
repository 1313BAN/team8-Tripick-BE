package com.ssafy.live.model.service;

import java.sql.Connection;
import java.util.List;

import com.ssafy.live.model.dao.AttractionDao;
import com.ssafy.live.model.dto.AttractionDto;
import com.ssafy.live.util.DBUtil;

public class AttractionService {
    private AttractionDao attractionDao = new AttractionDao();

    // 등록
    public void insertAttraction(AttractionDto attraction) throws Exception {
        System.out.println("[Service] Inserting attraction: " + attraction);
        try (Connection conn = DBUtil.getConnection()) {
            try {
                conn.setAutoCommit(false);
                attractionDao.insertAttraction(conn, attraction);
                conn.commit();
                System.out.println("[Service] Attraction inserted successfully: " + attraction);
            } catch (Exception e) {
                conn.rollback();
                System.out.println("[Service] Error inserting attraction: " + attraction);
                throw e;
            }
        }
    }

    // content_type_id로 조회
    public List<AttractionDto> getAttractionsByContentTypeId(int contentTypeId) throws Exception {
        System.out.println("[Service] Retrieving attractions by content_type_id: " + contentTypeId);
        try (Connection conn = DBUtil.getConnection()) {
            return attractionDao.getAttractionsByContentTypeId(conn, contentTypeId);
        }
    }

    // region (si_gun_gu_code)로 조회
    public List<AttractionDto> getAttractionsByRegion(int sigungu) throws Exception {
        System.out.println("[Service] Retrieving attractions by region: " + sigungu);
        try (Connection conn = DBUtil.getConnection()) {
            return attractionDao.getAttractionsByRegion(conn, sigungu);
        }
    }

    // content_type_id와 region으로 조회
    public List<AttractionDto> getAttractionsByContentTypeIdAndRegion(int contentTypeId, int sigungu) throws Exception {
        System.out.println("[Service] Retrieving attractions by content_type_id: " + contentTypeId + " and region: " + sigungu);
        try (Connection conn = DBUtil.getConnection()) {
            return attractionDao.getAttractionsByContentTypeIdAndRegion(conn, contentTypeId, sigungu);
        }
    }
    
 // 단건 조회: attraction no로 조회
    public AttractionDto getAttractionById(int id) throws Exception {
        System.out.println("[Service] Retrieving attraction with no: " + id);
        try (Connection conn = DBUtil.getConnection()) {
            return attractionDao.getAttractionById(conn, id);
        }
    }
    
    // 수정
    public void updateAttraction(AttractionDto attraction) throws Exception {
        System.out.println("[Service] Updating attraction: " + attraction);
        try (Connection conn = DBUtil.getConnection()) {
            try {
                conn.setAutoCommit(false);
                attractionDao.updateAttraction(conn, attraction);
                conn.commit();
                System.out.println("[Service] Attraction updated successfully: " + attraction);
            } catch (Exception e) {
                conn.rollback();
                System.out.println("[Service] Error updating attraction: " + attraction);
                throw e;
            }
        }
    }

    // 삭제
    public void deleteAttraction(int attractionNo) throws Exception {
        System.out.println("[Service] Deleting attraction with no: " + attractionNo);
        try (Connection conn = DBUtil.getConnection()) {
            try {
                conn.setAutoCommit(false);
                attractionDao.deleteAttraction(conn, attractionNo);
                conn.commit();
                System.out.println("[Service] Attraction deleted successfully with no: " + attractionNo);
            } catch (Exception e) {
                conn.rollback();
                System.out.println("[Service] Error deleting attraction with no: " + attractionNo);
                throw e;
            }
        }
    }

    // 전체 조회
    public List<AttractionDto> getAllAttractions() throws Exception {
        System.out.println("[Service] Retrieving all attractions");
        try (Connection conn = DBUtil.getConnection()) {
            return attractionDao.getAllAttractions(conn);
        }
    }
}
