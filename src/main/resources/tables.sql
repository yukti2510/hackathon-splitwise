--Create user table
CREATE TABLE splitwise.user (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    uuid CHAR(40) NOT NULL UNIQUE,
    name VARCHAR(256),
    phone VARCHAR(256) NOT NULL,
    jupiter_user_id VARCHAR(100),
    metadata JSON,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by CHAR(40),
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by CHAR(40),
    status BIT(1) DEFAULT 1
);

