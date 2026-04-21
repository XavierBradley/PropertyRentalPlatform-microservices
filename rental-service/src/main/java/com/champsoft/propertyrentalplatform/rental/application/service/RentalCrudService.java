package com.champsoft.propertyrentalplatform.rental.application.service;

import com.champsoft.propertyrentalplatform.rental.application.exception.RentalNotFoundException;
import com.champsoft.propertyrentalplatform.rental.application.port.out.RentalRepositoryPort;
import com.champsoft.propertyrentalplatform.rental.domain.model.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentalCrudService {

    private final RentalRepositoryPort repo;
    public RentalCrudService(RentalRepositoryPort repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public Rental get(String id) {
        return repo.findById(RentalId.of(id))
                .orElseThrow(() -> new RentalNotFoundException("Registration not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<Rental> list() {
        return repo.findAll();
    }

    @Transactional
    public Rental renew(String id, LocalDate newExpiry) {
        var reg = get(id);
        reg.renew(new ExpiryDate(newExpiry));
        return repo.save(reg);
    }

    @Transactional
    public Rental cancel(String id) {
        var reg = get(id);
        reg.cancel();
        return repo.save(reg);
    }

    @Transactional
    public void delete(String id) {
        get(id);
        repo.deleteById(RentalId.of(id));
    }
}
