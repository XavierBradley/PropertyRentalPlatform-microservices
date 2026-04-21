/* =========================
   TABLES
   ========================= */


-- 🔥 IMPORTANT: comes AFTER all referenced tables
CREATE TABLE IF NOT EXISTS rentals (
    id           Int PRIMARY KEY,
    rent  		 Decimal(10, 2) NOT NULL,
    expiry       DATE NOT NULL,
    status       VARCHAR(20) NOT NULL

);

/* =========================
   Checks
   ========================= */


ALTER TABLE rental
    ADD CONSTRAINT chk_status
        CHECK (status IN ('ACTIVE', 'EXPIRED'));

ALTER TABLE rental
    ADD CONSTRAINT chk_rent_positive
        CHECK (rent > 0);

ALTER TABLE rental
    ADD CONSTRAINT chk_expiry_future
        CHECK (expiry_date > CURRENT_DATE);

/* =========================
   INDEXES
   ========================= */


/* =========================
   SEED DATA
   ========================= */



INSERT INTO rentals (id, rent, expiry, status) VALUES
        ('00000000-0000-0000-0000-000000000301',
        1200.00, '2027-01-01', 'ACTIVE'),

        ('00000000-0000-0000-0000-000000000302',
        1500.00, '2027-06-01', 'ACTIVE'),

        ('00000000-0000-0000-0000-000000000303',
        900.00, '2026-12-31', 'EXPIRED');