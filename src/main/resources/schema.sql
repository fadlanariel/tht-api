-- ===============================
-- USERS
-- ===============================
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ===============================
-- PROFILE_IMAGES
-- ===============================
CREATE TABLE IF NOT EXISTS profile_images (
    user_id BIGINT PRIMARY KEY,
    profile_image VARCHAR(255),
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_profile_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);


-- ===============================
-- BALANCES
-- ===============================
CREATE TABLE IF NOT EXISTS balances (
    user_id BIGINT PRIMARY KEY,
    balance BIGINT NOT NULL DEFAULT 0,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_balance_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

-- ===============================
-- TRANSACTIONS
-- ===============================
CREATE TABLE IF NOT EXISTS transactions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    amount BIGINT NOT NULL,
    type VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_transaction_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

-- ===============================
-- INDEXES
-- ===============================
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_transactions_user_id ON transactions(user_id);
CREATE INDEX IF NOT EXISTS idx_transactions_created_at ON transactions(created_at);

-- ===============================
-- BANNERS
-- ===============================
CREATE TABLE IF NOT EXISTS banners (
    id BIGSERIAL PRIMARY KEY,
    banner_name VARCHAR(100) NOT NULL UNIQUE,
    banner_image VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);

-- Insert default banners jika belum ada
INSERT INTO banners (banner_name, banner_image, description)
VALUES ('Banner 1', 'https://nutech-integrasi.app/dummy.jpg', 'Lerem Ipsum Dolor sit amet')
ON CONFLICT (banner_name) DO NOTHING;

INSERT INTO banners (banner_name, banner_image, description)
VALUES ('Banner 2', 'https://nutech-integrasi.app/dummy.jpg', 'Lerem Ipsum Dolor sit amet')
ON CONFLICT (banner_name) DO NOTHING;

INSERT INTO banners (banner_name, banner_image, description)
VALUES ('Banner 3', 'https://nutech-integrasi.app/dummy.jpg', 'Lerem Ipsum Dolor sit amet')
ON CONFLICT (banner_name) DO NOTHING;

INSERT INTO banners (banner_name, banner_image, description)
VALUES ('Banner 4', 'https://nutech-integrasi.app/dummy.jpg', 'Lerem Ipsum Dolor sit amet')
ON CONFLICT (banner_name) DO NOTHING;

INSERT INTO banners (banner_name, banner_image, description)
VALUES ('Banner 5', 'https://nutech-integrasi.app/dummy.jpg', 'Lerem Ipsum Dolor sit amet')
ON CONFLICT (banner_name) DO NOTHING;

INSERT INTO banners (banner_name, banner_image, description)
VALUES ('Banner 6', 'https://nutech-integrasi.app/dummy.jpg', 'Lerem Ipsum Dolor sit amet')
ON CONFLICT (banner_name) DO NOTHING;

-- ===============================
-- SERVICES
-- ===============================
CREATE TABLE IF NOT EXISTS services (
    service_code VARCHAR(50) PRIMARY KEY,
    service_name VARCHAR(100) NOT NULL,
    service_icon VARCHAR(255),
    service_tariff BIGINT NOT NULL
);

INSERT INTO services(service_code, service_name, service_icon, service_tariff) VALUES
('PAJAK', 'Pajak PBB', 'https://nutech-integrasi.app/dummy.jpg', 40000)
ON CONFLICT (service_code) DO NOTHING;

INSERT INTO services(service_code, service_name, service_icon, service_tariff) VALUES
('PLN', 'Listrik', 'https://nutech-integrasi.app/dummy.jpg', 10000)
ON CONFLICT (service_code) DO NOTHING;

INSERT INTO services(service_code, service_name, service_icon, service_tariff) VALUES
('PDAM', 'PDAM Berlangganan', 'https://nutech-integrasi.app/dummy.jpg', 40000)
ON CONFLICT (service_code) DO NOTHING;

INSERT INTO services(service_code, service_name, service_icon, service_tariff) VALUES
('PULSA', 'Pulsa', 'https://nutech-integrasi.app/dummy.jpg', 40000)
ON CONFLICT (service_code) DO NOTHING;

INSERT INTO services(service_code, service_name, service_icon, service_tariff) VALUES
('PGN', 'PGN Berlangganan', 'https://nutech-integrasi.app/dummy.jpg', 50000)
ON CONFLICT (service_code) DO NOTHING;

INSERT INTO services(service_code, service_name, service_icon, service_tariff) VALUES
('MUSIK', 'Musik Berlangganan', 'https://nutech-integrasi.app/dummy.jpg', 50000)
ON CONFLICT (service_code) DO NOTHING;

INSERT INTO services(service_code, service_name, service_icon, service_tariff) VALUES
('TV', 'TV Berlangganan', 'https://nutech-integrasi.app/dummy.jpg', 50000)
ON CONFLICT (service_code) DO NOTHING;

INSERT INTO services(service_code, service_name, service_icon, service_tariff) VALUES
('PAKET_DATA', 'Paket data', 'https://nutech-integrasi.app/dummy.jpg', 50000)
ON CONFLICT (service_code) DO NOTHING;

INSERT INTO services(service_code, service_name, service_icon, service_tariff) VALUES
('VOUCHER_GAME', 'Voucher Game', 'https://nutech-integrasi.app/dummy.jpg', 100000)
ON CONFLICT (service_code) DO NOTHING;

INSERT INTO services(service_code, service_name, service_icon, service_tariff) VALUES
('VOUCHER_MAKANAN', 'Voucher Makanan', 'https://nutech-integrasi.app/dummy.jpg', 100000)
ON CONFLICT (service_code) DO NOTHING;

INSERT INTO services(service_code, service_name, service_icon, service_tariff) VALUES
('QURBAN', 'Qurban', 'https://nutech-integrasi.app/dummy.jpg', 200000)
ON CONFLICT (service_code) DO NOTHING;

INSERT INTO services(service_code, service_name, service_icon, service_tariff) VALUES
('ZAKAT', 'Zakat', 'https://nutech-integrasi.app/dummy.jpg', 300000)
ON CONFLICT (service_code) DO NOTHING;