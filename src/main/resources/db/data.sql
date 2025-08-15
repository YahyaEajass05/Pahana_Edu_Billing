-- Sample data for PahanaEDU application
-- H2 Database Syntax

-- Enable foreign key constraints
SET REFERENTIAL_INTEGRITY FALSE;

-- Clear existing data (optional, for clean reset)
DELETE FROM bill_items;
DELETE FROM bills;
DELETE FROM items;
DELETE FROM customer_accounts;
DELETE FROM accounts;

-- Reset sequences if using auto-increment (not needed for UUID primary keys)
-- ALTER TABLE accounts ALTER COLUMN account_id RESTART WITH 1;

-- Insert sample accounts (passwords should be hashed in production)
INSERT INTO accounts (account_id, username, password, role, active, created_date) VALUES
                                                                                      ('a1001', 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MrYV5YZbH.8pJ3E2fVBW7uZ5xQ9S1q2', 'ADMIN', TRUE, '2023-01-01 09:00:00'), -- password: admin123
                                                                                      ('a1002', 'manager1', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MrYV5YZbH.8pJ3E2fVBW7uZ5xQ9S1q2', 'MANAGER', TRUE, '2023-01-02 10:00:00'), -- password: manager123
                                                                                      ('a1003', 'staff1', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MrYV5YZbH.8pJ3E2fVBW7uZ5xQ9S1q2', 'STAFF', TRUE, '2023-01-03 11:00:00'), -- password: staff123
                                                                                      ('a1004', 'student1', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MrYV5YZbH.8pJ3E2fVBW7uZ5xQ9S1q2', 'STUDENT', TRUE, '2023-01-04 12:00:00'); -- password: student123

-- Insert sample customer accounts
INSERT INTO customer_accounts (customer_id, account_id, first_name, last_name, email, phone, address) VALUES
                                                                                                          ('c1001', 'a1004', 'John', 'Smith', 'john.smith@example.com', '+1234567890', '123 Main St, Anytown, USA'),
                                                                                                          ('c1002', NULL, 'Emily', 'Johnson', 'emily.j@example.com', '+1987654321', '456 Oak Ave, Somewhere, USA'),
                                                                                                          ('c1003', NULL, 'Michael', 'Williams', 'michael.w@example.com', '+1122334455', '789 Pine Rd, Nowhere, USA');

-- Insert sample items
INSERT INTO items (item_id, name, description, price, quantity, created_date, active) VALUES
                                                                                          ('i1001', 'Mathematics 101', 'Introductory Mathematics Textbook', 59.99, 100, '2023-01-10 09:00:00', TRUE),
                                                                                          ('i1002', 'Science Notebook', '200-page lab notebook', 12.50, 200, '2023-01-11 10:00:00', TRUE),
                                                                                          ('i1003', 'Chemistry Set', 'Basic chemistry lab equipment', 89.99, 30, '2023-01-12 11:00:00', TRUE),
                                                                                          ('i1004', 'Programming Guide', 'Java Programming Basics', 45.00, 75, '2023-01-13 12:00:00', TRUE),
                                                                                          ('i1005', 'History Book', 'World History Volume 1', 39.99, 50, '2023-01-14 13:00:00', FALSE);

-- Insert sample bills
INSERT INTO bills (bill_id, customer_id, bill_date, total_amount, status, notes) VALUES
                                                                                     ('b1001', 'c1001', '2023-02-01 14:00:00', 72.49, 'PAID', 'Paid via credit card'),
                                                                                     ('b1002', 'c1002', '2023-02-02 15:00:00', 102.49, 'PENDING', 'Awaiting payment'),
                                                                                     ('b1003', 'c1001', '2023-02-03 16:00:00', 134.99, 'PAID', 'Paid via bank transfer');

-- Insert sample bill items
INSERT INTO bill_items (bill_item_id, bill_id, item_id, quantity, unit_price) VALUES
                                                                                  ('bi1001', 'b1001', 'i1001', 1, 59.99),
                                                                                  ('bi1002', 'b1001', 'i1002', 1, 12.50),
                                                                                  ('bi1003', 'b1002', 'i1003', 1, 89.99),
                                                                                  ('bi1004', 'b1002', 'i1002', 1, 12.50),
                                                                                  ('bi1005', 'b1003', 'i1001', 1, 59.99),
                                                                                  ('bi1006', 'b1003', 'i1003', 1, 89.99),
                                                                                  ('bi1007', 'b1003', 'i1004', 1, 45.00);

-- Update last login for some accounts
UPDATE accounts SET last_login = '2023-03-01 09:30:00' WHERE username = 'admin';
UPDATE accounts SET last_login = '2023-03-02 10:15:00' WHERE username = 'manager1';

-- Enable foreign key constraints
SET REFERENTIAL_INTEGRITY TRUE;