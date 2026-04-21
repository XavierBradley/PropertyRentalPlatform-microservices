/* =========================
   TABLES
   ========================= */

CREATE TABLE IF NOT EXISTS properties (
    id          VARCHAR(250) PRIMARY KEY,
    tax         DOUBLE NOT NULL,
    address     VARCHAR(200) NOT NULL,
    status      VARCHAR(20) NOT NULL
);

/* =========================
   Checks
   ========================= */

ALTER TABLE properties
    ADD CONSTRAINT chk_status
        CHECK (status IN ('AVAILABLE', 'UNAVAILABLE'));

/* =========================
   SEED DATA
   ========================= */

INSERT INTO properties (id, tax, address, status) VALUES
        ('00000000-0000-0000-0000-000000000001', 0.015, '123 Maple St, Montreal', 'AVAILABLE'),
        ('00000000-0000-0000-0000-000000000002', 0.010, '456 Oak Ave, Montreal', 'UNAVAILABLE'),
        ('00000000-0000-0000-0000-000000000003', 0.010, '789 Pine Rd, Laval', 'AVAILABLE'),
        ('00000000-0000-0000-0000-000000000004', 0.015, '321 Birch Blvd, Longueuil', 'UNAVAILABLE'),
        ('00000000-0000-0000-0000-000000000005', 0.010, '654 Cedar Ln, Quebec City', 'AVAILABLE');
