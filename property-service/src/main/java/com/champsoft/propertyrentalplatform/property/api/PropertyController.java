package com.champsoft.propertyrentalplatform.property.api;

import com.champsoft.propertyrentalplatform.property.api.dto.CreatePropertyRequest;
import com.champsoft.propertyrentalplatform.property.api.dto.UpdatePropertyRequest;
import com.champsoft.propertyrentalplatform.property.api.mapper.PropertyApiMapper;
import com.champsoft.propertyrentalplatform.property.application.service.PropertyCrudService;
import com.champsoft.propertyrentalplatform.property.application.service.PropertyEligibilityService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/property")
public class PropertyController {

    private final PropertyCrudService service;
    private final PropertyEligibilityService eligibilityService;

    public PropertyController(PropertyCrudService service, PropertyEligibilityService eligibilityService) {
        this.service = service;
        this.eligibilityService = eligibilityService;
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreatePropertyRequest req) {
        var v = service.create(req.tax(), req.address());
        return ResponseEntity.ok(PropertyApiMapper.toResponse(v));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id) {
        return ResponseEntity.ok(PropertyApiMapper.toResponse(service.getById(id)));
    }

    @GetMapping
    public ResponseEntity<?> list() {
        List<?> data = service.list().stream().map(PropertyApiMapper::toResponse).toList();
        return ResponseEntity.ok(data);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid UpdatePropertyRequest req) {
        var v = service.update(id, req.tax(), req.address());
        return ResponseEntity.ok(PropertyApiMapper.toResponse(v));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<?> activate(@PathVariable UUID id) {
        var v = service.activate(id);
        return ResponseEntity.ok(PropertyApiMapper.toResponse(v));
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
