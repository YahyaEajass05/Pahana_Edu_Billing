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

CREATE TABLE customer_accounts (
                                   customer_id VARCHAR(36) PRIMARY KEY,
                                   account_id VARCHAR(36) NOT NULL,
                                   first_name VARCHAR(50) NOT NULL,
                                   last_name VARCHAR(50) NOT NULL,
                                   email VARCHAR(100) NOT NULL,
                                   phone VARCHAR(20) NOT NULL,
                                   address TEXT,
                                   FOREIGN KEY (account_id) REFERENCES accounts(account_id)
);

CREATE INDEX idx_customer_email ON customer_accounts(email);

-- ITEMS TABLE
CREATE TABLE items (
                                     item_id INT AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(100) NOT NULL,
                                     description TEXT,
                                     price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
                                     stock_quantity INT NOT NULL DEFAULT 0 CHECK (stock_quantity >= 0),
                                     category VARCHAR(50),
                                     is_active BOOLEAN DEFAULT TRUE,
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- INDEXES FOR PERFORMANCE
CREATE INDEX IF NOT EXISTS idx_items_name ON items(name);
CREATE INDEX IF NOT EXISTS idx_items_category ON items(category);
CREATE INDEX IF NOT EXISTS idx_items_active ON items(is_active);

-- SAMPLE DATA FOR TESTING (OPTIONAL)
INSERT INTO items (name, description, price, stock_quantity, category, is_active)
VALUES
    ('Textbook: Java Programming', 'Comprehensive guide to Java programming', 49.99, 100, 'Books', TRUE),
    ('Laptop: Dell XPS 15', 'High performance developer laptop', 1299.99, 25, 'Electronics', TRUE),
    ('Notebook', 'College-ruled writing notebook', 4.99, 500, 'Supplies', TRUE),
    ('Pen Set', 'Set of 5 colored gel pens', 7.49, 300, 'Supplies', TRUE),
    ('Calculator: TI-84', 'Graphing calculator for advanced math', 89.99, 50, 'Electronics', TRUE);

-- VIEW FOR ACTIVE ITEMS
CREATE VIEW IF NOT EXISTS active_items AS
SELECT * FROM items WHERE is_active = TRUE;

-- VIEW FOR LOW STOCK ITEMS
CREATE VIEW IF NOT EXISTS low_stock_items AS
SELECT * FROM items WHERE stock_quantity < 10 AND is_active = TRUE;