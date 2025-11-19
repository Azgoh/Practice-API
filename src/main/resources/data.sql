INSERT INTO users (
    username,
    email,
    password,
    role,
    enabled,
    verification_token,
    auth_provider
)
VALUES (
    'admin',
    'admin@example.com',
    '$2a$12$pCZbFA6MDLy2PL4EaQqih.hvdGM/wZ1Gue/fjhU.B2n24sxuW04ze', -- password: admin123
    'USER',
    true,
    NULL,
    'LOCAL'
);

-- USERS (password123)

INSERT INTO users (username, email, password, role, auth_provider, enabled, verification_token)
VALUES
('john_doe', 'john@example.com', '$2a$12$rgWiAVLqohu2E2jNOLCRe.JXvku3DC0VznCWYS1R9lCt2F0pSpR0C', 'USER', 'LOCAL', TRUE, NULL),
('jane_smith', 'jane@example.com', '$2a$12$rgWiAVLqohu2E2jNOLCRe.JXvku3DC0VznCWYS1R9lCt2F0pSpR0C', 'USER', 'LOCAL', TRUE, NULL),
('alice_miller', 'alice@example.com', '$2a$12$rgWiAVLqohu2E2jNOLCRe.JXvku3DC0VznCWYS1R9lCt2F0pSpR0C', 'PROFESSIONAL', 'LOCAL', TRUE, NULL),
('bob_jones', 'bob@example.com', '$2a$12$rgWiAVLqohu2E2jNOLCRe.JXvku3DC0VznCWYS1R9lCt2F0pSpR0C', 'PROFESSIONAL', 'LOCAL', TRUE, NULL);


-- PROFESSIONAL PROFILES

INSERT INTO professional_profiles (first_name, last_name, profession, location, description, phone, hourly_rate, user_id)
VALUES
('Alice', 'Miller', 'Plumber', 'New York', 'Experienced plumber', '1234567890', '50', (SELECT id FROM users WHERE username='alice_miller')),
('Bob', 'Jones', 'Electrician', 'Los Angeles', 'Certified electrician', '0987654321', '60', (SELECT id FROM users WHERE username='bob_jones'));


-- REVIEWS

INSERT INTO review_entity (professional_id, user_id, score, review, timestamp)
VALUES
((SELECT id FROM professional_profiles WHERE first_name='Alice'), (SELECT id FROM users WHERE username='john_doe'), 5, 'Great work, very professional!', NOW()),
((SELECT id FROM professional_profiles WHERE first_name='Bob'), (SELECT id FROM users WHERE username='jane_smith'), 4, 'Good service, arrived on time.', NOW());


-- Appointments
INSERT INTO appointments (user_id, professional_id, date, start_time, end_time, appointment_status)
VALUES
((SELECT id FROM users WHERE username='john_doe'),
 (SELECT id FROM professional_profiles WHERE first_name='Alice'),
 '2025-11-25', '07:00:00', '08:30:00', 0);

INSERT INTO appointments (user_id, professional_id, date, start_time, end_time, appointment_status)
VALUES
((SELECT id FROM users WHERE username='jane_smith'),
 (SELECT id FROM professional_profiles WHERE first_name='Bob'),
 '2025-11-26', '17:00:00', '18:00:00', 2);

INSERT INTO appointments (user_id, professional_id, date, start_time, end_time, appointment_status)
VALUES
((SELECT id FROM users WHERE username='john_doe'),
 (SELECT id FROM professional_profiles WHERE first_name='Bob'),
 '2025-11-25', '06:00:00', '07:00:00', 0);

INSERT INTO appointments (user_id, professional_id, date, start_time, end_time, appointment_status)
VALUES
((SELECT id FROM users WHERE username='jane_smith'),
 (SELECT id FROM professional_profiles WHERE first_name='Alice'),
 '2025-11-26', '18:00:00', '19:00:00', 0);


-- AVAILABILITIES

INSERT INTO availabilities (title, date, start_time, end_time, user_id, professional_id)
VALUES
-- Alice's slots
('Morning Slot', '2025-11-25', '09:00:00', '12:00:00',
    (SELECT id FROM users WHERE username='alice_miller'),
    (SELECT id FROM professional_profiles WHERE first_name='Alice')),
('Afternoon Slot', '2025-11-25', '13:00:00', '17:00:00',
    (SELECT id FROM users WHERE username='alice_miller'),
    (SELECT id FROM professional_profiles WHERE first_name='Alice')),
('Morning Slot', '2025-11-26', '09:00:00', '12:00:00',
    (SELECT id FROM users WHERE username='alice_miller'),
    (SELECT id FROM professional_profiles WHERE first_name='Alice')),
('Afternoon Slot', '2025-11-26', '13:00:00', '17:00:00',
    (SELECT id FROM users WHERE username='alice_miller'),
    (SELECT id FROM professional_profiles WHERE first_name='Alice')),

-- Bob's slots
('Morning Slot', '2025-11-25', '08:00:00', '11:00:00',
    (SELECT id FROM users WHERE username='bob_jones'),
    (SELECT id FROM professional_profiles WHERE first_name='Bob')),
('Afternoon Slot', '2025-11-25', '12:00:00', '16:00:00',
    (SELECT id FROM users WHERE username='bob_jones'),
    (SELECT id FROM professional_profiles WHERE first_name='Bob')),
('Morning Slot', '2025-11-26', '08:00:00', '11:00:00',
    (SELECT id FROM users WHERE username='bob_jones'),
    (SELECT id FROM professional_profiles WHERE first_name='Bob')),
('Afternoon Slot', '2025-11-26', '12:00:00', '16:00:00',
    (SELECT id FROM users WHERE username='bob_jones'),
    (SELECT id FROM professional_profiles WHERE first_name='Bob'));
