package com.champsoft.propertyrentalplatform.owners.api;

import com.champsoft.propertyrentalplatform.owners.api.dto.*;
import com.champsoft.propertyrentalplatform.owners.api.mapper.OwnerApiMapper;
import com.champsoft.propertyrentalplatform.owners.application.service.OwnerCrudService;
import com.champsoft.propertyrentalplatform.owners.application.service.OwnerEligibilityService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private final OwnerCrudService service;
    private final OwnerEligibilityService eligibilityService;

    public OwnerController(OwnerCrudService service, OwnerEligibilityService eligibilityService) {
        this.service = service;
        this.eligibilityService = eligibilityService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateOwnerRequest req) {
        var o = service.create(req.fullName(), req.address());
        return ResponseEntity.ok(OwnerApiMapper.toResponse(o));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseEntity.ok(OwnerApiMapper.toResponse(service.getById(id)));
    }

    @GetMapping
    public ResponseEntity<?> list() { return ResponseEntity.ok(service.list().stream().map(OwnerApiMapper::toResponse).toList()); }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody @Valid UpdateOwnerRequest req) {
        var o = service.update(id, req.fullName(), req.address());
        return ResponseEntity.ok(OwnerApiMapper.toResponse(o));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<?> activate(@PathVariable String id) {
        var o = service.activate(id);
        return ResponseEntity.ok(OwnerApiMapper.toResponse(o));
    }

    @PostMapping("/{id}/suspend")
    public ResponseEntity<?> suspend(@PathVariable String id) {
        var o = service.deactivate(id);
        return ResponseEntity.ok(OwnerApiMapper.toResponse(o));
    }

    @GetMapping("/{id}/eligibility")
    public ResponseEntity<Boolean> isEligible(@PathVariable String id) {
        return ResponseEntity.ok(eligibilityService.isEligible(id));
    }
}
