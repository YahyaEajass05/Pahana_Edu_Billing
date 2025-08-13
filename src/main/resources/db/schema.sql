CREATE TABLE accounts (
                          account_id VARCHAR(36) PRIMARY KEY,
                          username VARCHAR(50) UNIQUE NOT NULL,
                          password VARCHAR(100) NOT NULL,
                          role VARCHAR(20) NOT NULL,
                          created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);