package com.champsoft.propertyrentalplatform.owners.application.service;

import com.champsoft.propertyrentalplatform.owners.application.exception.DuplicateOwnerException;
import com.champsoft.propertyrentalplatform.owners.application.exception.OwnerNotFoundException;
import com.champsoft.propertyrentalplatform.owners.application.port.out.OwnerRepositoryPort;
import com.champsoft.propertyrentalplatform.owners.domain.model.*;

// import com.champsoft.propertyrentalplatform.tenant.domain.model.BankDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OwnerCrudService {

    private final OwnerRepositoryPort repo;
    public OwnerCrudService(OwnerRepositoryPort repo) { this.repo = repo; }

    @Transactional
    public Owner create(String fullName, String address) {
        var name = new FullName(fullName);
        String key = name.value();
        if (repo.existsByFullName(key)) {
            throw new DuplicateOwnerException("Owner already exists by name: " + key);
        }
        var owner = new Owner(OwnerId.newId(), name, new Address(address));
        return repo.save(owner);
    }

    @Transactional(readOnly = true)
    public Owner getById(UUID id) {
        return repo.findById(OwnerId.of(id))
                .orElseThrow(() -> new OwnerNotFoundException("Owner not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<Owner> list() { return repo.findAll(); }

    @Transactional
    public Owner update(UUID id, String fullName, String address) {
        var owner = getById(id);
        owner.update(new FullName(fullName), new Address(address));
        return repo.save(owner);
    }

    @Transactional
    public Owner activate(UUID id) {
        var owner = getById(id);
        owner.activate();
        return repo.save(owner);
    }

    @Transactional
    public Owner deactivate(UUID id) {
        var owner = getById(id);
        owner.deactivate();
        return repo.save(owner);
    }

    @Transactional
    public void delete(UUID id) {
        getById(id);
        repo.deleteById(OwnerId.of(id));
    }
}
