/* =========================
   TABLES
   ========================= */

CREATE TABLE IF NOT EXISTS owners (
    id          VARCHAR(255) PRIMARY KEY,
    full_name   VARCHAR(100) NOT NULL,
    address     VARCHAR(200),
    status      VARCHAR(20) NOT NULL
);

/* =========================
   Checks
   ========================= */
ALTER TABLE owners
    ADD CONSTRAINT chk_status
        CHECK (status IN ('ACTIVE', 'INACTIVE'));
ALTER TABLE owners
    ADD CONSTRAINT chk_full_name_length
        CHECK (char_length(full_name) BETWEEN 2 AND 100);

/* =========================
   SEED DATA
   ========================= */

INSERT INTO owners (id, full_name, address, status) VALUES
        ('00000000-0000-0000-0000-000000000101', 'Alice Tremblay', 'Montreal, QC', 'ACTIVE'),
        ('00000000-0000-0000-0000-000000000102', 'Bob Martin', 'Laval, QC', 'ACTIVE'),
        ('00000000-0000-0000-0000-000000000103', 'Charlie Nguyen', 'Longueuil, QC', 'INACTIVE'),
        ('00000000-0000-0000-0000-000000000104', 'Diana Smith', 'Quebec City, QC', 'ACTIVE'),
        ('00000000-0000-0000-0000-000000000105', 'Ethan Brown', 'Sherbrooke, QC', 'INACTIVE');