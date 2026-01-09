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

