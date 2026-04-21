package com.champsoft.propertyrentalplatform.tenant.api;

import com.champsoft.propertyrentalplatform.tenant.api.dto.*;
import com.champsoft.propertyrentalplatform.tenant.api.mapper.TenantApiMapper;
import com.champsoft.propertyrentalplatform.tenant.application.service.TenantCrudService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenant")
public class TenantController {

    private final TenantCrudService service;
    public TenantController(TenantCrudService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateTenantRequest req) {
        var a = service.create(req.name(), req.score(), req.accountNumber(), req.ABA());
        return ResponseEntity.ok(TenantApiMapper.toResponse(a));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseEntity.ok(TenantApiMapper.toResponse(service.getById(id)));
    }

    @GetMapping
    public ResponseEntity<?> list() { return ResponseEntity.ok(service.list().stream().map(TenantApiMapper::toResponse).toList()); }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody @Valid UpdateTenantRequest req) {
        var a = service.update(id, req.name(), req.score(), req.accountNumber(), req.ABA());
        return ResponseEntity.ok(TenantApiMapper.toResponse(a));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<?> activate(@PathVariable String id) {
        var a = service.activate(id);
        return ResponseEntity.ok(TenantApiMapper.toResponse(a));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
