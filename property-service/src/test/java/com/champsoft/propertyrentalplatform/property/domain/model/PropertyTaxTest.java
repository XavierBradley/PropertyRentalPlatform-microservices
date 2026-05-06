package com.champsoft.propertyrentalplatform.property.domain.model;

import com.champsoft.propertyrentalplatform.property.domain.exception.InvalidPropertyTaxException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// Domain test → business rule validation
class PropertyTaxTest {

    @Test
    void shouldCreateValidPropertyTax() {

        // ------------------- Act -------------------
        PropertyTax tax = new PropertyTax(0.015);

        // ------------------- Assert -------------------
        assertThat(tax.value()).isEqualTo(0.015);
    }

    @Test
    void shouldRejectZeroOrNegativeTax() {

        // ------------------- Assert -------------------
        assertThatThrownBy(() -> new PropertyTax(0))
                .isInstanceOf(InvalidPropertyTaxException.class);

        assertThatThrownBy(() -> new PropertyTax(-0.01))
                .isInstanceOf(InvalidPropertyTaxException.class);
    }

    @Test
    void shouldRejectTaxBelowMinimumThreshold() {

        // ------------------- Assert -------------------
        assertThatThrownBy(() -> new PropertyTax(0.002))
                .isInstanceOf(InvalidPropertyTaxException.class);
    }

    @Test
    void shouldRejectTaxAboveMaximumThreshold() {

        // ------------------- Assert -------------------
        assertThatThrownBy(() -> new PropertyTax(0.03))
                .isInstanceOf(InvalidPropertyTaxException.class);
    }
}