package com.champsoft.propertyrentalplatform.rental.application.port.out;

import com.champsoft.propertyrentalplatform.rental.domain.model.*;

import java.util.List;
import java.util.Optional;

public interface RentalRepositoryPort {
    Rental save(Rental reg);
    Optional<Rental> findById(RentalId id);
    List<Rental> findAll();
    void deleteById(RentalId id);
}
