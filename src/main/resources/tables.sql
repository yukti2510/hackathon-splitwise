--Create user table
CREATE TABLE splitwise.user (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
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

--Create group table
CREATE TABLE splitwise.user_group (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    uuid CHAR(40) NOT NULL UNIQUE,
    name VARCHAR(256) NOT NULL,
    type VARCHAR(256),
    metadata JSON,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by CHAR(40),
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by CHAR(40),
    status BIT(1) DEFAULT 1
);

--Create expense table
CREATE TABLE splitwise.expense (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    uuid CHAR(40) NOT NULL UNIQUE,
    name VARCHAR(256),
    type VARCHAR(256),
    group_id BIGINT NOT NULL,
    amount_paid DOUBLE NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by CHAR(40),
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by CHAR(40),
    status BIT(1) DEFAULT 1
);

--Create user_group_mapping table
CREATE TABLE splitwise.user_group_mapping (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    uuid CHAR(40) NOT NULL UNIQUE,
    group_id BIGINT NOT NULL,
    phone VARCHAR(256) NOT NULL,
    is_admin BOOLEAN DEFAULT FALSE,
    amount_paid DOUBLE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by CHAR(40),
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by CHAR(40),
    status BIT(1) DEFAULT 1
);

--Create user_balance table
CREATE TABLE splitwise.user_balance (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    group_id BIGINT NOT NULL,
    user_1 VARCHAR(256) NOT NULL,
    user_2 VARCHAR(256) NOT NULL,
    amount_paid DOUBLE NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by CHAR(40),
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by CHAR(40),
    status BIT(1) DEFAULT 1
);

--Create expense_breakup table
CREATE TABLE splitwise.expense_breakup (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    uuid CHAR(40) NOT NULL UNIQUE,
    expense_id BIGINT NOT NULL,
    payer_id VARCHAR(256) NOT NULL,
    ower_id VARCHAR(256) NOT NULL,
    amount DOUBLE NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_by CHAR(40),
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by CHAR(40),
    status BIT(1) DEFAULT 1
);