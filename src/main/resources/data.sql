-- Medical Conditions Lookup Data
INSERT INTO medical_condition_lookup (name, description, is_active, display_order) VALUES
('Diabetes', 'Diabetes mellitus condition', true, 1),
('Hypertension', 'High blood pressure condition', true, 2),
('Heart Disease', 'Cardiovascular disease', true, 3),
('Asthma / Breathing Issues', 'Respiratory conditions including asthma', true, 4),
('Thyroid', 'Thyroid related conditions', true, 5),
('Other', 'Other medical conditions not listed above', true, 6)
ON DUPLICATE KEY UPDATE name = VALUES(name);

-- Sample Addresses
INSERT INTO addresses (id, state_id, state, district_id, district, mandal_id, mandal, city_village, street_address, pin_code, created_at, updated_at) VALUES
(1, 1, 'Telangana', 1, 'Hyderabad', 1, 'Secunderabad', 'Secunderabad', '123 Main Street, Near Railway Station', '500003', NOW(), NOW()),
(2, 1, 'Telangana', 2, 'Rangareddy', 2, 'Shamshabad', 'Shamshabad', '456 Airport Road, Gandhi Nagar', '501218', NOW(), NOW())
ON DUPLICATE KEY UPDATE id = VALUES(id);

-- Sample Patients
INSERT INTO patients (id, patient_id, first_name, last_name, father_spouse_name, gender, age, marital_status, phone_number, photo_url, address_id, has_medical_history, payment_type, payment_percentage, created_at, updated_at) VALUES
(1, 'PAT-20260219-00001', 'Ramesh', 'Kumar', 'Suresh Kumar', 'MALE', 45, 'MARRIED', '9876543210', NULL, 1, true, 'PAID', 50, NOW(), NOW()),
(2, 'PAT-20260219-00002', 'Lakshmi', 'Devi', 'Venkat Rao', 'FEMALE', 32, 'MARRIED', '9876543211', NULL, 2, false, 'FREE', NULL, NOW(), NOW())
ON DUPLICATE KEY UPDATE id = VALUES(id);

-- Sample Medical History for Patient 1
INSERT INTO patient_original_history (id, previous_hospital_name, current_medications, past_surgery_major_illness, created_at, updated_at) VALUES
(1, 'Apollo Hospital', 'Metformin 500mg, Amlodipine 5mg', 'Appendix surgery in 2015', NOW(), NOW())
ON DUPLICATE KEY UPDATE id = VALUES(id);

-- Update Patient 1 with medical history reference
UPDATE patients SET medical_history_id = 1 WHERE id = 1;

-- Link Patient 1's conditions (Diabetes and Hypertension)
INSERT INTO patient_medical_conditions (patient_history_id, condition_id, other_details) VALUES
(1, 1, NULL),
(1, 2, NULL)
ON DUPLICATE KEY UPDATE patient_history_id = VALUES(patient_history_id);
