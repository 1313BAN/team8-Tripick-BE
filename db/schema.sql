USE ssafytrip;

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



CREATE TABLE IF NOT EXISTS spot_like (
    spot_id INT,
    user_id INT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (spot_id, user_id),
+    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (spot_id) REFERENCES ssafytrip.attractions(no)
);