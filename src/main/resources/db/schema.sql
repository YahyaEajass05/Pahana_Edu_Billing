CREATE TABLE accounts (
                          account_id VARCHAR(36) PRIMARY KEY,
                          username VARCHAR(50) UNIQUE NOT NULL,
                          password_hash VARCHAR(255) NOT NULL,
                          role VARCHAR(20) NOT NULL,
                          active BOOLEAN DEFAULT TRUE,
                          created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          last_login TIMESTAMP,
                          last_failed_login TIMESTAMP,
                          failed_attempts INT DEFAULT 0,
                          locked BOOLEAN DEFAULT FALSE,
                          lock_until TIMESTAMP NULL
);

CREATE TABLE login_attempts (
                                attempt_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                account_id VARCHAR(36) NOT NULL,
                                attempt_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                ip_address VARCHAR(45),
                                user_agent VARCHAR(255),
                                successful BOOLEAN NOT NULL,
                                FOREIGN KEY (account_id) REFERENCES accounts(account_id)
);