package com.champsoft.propertyrentalplatform.tenant.domain.model;

import com.champsoft.propertyrentalplatform.tenant.domain.exception.InvalidCreditScoreException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// Domain test → business rule validation
class CreditScoreTest {

    @Test
    void shouldCreateValidCreditScore() {

        // ------------------- Act -------------------
        CreditScore score = new CreditScore(700);

        // ------------------- Assert -------------------
        assertThat(score.value()).isEqualTo(700);
    }

    @Test
    void shouldRejectTooLowCreditScore() {

        // ------------------- Assert -------------------
        assertThatThrownBy(() -> new CreditScore(299))
                .isInstanceOf(InvalidCreditScoreException.class);
    }

    @Test
    void shouldAllowMinimumValidCreditScore() {

        // ------------------- Act -------------------
        CreditScore score = new CreditScore(300);

        // ------------------- Assert -------------------
        assertThat(score.value()).isEqualTo(300);
    }
}