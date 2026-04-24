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
        var tenant = new Tenant(TenantId.newId(), name, new CreditScore(score), new BankDetails(accountNumber, ABA));
        if (repo.existsByName(tenant.name())) {
            throw new DuplicateTenantException("Tenant already exists by name: " + tenant.name());
        }

        if (!name.matches("[A-Za-z -]+")) {
            throw new InvalidTenantNameException("Name must contain only letters");
        }
        String n = name.trim();
        if (n.length() < 2 || n.length() > 100) throw new InvalidTenantNameException("Tenant name length must be 2..100");
        return repo.save(tenant);
    }

    @Transactional(readOnly = true)
    public Tenant getById(UUID id) {
        return repo.findById(TenantId.of(id))
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<Tenant> list() { return repo.findAll(); }

    @Transactional
    public Tenant update(UUID id, String name, int score, String accountNumber, String ABA) {
        var a = getById(id);
        a.update(name, new CreditScore(score), new BankDetails(accountNumber, ABA));
        return repo.save(a);
    }

    @Transactional
    public Tenant activate(UUID id) {
        var a = getById(id);
        a.activate();
        return repo.save(a);
    }

    @Transactional
    public void delete(UUID id) {
        getById(id);
        repo.deleteById(TenantId.of(id));
    }
}
