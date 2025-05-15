USE ssafytrip;

/*
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nickname VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    profile_image VARCHAR(255),
    role ENUM('USER', 'ADMIN') DEFAULT 'USER',
    bio TEXT,
    gender ENUM('MALE', 'FEMALE'),
    age INT DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
*/

/* 유저 테이블 수정*/
CREATE TABLE `users` (
   `id` int NOT NULL AUTO_INCREMENT,
   `nickname` varchar(50) NOT NULL,
   `name` varchar(50) NOT NULL,
   `email` varchar(100) NOT NULL,
   `password` varchar(255) NOT NULL,
   `profile_image` varchar(255) DEFAULT NULL,
   `role` enum('USER','ADMIN') DEFAULT 'USER',
   `bio` text,
   `gender` enum('MALE','FEMALE') DEFAULT NULL,
   `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
   `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   `age` int DEFAULT NULL,
   `city_nature_prefer` int DEFAULT NULL COMMENT '도시 자연 선호도',
   `residence_sido_code` int DEFAULT NULL COMMENT '거주지 시도코드',
   `residence_gugun_code` int DEFAULT NULL COMMENT '거주지 구군코드',
   PRIMARY KEY (`id`),
   UNIQUE KEY `email` (`email`),
   KEY `users_sido_code_fk` (`residence_sido_code`),
   KEY `users_gugun_code_fk` (`residence_gugun_code`),
   CONSTRAINT `users_gugun_code_fk` FOREIGN KEY (`residence_gugun_code`) REFERENCES `guguns` (`gugun_code`),
   CONSTRAINT `users_sido_code_fk` FOREIGN KEY (`residence_sido_code`) REFERENCES `sidos` (`sido_code`)
 ) 
 

/*리뷰 테이블*/
CREATE TABLE `reviews` (
   `review_id` int NOT NULL AUTO_INCREMENT,
   `user_id` int NOT NULL,
   `rating` float DEFAULT NULL,
   `review_like` int DEFAULT '0',
   `content` varchar(1000) DEFAULT NULL,
   `title` varchar(255) DEFAULT NULL,
   `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
   `modified_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   `no` int DEFAULT NULL,
   `motive_code` int DEFAULT NULL COMMENT '동기 코드',
   `com_num` int DEFAULT NULL COMMENT '여행 동반자 수',
   PRIMARY KEY (`review_id`),
   KEY `user_id` (`user_id`),
   KEY `no` (`no`),
   KEY `fk_reviews_motive_code` (`motive_code`),
   CONSTRAINT `fk_reviews_motive_code` FOREIGN KEY (`motive_code`) REFERENCES `motive` (`motive_code`),
   CONSTRAINT `reviews_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
   CONSTRAINT `reviews_ibfk_2` FOREIGN KEY (`no`) REFERENCES `spots` (`no`)
 )

/* 동기 테이블 */
CREATE TABLE `motive` (
    `motive_id` INT NOT NULL AUTO_INCREMENT COMMENT '동기 id (기본키)',
    `motive_code` INT NOT NULL COMMENT '동기 코드',
    `motive_name` VARCHAR(255) NOT NULL COMMENT '동기 이름',
    PRIMARY KEY (`motive_id`)
);





 
 
 