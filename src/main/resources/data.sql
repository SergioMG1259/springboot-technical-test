-- ===========================
-- TABLA SPECIALTY
-- ===========================
INSERT INTO specialty (id, name, created_at, updated_at) VALUES
                                                             (1, 'Cardiology', NOW(), NULL),
                                                             (2, 'Dermatology', NOW(), NULL),
                                                             (3, 'Neurology', NOW(), NULL),
                                                             (4, 'Pediatrics', NOW(), NULL),
                                                             (5, 'Orthopedics', NOW(), NULL),
                                                             (6, 'General Medicine', NOW(), NULL);

-- ===========================
-- TABLA PERSON
-- ===========================
INSERT INTO person (id, name, surname, email, birthday, created_at, updated_at) VALUES
                                                                                    (1, 'Alice', 'Smith', 'alice.smith@example.com', '1980-05-10T00:00:00', NOW(), NULL),
                                                                                    (2, 'Bob', 'Johnson', 'bob.johnson@example.com', '1975-08-22T00:00:00', NOW(), NULL),
                                                                                    (3, 'Carol', 'Williams', 'carol.williams@example.com', '1988-11-15T00:00:00', NOW(), NULL),
                                                                                    (4, 'David', 'Brown', 'david.brown@example.com', '1990-03-02T00:00:00', NOW(), NULL),
                                                                                    (5, 'Eva', 'Davis', 'eva.davis@example.com', '1992-12-10T00:00:00', NOW(), NULL),
                                                                                    (6, 'Frank', 'Miller', 'frank.miller@example.com', '1985-07-30T00:00:00', NOW(), NULL),
                                                                                    (7, 'Grace', 'Wilson', 'grace.wilson@example.com', '1995-01-20T00:00:00', NOW(), NULL),
                                                                                    (8, 'Henry', 'Moore', 'henry.moore@example.com', '2000-04-25T00:00:00', NOW(), NULL),
                                                                                    (9, 'Ivy', 'Taylor', 'ivy.taylor@example.com', '1998-09-12T00:00:00', NOW(), NULL),
                                                                                    (10, 'Jack', 'Anderson', 'jack.anderson@example.com', '1982-06-18T00:00:00', NOW(), NULL),
                                                                                    (11, 'Kathy', 'Thomas', 'kathy.thomas@example.com', '1991-10-05T00:00:00', NOW(), NULL),
                                                                                    (12, 'Leo', 'Jackson', 'leo.jackson@example.com', '1993-02-14T00:00:00', NOW(), NULL),
                                                                                    (13, 'Mia', 'White', 'mia.white@example.com', '2001-08-03T00:00:00', NOW(), NULL),
                                                                                    (14, 'Nina', 'Harris', 'nina.harris@example.com', '1996-11-27T00:00:00', NOW(), NULL),
                                                                                    (15, 'Oscar', 'Martin', 'oscar.martin@example.com', '1987-05-08T00:00:00', NOW(), NULL);

-- ===========================
-- TABLA USER
-- ===========================
-- Contraseña cifrada con BCrypt de '123456789'
INSERT INTO app_user (id, user_name, password, person_id, created_at, updated_at) VALUES
                                                                                      (1, 'Alice19800510', '$2a$12$TFTt/q9U4xF/uCDK/YNunO3J95zEEDWObZC6KbMlmzwKBqfvVNxJa', 1, NOW(), NULL),
                                                                                      (2, 'Bob19750822', '$$2a$12$TFTt/q9U4xF/uCDK/YNunO3J95zEEDWObZC6KbMlmzwKBqfvVNxJa', 2, NOW(), NULL),
                                                                                      (3, 'Carol19881115', '$2a$12$TFTt/q9U4xF/uCDK/YNunO3J95zEEDWObZC6KbMlmzwKBqfvVNxJa', 3, NOW(), NULL),
                                                                                      (4, 'David19900302', '$2a$12$TFTt/q9U4xF/uCDK/YNunO3J95zEEDWObZC6KbMlmzwKBqfvVNxJa', 4, NOW(), NULL),
                                                                                      (5, 'Eva19921210', '$2a$12$TFTt/q9U4xF/uCDK/YNunO3J95zEEDWObZC6KbMlmzwKBqfvVNxJa', 5, NOW(), NULL),
                                                                                      (6, 'Frank19850730', '$2a$12$TFTt/q9U4xF/uCDK/YNunO3J95zEEDWObZC6KbMlmzwKBqfvVNxJa', 6, NOW(), NULL),
                                                                                      (7, 'Grace19950120', '$2a$12$TFTt/q9U4xF/uCDK/YNunO3J95zEEDWObZC6KbMlmzwKBqfvVNxJa', 7, NOW(), NULL),
                                                                                      (8, 'Henry20000425', '$2a$12$TFTt/q9U4xF/uCDK/YNunO3J95zEEDWObZC6KbMlmzwKBqfvVNxJa', 8, NOW(), NULL),
                                                                                      (9, 'Ivy19980912', '$2a$12$TFTt/q9U4xF/uCDK/YNunO3J95zEEDWObZC6KbMlmzwKBqfvVNxJa', 9, NOW(), NULL),
                                                                                      (10, 'Jack19820618', '$2a$12$TFTt/q9U4xF/uCDK/YNunO3J95zEEDWObZC6KbMlmzwKBqfvVNxJa', 10, NOW(), NULL),
                                                                                      (11, 'Kathy19911005', '$2a$12$TFTt/q9U4xF/uCDK/YNunO3J95zEEDWObZC6KbMlmzwKBqfvVNxJa', 11, NOW(), NULL),
                                                                                      (12, 'Leo19930214', '$2a$12$TFTt/q9U4xF/uCDK/YNunO3J95zEEDWObZC6KbMlmzwKBqfvVNxJa', 12, NOW(), NULL),
                                                                                      (13, 'Mia20010803', '$2a$12$TFTt/q9U4xF/uCDK/YNunO3J95zEEDWObZC6KbMlmzwKBqfvVNxJa', 13, NOW(), NULL),
                                                                                      (14, 'Nina19961127', '$2a$12$TFTt/q9U4xF/uCDK/YNunO3J95zEEDWObZC6KbMlmzwKBqfvVNxJa', 14, NOW(), NULL),
                                                                                      (15, 'Oscar19870508', '$2a$12$TFTt/q9U4xF/uCDK/YNunO3J95zEEDWObZC6KbMlmzwKBqfvVNxJa', 15, NOW(), NULL);

