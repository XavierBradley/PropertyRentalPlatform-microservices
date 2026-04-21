package com.champsoft.propertyrentalplatform.rental.domain.model;

import com.champsoft.propertyrentalplatform.rental.domain.exception.ExpiryDateMustBeFutureException;
import java.time.LocalDate;

public record ExpiryDate(LocalDate value) {
    public ExpiryDate {
        if (value == null) throw new IllegalArgumentException("expiry is required");
        if (!value.isAfter(LocalDate.now())) throw new ExpiryDateMustBeFutureException("Expiry must be in the future");
    }
}
