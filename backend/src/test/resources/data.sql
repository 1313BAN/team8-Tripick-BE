
INSERT INTO accompany VALUES (1, '혼자'), (2, '가족');
INSERT INTO content_type VALUES (12, '관광지');
INSERT INTO sidos VALUES (11, '서울');
INSERT INTO guguns VALUES (11, 1, '종로구');
INSERT INTO motive VALUES (1, '휴양'), (2, '관광');

INSERT INTO users (id, nickname, email, gender, age, accompany_code, residence_sido_code)
VALUES (1000012, 'tester', 'test@example.com', '여성', 30, 1, 11);

INSERT INTO spots (no, title, content_type_id, area_code, si_gun_gu_code, latitude, longitude)
VALUES (1001, '경복궁', 12, 11, 1, 37.5796, 126.9770);
INSERT INTO spots (no, title, content_type_id, area_code, si_gun_gu_code, latitude, longitude)
VALUES (1, '테스트 관광지', 12, 11, 1, 37.0, 127.0);

INSERT INTO user_tralve_motive VALUES (1000012, 2);
INSERT INTO reviews (review_id, user_id, no, rating, title, content, review_like, created_at, modified_at)
VALUES (1, 1000012, 1001, 4.5, '정말 좋은 관광지', '설명이 길 필요는 없죠', 3, NOW(), NOW());

-- 테스트 관광지에도 리뷰 추가
INSERT INTO reviews (review_id, user_id, no, rating, title, content, review_like, created_at, modified_at)
VALUES (2, 1000012, 1, 4.0, '테스트 관광지도 괜찮아요', '평범하지만 나쁘지 않아요', 1, NOW(), NOW());
