/* =========================
   TABLES
   ========================= */


CREATE TABLE IF NOT EXISTS tenants (
    id              VARCHAR(250) PRIMARY KEY,
    name            VARCHAR(100) NOT NULL,
    score           INT NOT NULL,

    -- bank_details
    account_number  VARCHAR(12) NOT NULL,
    aba             VARCHAR(9) NOT NULL,

    status          VARCHAR(20) NOT NULL
);


/* =========================
   Checks
   ========================= */

ALTER TABLE tenants
    ADD CONSTRAINT chk_status
        CHECK (status IN ('ACTIVE', 'INACTIVE'));

ALTER TABLE tenants
    ADD CONSTRAINT chk_name_length
        CHECK (char_length(name) BETWEEN 2 AND 100);

ALTER TABLE tenants
    ADD CONSTRAINT chk_account_number_length
        CHECK (char_length(account_number) BETWEEN 8 AND 12);

ALTER TABLE tenants
    ADD CONSTRAINT chk_aba_length
        CHECK (char_length(aba) = 9);

/* =========================
   INDEXES
   ========================= */


/* =========================
   SEED DATA
   ========================= */

INSERT INTO tenants (id, name, score, account_number, aba, status) VALUES
        ('00000000-0000-0000-0000-000000000201', 'John Doe', 720, '12345678', '111000025', 'ACTIVE'),
        ('00000000-0000-0000-0000-000000000202', 'Jane Smith', 680, '87654321', '222000111', 'ACTIVE'),
        ('00000000-0000-0000-0000-000000000203', 'Mike Johnson', 650, '1122334455', '333000999', 'INACTIVE'),
        ('00000000-0000-0000-0000-000000000204', 'Emily Davis', 740, '99887766', '444000888', 'ACTIVE'),
        ('00000000-0000-0000-0000-000000000205', 'Chris Lee', 700, '5566778899', '555000777', 'ACTIVE');
