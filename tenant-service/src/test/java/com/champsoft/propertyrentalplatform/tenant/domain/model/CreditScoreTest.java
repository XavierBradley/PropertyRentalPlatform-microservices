package com.champsoft.propertyrentalplatform.tenant.domain.model;

import com.champsoft.propertyrentalplatform.tenant.domain.exception.InvalidCreditScoreException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreditScoreTest {

    @Test
    @DisplayName("Should create valid credit score")
    void shouldCreateValidCreditScore() {

        CreditScore score = new CreditScore(700);

        assertThat(score.value()).isEqualTo(700);
    }

    @Test
    @DisplayName("Should allow minimum score")
    void shouldAllowMinimumScore() {

        CreditScore score = new CreditScore(300);

        assertThat(score.value()).isEqualTo(300);
    }

    @Test
    @DisplayName("Should throw when score is below minimum")
    void shouldThrowWhenScoreIsBelowMinimum() {

        assertThrows(
                InvalidCreditScoreException.class,
                () -> new CreditScore(299)
        );
    }
}