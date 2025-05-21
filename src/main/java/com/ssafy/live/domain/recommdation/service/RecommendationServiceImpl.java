package com.ssafy.live.domain.recommdation.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.live.domain.recommdation.dao.RecommendationDao;
import com.ssafy.live.domain.recommdation.dto.CategoryRecommendationDTO;
import com.ssafy.live.domain.recommdation.dto.RecommendationRequestDTO;
import com.ssafy.live.domain.recommdation.dto.SpotRecommendationDTO;
import com.ssafy.live.domain.user.dao.UserDao;
import com.ssafy.live.domain.user.dto.UserDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationDao recommendationDao;
    private final UserDao userDao;
    
    /**
     * 1. 성별과 나이에 따른 여행지 추천
     */
    @Override
    @Transactional(readOnly = true)
    public List<SpotRecommendationDTO> getRecommendationsByGenderAndAge(String gender, Integer minAge, Integer maxAge) {
        // 파라미터 검증
        if (minAge == null || minAge < 0) {
            minAge = 0;
        }
        if (maxAge == null || maxAge < minAge) {
            maxAge = 100;
        }
        
        return recommendationDao.selectTopSpotsByGenderAndAge(gender, minAge, maxAge);
    }
    
    /**
     * 2. 여행 동기에 따른 추천
     */
    @Override
    @Transactional(readOnly = true)
    public List<SpotRecommendationDTO> getRecommendationsByMotive(Integer motiveCode) {
        if (motiveCode == null) {
            throw new IllegalArgumentException("여행 동기 코드는 필수입니다.");
        }
        
        return recommendationDao.selectTopSpotsByMotive(motiveCode);
    }
    
    /**
     * 3. 2022년 가장 인기 있었던 여행지 (평점 기준)
     */
    @Override
    @Transactional(readOnly = true)
    public List<SpotRecommendationDTO> getPopularSpotsIn2022() {
        return recommendationDao.selectPopularSpotsIn2022();
    }
    
    /**
     * 4. 복합 조건 기반 추천 (동기, 나이, 성별, 관광지 타입 등)
     */
    @Override
    @Transactional(readOnly = true)
    public List<SpotRecommendationDTO> getComplexRecommendations(RecommendationRequestDTO requestDTO) {
        // 입력값 검증
        if (requestDTO == null) {
            throw new IllegalArgumentException("요청 정보가 없습니다.");
        }
        
        // 나이 범위 기본값 설정
        if (requestDTO.getMinAge() == null) {
            requestDTO.setMinAge(0);
        }
        if (requestDTO.getMaxAge() == null || requestDTO.getMaxAge() < requestDTO.getMinAge()) {
            requestDTO.setMaxAge(100);
        }
        
        // 결과 개수 제한 기본값 설정
        if (requestDTO.getLimit() == null || requestDTO.getLimit() <= 0) {
            requestDTO.setLimit(10);
        }
        
        return recommendationDao.selectSpotsByMultipleCriteria(requestDTO);
    }
    
    /**
     * 5. 특정 지역 기반 추천
     */
    @Override
    @Transactional(readOnly = true)
    public List<SpotRecommendationDTO> getRecommendationsByArea(Integer areaCode, Integer siGunGuCode) {
        if (areaCode == null) {
            throw new IllegalArgumentException("지역 코드는 필수입니다.");
        }
        
        return recommendationDao.selectTopSpotsByArea(areaCode, siGunGuCode);
    }
    
    /**
     * 6. 컨텐츠 타입별 추천
     */
    @Override
    @Transactional(readOnly = true)
    public List<SpotRecommendationDTO> getRecommendationsByContentType(Integer contentTypeId) {
        if (contentTypeId == null) {
            throw new IllegalArgumentException("컨텐츠 타입 ID는 필수입니다.");
        }
        
        return recommendationDao.selectTopSpotsByContentType(contentTypeId);
    }
    
    
    @Override
    @Transactional(readOnly = true)
    public CategoryRecommendationDTO getRecommendationsByUserGenderAndAgeWithCategory(Integer userId) {
        // 사용자 정보 조회
        UserDto user = userDao.findById(userId);
        if (user == null) {
            throw new NoSuchElementException("존재하지 않는 사용자입니다.");
        }
        
        // 사용자 연령대 계산
        int age = user.getAge();
        int minAge = Math.max(0, age - 5);
        int maxAge = age + 5;
        
        // 성별과 나이 기반 추천 목록 조회
        List<SpotRecommendationDTO> recommendations = 
            recommendationDao.selectTopSpotsByGenderAndAge(user.getGender(), minAge, maxAge);
        
        // 카테고리 정보를 포함한 응답 생성
        CategoryRecommendationDTO categoryDTO = new CategoryRecommendationDTO();
        categoryDTO.setCategoryName("당신과 비슷한 여행자들이 선호하는 여행지");
        categoryDTO.setCategoryDescription(
            user.getGender() + " " + minAge + "~" + maxAge + "세 여행자들이 높은 평점을 준 장소");
        categoryDTO.setSpots(recommendations);
        
        return categoryDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryRecommendationDTO getRecommendationsByUserMotiveWithCategory(Integer userId) {
        // 사용자 존재 확인
        UserDto user = userDao.findById(userId);
        if (user == null) {
            throw new NoSuchElementException("존재하지 않는 사용자입니다.");
        }
        
        // 사용자의 여행 동기 조회
        List<Integer> userMotives = recommendationDao.selectUserMotives(userId);
        CategoryRecommendationDTO categoryDTO = new CategoryRecommendationDTO();
        
        if (userMotives == null || userMotives.isEmpty()) {
            // 여행 동기가 설정되지 않은 경우, 인기 장소 추천
            List<SpotRecommendationDTO> recommendations = recommendationDao.selectPopularSpotsIn2022();
            categoryDTO.setCategoryName("인기 여행지 추천");
            categoryDTO.setCategoryDescription("다른 여행자들에게 인기 있는 장소");
            categoryDTO.setSpots(recommendations);
        } else {
            // 첫 번째 여행 동기를 이용한 추천
            Integer motiveCode = userMotives.get(0);
            String motiveName = recommendationDao.selectMotiveName(motiveCode);
            List<SpotRecommendationDTO> recommendations = 
                recommendationDao.selectTopSpotsByMotive(motiveCode);
            
            categoryDTO.setCategoryName(motiveName + " 여행에 적합한 장소");
            categoryDTO.setCategoryDescription(
                motiveName + " 목적의 여행자들이 높은 평점을 준 장소");
            categoryDTO.setSpots(recommendations);
        }
        
        return categoryDTO;
    }
    
    
    
}