-- ===========================
-- TABLA EMPLOYEE
-- ===========================
INSERT INTO employee (id, role, person_id) VALUES
                                               (1, 'ADMIN', 1),
                                               (2, 'ADMIN', 2),
                                               (3, 'DOCTOR', 3),
                                               (4, 'DOCTOR', 4),
                                               (5, 'DOCTOR', 5),
                                               (6, 'DOCTOR', 6);

-- ===========================
-- TABLA PATIENT
-- ===========================
INSERT INTO patient (id, role, person_id) VALUES
                                              (1, 'PATIENT', 7),
                                              (2, 'PATIENT', 8),
                                              (3, 'PATIENT', 9),
                                              (4, 'PATIENT', 10),
                                              (5, 'PATIENT', 11),
                                              (6, 'PATIENT', 12),
                                              (7, 'PATIENT', 13),
                                              (8, 'PATIENT', 14),
                                              (9, 'PATIENT', 15);

-- ===========================
-- TABLA DOCTOR_SPECIALTY
-- ===========================
INSERT INTO doctor_specialty (employee_id, specialty_id) VALUES
                                                             (3, 1), (3, 6),
                                                             (4, 2),
                                                             (5, 3), (5, 4),
                                                             (6, 5);

-- ===========================
-- TABLA ATTENTION
-- ===========================
-- Ejemplo de citas aleatorias futuras/pasadas sin solapamiento (duración 30-60 min)
INSERT INTO attention (id, start_date, end_date, reason, patient_id, employee_id, created_at, updated_at) VALUES
                                                                                                              (1, '2025-09-28T09:00:00', '2025-09-28T09:45:00', 'General check-up', 1, 3, NOW(), NULL),
                                                                                                              (2, '2025-09-28T10:00:00', '2025-09-28T10:30:00', 'Skin rash', 2, 4, NOW(), NULL),
                                                                                                              (3, '2025-09-29T11:00:00', '2025-09-29T11:50:00', 'Headache', 3, 5, NOW(), NULL),
                                                                                                              (4, '2025-09-30T14:00:00', '2025-09-30T14:45:00', 'Follow-up', 4, 6, NOW(), NULL),
                                                                                                              (5, '2025-09-25T15:00:00', '2025-09-25T15:40:00', 'Flu symptoms', 5, 3, NOW(), NULL),
                                                                                                              (6, '2025-09-26T09:30:00', '2025-09-26T10:15:00', 'Back pain', 6, 4, NOW(), NULL),
                                                                                                              (7, '2025-09-27T12:00:00', '2025-09-27T12:45:00', 'Routine check', 7, 5, NOW(), NULL),
                                                                                                              (8, '2025-09-28T13:00:00', '2025-09-28T13:35:00', 'Cold symptoms', 8, 6, NOW(), NULL),
                                                                                                              (9, '2025-09-29T15:00:00', '2025-09-29T15:50:00', 'Vaccination', 9, 3, NOW(), NULL),
                                                                                                              (10, '2025-09-30T16:00:00', '2025-09-30T16:40:00', 'Consultation', 1, 4, NOW(), NULL),
                                                                                                              (11, '2025-10-01T09:00:00', '2025-10-01T09:50:00', 'Follow-up', 2, 5, NOW(), NULL),
                                                                                                              (12, '2025-10-02T11:00:00', '2025-10-02T11:30:00', 'Routine check', 3, 6, NOW(), NULL);