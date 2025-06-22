
CREATE TABLE accompany (
  accompany_code int NOT NULL,
  accompany_label varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (accompany_code)
);

CREATE TABLE content_type (
  type_id int NOT NULL,
  type_name varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (type_id)
);

CREATE TABLE sidos (
  sido_code int NOT NULL,
  sido_name varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (sido_code)
);

CREATE TABLE guguns (
  sido_code int NOT NULL,
  gugun_code int NOT NULL,
  gugun_name varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (sido_code,gugun_code)
);

CREATE TABLE motive (
  motive_code int NOT NULL,
  motive_name varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (motive_code)
);

CREATE TABLE users (
  id int NOT NULL AUTO_INCREMENT,
  nickname varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  name varchar(50),
  email varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  password varchar(255),
  profile_image varchar(50),
  role varchar(50),
  bio varchar(50),
  gender varchar(50),
  age int DEFAULT NULL,
  created_at datetime DEFAULT NULL,
  updated_at datetime DEFAULT NULL,
  accompany_code int DEFAULT NULL,
  residence_sido_code int DEFAULT NULL,
  real_user tinyint(1) DEFAULT 1,
  PRIMARY KEY (id),
  UNIQUE KEY uk_users_email (email),
  FOREIGN KEY (accompany_code) REFERENCES accompany (accompany_code) ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (residence_sido_code) REFERENCES sidos (sido_code) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE spots (
  no int NOT NULL AUTO_INCREMENT,
  title varchar(50),
  content_type_id int DEFAULT NULL,
  area_code int DEFAULT NULL,
  si_gun_gu_code int DEFAULT NULL,
  first_image1 varchar(100),
  first_image2 varchar(100),
  latitude double DEFAULT NULL,
  longitude double DEFAULT NULL,
  addr varchar(100),
  homepage varchar(100),
  overview varchar(1000),
  PRIMARY KEY (no),
  FOREIGN KEY (content_type_id) REFERENCES content_type (type_id) ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (area_code) REFERENCES sidos (sido_code) ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (area_code, si_gun_gu_code) REFERENCES guguns (sido_code, gugun_code) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE reviews (
  review_id int NOT NULL AUTO_INCREMENT,
  no int DEFAULT NULL,
  user_id int DEFAULT NULL,
  rating double DEFAULT NULL,
  title varchar(50),
  content varchar(1000),
  review_like int NOT NULL,
  created_at datetime DEFAULT NULL,
  init_data int DEFAULT NULL,
  modified_at datetime DEFAULT NULL,
  PRIMARY KEY (review_id),
  FOREIGN KEY (no) REFERENCES spots (no) ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE user_tralve_motive (
  user_id int NOT NULL,
  motive_code int NOT NULL,
  PRIMARY KEY (user_id,motive_code),
  FOREIGN KEY (motive_code) REFERENCES motive (motive_code) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);
