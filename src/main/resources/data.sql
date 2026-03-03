-- ─── ROLE ──────────────────────────────────────────────────────
INSERT INTO role (id, name)
VALUES (1, 'ADMIN'),
       (2, 'OWNER'),
       (3, 'TENANT')
ON CONFLICT DO NOTHING;

SELECT setval('role_id_seq', (SELECT MAX(id) FROM role));

-- ─── APP_USER ──────────────────────────────────────────────────
INSERT INTO app_user (id, active, created_at, role_id, email, first_name, last_name, password, phone_number)
VALUES (1, true, '2026-02-27', 1, 'Admin@admin.com', 'Lorenzo', 'Admin',
        '$2a$10$b8iwgjzoue5J.RDyK6BMIujia/dKbAw2S1Uvv8cix7YENv2sqt9kO', '3339876534'),
       (2, true, '2026-02-27', 2, 'Owner@owner.com', 'Lorenzo', 'Owner',
        '$2a$10$60eM9bBvR5R6D4.dAmBH6uff4GNjiT0N90iWm7R1FNABMdv5Llyle', '3339876535'),
       (3, true, '2026-02-27', 3, 'Tenant@tenant.com', 'Lorenzo', 'Tenant',
        '$2a$12$alF8Q1funjC4PoCzO5ijFuFG2ffqSbMcCXkRpkiLx7ptQb8sSA1za', '3339876536')
ON CONFLICT DO NOTHING;

SELECT setval('app_user_id_seq', (SELECT MAX(id) FROM app_user));

-- ─── PROPERTY ──────────────────────────────────────────────────
INSERT INTO property (id, active, price_per_month, rooms, address, city, name, created_at, updated_at, owner_id)
VALUES (1, true, 800.00, 3, 'Via Roma 10', 'Roma', 'Appartamento Centro', '2026-02-23', null, 2),
       (2, true, 100.00, 2, 'via del pincio', 'Roma', 'prova', '2026-02-19', '2026-02-24', 2),
       (3, true, 700.00, 1, 'Via Ascoli Piceno 23', 'Roma', 'Mini house', '2026-02-26', null, 2),
       (4, true, 600.00, 2, 'Via Ascoli Piceno 32, int 9', 'Roma', 'Casa rossa', '2026-02-23', null, 2)
ON CONFLICT DO NOTHING;

SELECT setval('property_id_seq', (SELECT MAX(id) FROM property));

-- ─── RENTAL ────────────────────────────────────────────────────
INSERT INTO rental (id, active, end_date, start_date, total_price, tenant_id, property_id)
VALUES (1, true, '2030-03-29', '2026-02-23', 35000.00, 3, 3),
       (2, true, '2026-03-31', '2026-03-01', 99.90, 3, 2)
ON CONFLICT DO NOTHING;

SELECT setval('rental_id_seq', (SELECT MAX(id) FROM rental));