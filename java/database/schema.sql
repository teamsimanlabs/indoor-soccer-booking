BEGIN TRANSACTION;

-- Users Table
DROP TABLE IF EXISTS users;

CREATE TABLE users (
     user_id SERIAL PRIMARY KEY,
     username VARCHAR(50) NOT NULL UNIQUE,
     password_hash VARCHAR(200) NOT NULL,
     role VARCHAR(50) NOT NULL, -- Roles: admin, staff, customer
     email VARCHAR(100) NOT NULL UNIQUE,
     email_verified BOOLEAN DEFAULT FALSE,
     phone_number VARCHAR(15),
     additional_contact_info JSON,
     preferences JSON,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Fields Table
DROP TABLE IF EXISTS fields;

CREATE TABLE fields (
    field_id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description TEXT,
    location VARCHAR(100),
    latitude DECIMAL(9, 6),
    longitude DECIMAL(9, 6),
    images JSON,
    status VARCHAR(20) DEFAULT 'available', -- Status: available, maintenance, booked
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Reservations Table
DROP TABLE IF EXISTS reservations;

CREATE TABLE reservations (
    reservation_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    field_id INT NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    total_price DECIMAL(10, 2),
    discount DECIMAL(10, 2),
    special_requests TEXT,
    status VARCHAR(20) DEFAULT 'booked', -- Status: booked, canceled, completed
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FK_reservation_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT FK_reservation_field FOREIGN KEY (field_id) REFERENCES fields(field_id)
);

-- Payments Table
DROP TABLE IF EXISTS payments;

CREATE TABLE payments (
    payment_id SERIAL PRIMARY KEY,
    reservation_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(50) NOT NULL, -- Payment methods: card, paypal, cash
    transaction_id VARCHAR(100),
    payment_metadata JSON,
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'completed', -- Status: completed, pending, failed
    CONSTRAINT FK_payment_reservation FOREIGN KEY (reservation_id) REFERENCES reservations(reservation_id)
);

-- Notifications Table
DROP TABLE IF EXISTS notifications;

CREATE TABLE notifications (
    notification_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    message TEXT NOT NULL,
    type VARCHAR(50), -- e.g., booking, payment, general
    priority VARCHAR(20) DEFAULT 'normal', -- e.g., normal, high
    notification_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'unread', -- Status: unread, read
    CONSTRAINT FK_notification_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Reports Table
DROP TABLE IF EXISTS reports;

CREATE TABLE reports (
   report_id SERIAL PRIMARY KEY,
   user_id INT NOT NULL,
   report_type VARCHAR(50) NOT NULL, -- Types: usage, financial, maintenance
   report_data JSON,
   period_start DATE,
   period_end DATE,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   CONSTRAINT FK_report_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Schedules Table
DROP TABLE IF EXISTS schedules;

CREATE TABLE schedules (
   schedule_id SERIAL PRIMARY KEY,
   field_id INT NOT NULL,
   date DATE NOT NULL,
   time_slot VARCHAR(50) NOT NULL, -- e.g., '08:00-09:00'
   status VARCHAR(20) DEFAULT 'available', -- Status: available, booked, maintenance
   CONSTRAINT FK_schedule_field FOREIGN KEY (field_id) REFERENCES fields(field_id)
);

-- Reviews Table
DROP TABLE IF EXISTS reviews;

CREATE TABLE reviews (
   review_id SERIAL PRIMARY KEY,
   user_id INT NOT NULL,
   field_id INT NOT NULL,
   rating INT CHECK (rating >= 1 AND rating <= 5),
   comment TEXT,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   CONSTRAINT FK_review_user FOREIGN KEY (user_id) REFERENCES users(user_id),
   CONSTRAINT FK_review_field FOREIGN KEY (field_id) REFERENCES fields(field_id)
);

-- Inventory Table
DROP TABLE IF EXISTS inventory;

CREATE TABLE inventory (
   item_id SERIAL PRIMARY KEY,
   item_name VARCHAR(100) NOT NULL,
   quantity INT NOT NULL,
   status VARCHAR(20) DEFAULT 'available', -- Status: available, out_of_stock, ordered
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Maintenance Logs Table
DROP TABLE IF EXISTS maintenance_logs;

CREATE TABLE maintenance_logs (
    log_id SERIAL PRIMARY KEY,
    field_id INT NOT NULL,
    description TEXT,
    maintenance_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FK_maintenance_log_field FOREIGN KEY (field_id) REFERENCES fields(field_id)
);

-- Staff Schedule Table
DROP TABLE IF EXISTS staff_schedule;

CREATE TABLE staff_schedule (
    schedule_id SERIAL PRIMARY KEY,
    staff_id INT NOT NULL,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    role VARCHAR(50) NOT NULL, -- Roles: manager, cleaner, coach
    status VARCHAR(20) DEFAULT 'scheduled', -- Status: scheduled, completed, canceled
    recurring BOOLEAN DEFAULT FALSE,
    CONSTRAINT FK_staff_schedule_staff FOREIGN KEY (staff_id) REFERENCES users(user_id)
);

-- Customer Feedback Table
DROP TABLE IF EXISTS feedback;

CREATE TABLE feedback (
   feedback_id SERIAL PRIMARY KEY,
   user_id INT NOT NULL,
   message TEXT,
   feedback_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   status VARCHAR(20) DEFAULT 'new', -- Status: new, reviewed
   CONSTRAINT FK_feedback_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

COMMIT TRANSACTION;
