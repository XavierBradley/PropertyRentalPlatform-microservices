package com.champsoft.propertyrentalplatform.tenant.application.service;

import com.champsoft.propertyrentalplatform.tenant.application.exception.*;
import com.champsoft.propertyrentalplatform.tenant.application.port.out.TenantRepositoryPort;
import com.champsoft.propertyrentalplatform.tenant.domain.exception.InvalidTenantNameException;
import com.champsoft.propertyrentalplatform.tenant.domain.model.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TenantCrudService {

    private final TenantRepositoryPort repo;

    public TenantCrudService(TenantRepositoryPort repo) {
        this.repo = repo;
    }

    @Transactional
    public Tenant create(String name, int score, String accountNumber, String ABA) {

        String n = name == null ? "" : name.trim();

        if (!n.matches("[A-Za-z -]+")) {
            throw new InvalidTenantNameException("Name must contain only letters");
        }

        if (n.length() < 2 || n.length() > 100) {
            throw new InvalidTenantNameException("Tenant name length must be 2..100");
        }

        if (repo.existsByName(n)) {
            throw new DuplicateTenantException("Tenant already exists by name: " + n);
        }

        Tenant tenant = new Tenant(
                TenantId.newId(),
                n,
                new CreditScore(score),
                new BankDetails(accountNumber, ABA)
        );

        return repo.save(tenant);
    }

    @Transactional(readOnly = true)
    public Tenant getById(UUID id) {
        return repo.findById(TenantId.of(id))
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<Tenant> list() {
        return repo.findAll();
    }

    @Transactional
    public Tenant update(UUID id, String name, int score, String accountNumber, String ABA) {
        Tenant tenant = getById(id);
        tenant.update(name, new CreditScore(score), new BankDetails(accountNumber, ABA));
        return repo.save(tenant);
    }

    @Transactional
    public Tenant activate(UUID id) {
        Tenant tenant = getById(id);
        tenant.activate();
        return repo.save(tenant);
    }

    @Transactional
    public void delete(UUID id) {
        getById(id);
        repo.deleteById(TenantId.of(id));
    }
}