package com.champsoft.propertyrentalplatform.tenant.api;

import com.champsoft.propertyrentalplatform.tenant.api.dto.*;
import com.champsoft.propertyrentalplatform.tenant.api.mapper.TenantApiMapper;
import com.champsoft.propertyrentalplatform.tenant.application.service.TenantCrudService;
import com.champsoft.propertyrentalplatform.tenant.application.service.TenantEligibilityService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    private final TenantCrudService service;
    private final TenantEligibilityService eligibilityService;

    public TenantController(TenantCrudService service, TenantEligibilityService eligibilityService) {
        this.service = service;
        this.eligibilityService = eligibilityService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateTenantRequest req) {
        var a = service.create(req.name(), req.score(), req.accountNumber(), req.ABA());
        return ResponseEntity.ok(TenantApiMapper.toResponse(a));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id) {
        return ResponseEntity.ok(TenantApiMapper.toResponse(service.getById(id)));
    }

    @GetMapping
    public ResponseEntity<?> list() { return ResponseEntity.ok(service.list().stream().map(TenantApiMapper::toResponse).toList()); }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid UpdateTenantRequest req) {
        var a = service.update(id, req.name(), req.score(), req.accountNumber(), req.ABA());
        return ResponseEntity.ok(TenantApiMapper.toResponse(a));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<?> activate(@PathVariable UUID id) {
        var a = service.activate(id);
        return ResponseEntity.ok(TenantApiMapper.toResponse(a));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/eligibility")
    public ResponseEntity<Boolean> isEligible(@PathVariable UUID id) {
        return ResponseEntity.ok(eligibilityService.isEligible(id));
    }
}
