BEGIN TRANSACTION;

-- Users Table
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    user_id SERIAL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(200) NOT NULL,
    role VARCHAR(50) NOT NULL, -- Roles: admin, staff, customer
    email VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(15),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PK_user PRIMARY KEY (user_id)
);

-- Fields Table
DROP TABLE IF EXISTS fields;

CREATE TABLE fields (
    field_id SERIAL,
    name VARCHAR(50) NOT NULL,
    description TEXT,
    location VARCHAR(100),
    status VARCHAR(20) DEFAULT 'available', -- Status: available, maintenance, booked
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PK_field PRIMARY KEY (field_id)
);

-- Reservations Table
DROP TABLE IF EXISTS reservations;

CREATE TABLE reservations (
    reservation_id SERIAL,
    user_id INT NOT NULL,
    field_id INT NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    status VARCHAR(20) DEFAULT 'booked', -- Status: booked, canceled, completed
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PK_reservation PRIMARY KEY (reservation_id),
    CONSTRAINT FK_reservation_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT FK_reservation_field FOREIGN KEY (field_id) REFERENCES fields(field_id)
);

-- Payments Table
DROP TABLE IF EXISTS payments;

CREATE TABLE payments (
   payment_id SERIAL,
   reservation_id INT NOT NULL,
   amount DECIMAL(10, 2) NOT NULL,
   payment_method VARCHAR(50) NOT NULL, -- Payment methods: card, paypal, cash
   payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   status VARCHAR(20) DEFAULT 'completed', -- Status: completed, pending, failed
   CONSTRAINT PK_payment PRIMARY KEY (payment_id),
   CONSTRAINT FK_payment_reservation FOREIGN KEY (reservation_id) REFERENCES reservations(reservation_id)
);

-- Notifications Table
DROP TABLE IF EXISTS notifications;

CREATE TABLE notifications (
   notification_id SERIAL,
   user_id INT NOT NULL,
   message TEXT NOT NULL,
   notification_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   status VARCHAR(20) DEFAULT 'unread', -- Status: unread, read
   CONSTRAINT PK_notification PRIMARY KEY (notification_id),
   CONSTRAINT FK_notification_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Reports Table
DROP TABLE IF EXISTS reports;

CREATE TABLE reports (
    report_id SERIAL,
    user_id INT NOT NULL,
    report_type VARCHAR(50) NOT NULL, -- Types: usage, financial, maintenance
    report_data JSON,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PK_report PRIMARY KEY (report_id),
    CONSTRAINT FK_report_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Schedules Table
DROP TABLE IF EXISTS schedules;

CREATE TABLE schedules (
        schedule_id SERIAL,
        field_id INT NOT NULL,
        date DATE NOT NULL,
        time_slot VARCHAR(50) NOT NULL, -- e.g., '08:00-09:00'
        status VARCHAR(20) DEFAULT 'available', -- Status: available, booked, maintenance
        CONSTRAINT PK_schedule PRIMARY KEY (schedule_id),
        CONSTRAINT FK_schedule_field FOREIGN KEY (field_id) REFERENCES fields(field_id)
);

-- Reviews Table
DROP TABLE IF EXISTS reviews;

CREATE TABLE reviews (
        review_id SERIAL,
        user_id INT NOT NULL,
        field_id INT NOT NULL,
        rating INT CHECK (rating >= 1 AND rating <= 5),
        comment TEXT,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        CONSTRAINT PK_review PRIMARY KEY (review_id),
        CONSTRAINT FK_review_user FOREIGN KEY (user_id) REFERENCES users(user_id),
        CONSTRAINT FK_review_field FOREIGN KEY (field_id) REFERENCES fields(field_id)
);

-- Inventory Table
DROP TABLE IF EXISTS inventory;

CREATE TABLE inventory (
        item_id SERIAL,
        item_name VARCHAR(100) NOT NULL,
        quantity INT NOT NULL,
        status VARCHAR(20) DEFAULT 'available', -- Status: available, out_of_stock, ordered
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        CONSTRAINT PK_inventory PRIMARY KEY (item_id)
);

-- Maintenance Logs Table
DROP TABLE IF EXISTS maintenance_logs;

CREATE TABLE maintenance_logs (
        log_id SERIAL,
        field_id INT NOT NULL,
        description TEXT,
        maintenance_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        CONSTRAINT PK_maintenance_log PRIMARY KEY (log_id),
        CONSTRAINT FK_maintenance_log_field FOREIGN KEY (field_id) REFERENCES fields(field_id)
);

-- Staff Schedule Table
DROP TABLE IF EXISTS staff_schedule;

CREATE TABLE staff_schedule (
        schedule_id SERIAL,
        staff_id INT NOT NULL,
        date DATE NOT NULL,
        start_time TIME NOT NULL,
        end_time TIME NOT NULL,
        role VARCHAR(50) NOT NULL, -- Roles: manager, cleaner, coach
        status VARCHAR(20) DEFAULT 'scheduled', -- Status: scheduled, completed, canceled
        CONSTRAINT PK_staff_schedule PRIMARY KEY (schedule_id),
        CONSTRAINT FK_staff_schedule_staff FOREIGN KEY (staff_id) REFERENCES users(user_id)
);

-- Customer Feedback Table
DROP TABLE IF EXISTS feedback;

CREATE TABLE feedback (
        feedback_id SERIAL,
        user_id INT NOT NULL,
        message TEXT,
        feedback_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        status VARCHAR(20) DEFAULT 'new', -- Status: new, reviewed
        CONSTRAINT PK_feedback PRIMARY KEY (feedback_id),
        CONSTRAINT FK_feedback_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

COMMIT TRANSACTION;
