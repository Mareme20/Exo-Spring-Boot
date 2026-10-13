-- ============================================================
-- Initialisation de la base de donnees avec 10 utilisateurs
-- Chaque utilisateur possede 1 portefeuille et des transactions
-- ============================================================
-- Note : Les tables sont creees par Hibernate (ddl-auto: update)
-- Ce script s'execute APRES la creation des tables
-- Les mots de passe sont encodes en BCrypt (mot de passe : "123456")
-- ============================================================

-- ==================== UTILISATEURS ====================
INSERT INTO users (nom, email, password, date_creation, date_mise_a_jour) VALUES
('Marieme Diop',   'marieme.diop@email.com',   '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', NOW(), NOW()),
('Amadou Fall',    'amadou.fall@email.com',    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', NOW(), NOW()),
('Fatou Sow',      'fatou.sow@email.com',      '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', NOW(), NOW()),
('Ousmane Ndiaye', 'ousmane.ndiaye@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', NOW(), NOW()),
('Aicha Barry',    'aicha.barry@email.com',    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', NOW(), NOW()),
('Cheikh Diallo',  'cheikh.diallo@email.com',  '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', NOW(), NOW()),
('Ndeye Ba',       'ndeye.ba@email.com',       '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', NOW(), NOW()),
('Ibrahima Kane',  'ibrahima.kane@email.com',  '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', NOW(), NOW()),
('Khady Gueye',    'khady.gueye@email.com',    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', NOW(), NOW()),
('Modou Thioune',  'modou.thioune@email.com',  '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', NOW(), NOW())
ON CONFLICT (email) DO NOTHING;

-- ==================== PORTEFEUILLES ====================
INSERT INTO wallets (solde, devise, utilisateur_id, date_creation, date_mise_a_jour) VALUES
(150000.0000, 'XOF', 1, NOW(), NOW()),
(75000.0000,  'XOF', 2, NOW(), NOW()),
(5000.0000,   'EUR', 3, NOW(), NOW()),
(320000.0000, 'XOF', 4, NOW(), NOW()),
(25000.0000,  'EUR', 5, NOW(), NOW()),
(1000000.0000,'XOF', 6, NOW(), NOW()),
(12000.0000,  'EUR', 7, NOW(), NOW()),
(450000.0000, 'XOF', 8, NOW(), NOW()),
(85000.0000,  'XOF', 9, NOW(), NOW()),
(2500.0000,   'EUR', 10, NOW(), NOW());

-- ==================== TRANSACTIONS ====================
-- Portefeuille 1: Marieme Diop
INSERT INTO transactions (type, montant, date_transaction, statut, wallet_id, date_creation, date_mise_a_jour) VALUES
('DEPOT',  200000.0000, NOW() - INTERVAL '30 days', 'VALIDEE', 1, NOW(), NOW()),
('RETRAIT', 50000.0000, NOW() - INTERVAL '15 days', 'VALIDEE', 1, NOW(), NOW());

-- Portefeuille 2: Amadou Fall
INSERT INTO transactions (type, montant, date_transaction, statut, wallet_id, date_creation, date_mise_a_jour) VALUES
('DEPOT',  100000.0000, NOW() - INTERVAL '20 days', 'VALIDEE', 2, NOW(), NOW()),
('RETRAIT', 25000.0000, NOW() - INTERVAL '10 days', 'VALIDEE', 2, NOW(), NOW());

-- Portefeuille 3: Fatou Sow
INSERT INTO transactions (type, montant, date_transaction, statut, wallet_id, date_creation, date_mise_a_jour) VALUES
('DEPOT', 5000.0000, NOW() - INTERVAL '5 days', 'VALIDEE', 3, NOW(), NOW());

-- Portefeuille 4: Ousmane Ndiaye
INSERT INTO transactions (type, montant, date_transaction, statut, wallet_id, date_creation, date_mise_a_jour) VALUES
('DEPOT',  350000.0000, NOW() - INTERVAL '60 days', 'VALIDEE', 4, NOW(), NOW()),
('RETRAIT', 30000.0000, NOW() - INTERVAL '40 days', 'VALIDEE', 4, NOW(), NOW());

-- Portefeuille 5: Aicha Barry
INSERT INTO transactions (type, montant, date_transaction, statut, wallet_id, date_creation, date_mise_a_jour) VALUES
('DEPOT',  30000.0000, NOW() - INTERVAL '25 days', 'VALIDEE', 5, NOW(), NOW()),
('RETRAIT', 5000.0000, NOW() - INTERVAL '12 days', 'VALIDEE', 5, NOW(), NOW());

-- Portefeuille 6: Cheikh Diallo
INSERT INTO transactions (type, montant, date_transaction, statut, wallet_id, date_creation, date_mise_a_jour) VALUES
('DEPOT', 1000000.0000, NOW() - INTERVAL '90 days', 'VALIDEE', 6, NOW(), NOW());

-- Portefeuille 7: Ndeye Ba
INSERT INTO transactions (type, montant, date_transaction, statut, wallet_id, date_creation, date_mise_a_jour) VALUES
('DEPOT',  15000.0000, NOW() - INTERVAL '7 days', 'VALIDEE', 7, NOW(), NOW()),
('RETRAIT', 3000.0000, NOW() - INTERVAL '2 days', 'VALIDEE', 7, NOW(), NOW());

-- Portefeuille 8: Ibrahima Kane
INSERT INTO transactions (type, montant, date_transaction, statut, wallet_id, date_creation, date_mise_a_jour) VALUES
('DEPOT',  500000.0000, NOW() - INTERVAL '45 days', 'VALIDEE', 8, NOW(), NOW()),
('RETRAIT', 50000.0000, NOW() - INTERVAL '20 days', 'VALIDEE', 8, NOW(), NOW());

-- Portefeuille 9: Khady Gueye
INSERT INTO transactions (type, montant, date_transaction, statut, wallet_id, date_creation, date_mise_a_jour) VALUES
('DEPOT',  100000.0000, NOW() - INTERVAL '10 days', 'VALIDEE', 9, NOW(), NOW()),
('RETRAIT', 15000.0000, NOW() - INTERVAL '3 days',  'VALIDEE', 9, NOW(), NOW());

-- Portefeuille 10: Modou Thioune
INSERT INTO transactions (type, montant, date_transaction, statut, wallet_id, date_creation, date_mise_a_jour) VALUES
('DEPOT', 2500.0000, NOW() - INTERVAL '1 day', 'VALIDEE', 10, NOW(), NOW());
