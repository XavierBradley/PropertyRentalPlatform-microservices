/* =========================
   TABLES
   ========================= */


-- 🔥 IMPORTANT: comes AFTER all referenced tables
CREATE TABLE IF NOT EXISTS rentals (
    id           UUID PRIMARY KEY,
    property_id  UUID NOT NULL,
    owner_id     UUID NOT NULL,
    tenant_id    UUID NOT NULL,

    rent  		 Decimal(10, 2) NOT NULL,
    expiry       DATE NOT NULL,
    status       VARCHAR(20) NOT NULL


    );

/* =========================
   Checks
   ========================= */


ALTER TABLE rentals
    ADD CONSTRAINT chk_status
        CHECK (status IN ('ACTIVE', 'EXPIRED'));

ALTER TABLE rentals
    ADD CONSTRAINT chk_rent_positive
        CHECK (rent > 0);

ALTER TABLE rentals
    ADD CONSTRAINT chk_expiry_future
        CHECK (expiry > CURRENT_DATE);

/* =========================
   INDEXES
   ========================= */


/* =========================
   SEED DATA
   ========================= */



INSERT INTO rentals (id, property_id, owner_id, tenant_id, rent, expiry, status) VALUES
                                                                                     ('00000000-0000-0000-0000-000000000301',
                                                                                      '00000000-0000-0000-0000-000000000001',
                                                                                      '00000000-0000-0000-0000-000000000101',
                                                                                      '00000000-0000-0000-0000-000000000201',
                                                                                      1200.00, '2027-01-01', 'ACTIVE'),

                                                                                     ('00000000-0000-0000-0000-000000000302',
                                                                                      '00000000-0000-0000-0000-000000000002',
                                                                                      '00000000-0000-0000-0000-000000000102',
                                                                                      '00000000-0000-0000-0000-000000000202',
                                                                                      1500.00, '2027-06-01', 'ACTIVE'),

                                                                                     ('00000000-0000-0000-0000-000000000303',
                                                                                      '00000000-0000-0000-0000-000000000003',
                                                                                      '00000000-0000-0000-0000-000000000103',
                                                                                      '00000000-0000-0000-0000-000000000203',
                                                                                      900.00, '2026-12-31', 'EXPIRED');