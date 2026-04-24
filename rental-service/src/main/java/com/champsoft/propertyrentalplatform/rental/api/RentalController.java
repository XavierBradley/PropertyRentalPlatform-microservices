package com.champsoft.propertyrentalplatform.rental.api;

import com.champsoft.propertyrentalplatform.rental.api.dto.*;
import com.champsoft.propertyrentalplatform.rental.api.mapper.RentalApiMapper;
import com.champsoft.propertyrentalplatform.rental.application.service.*;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalOrchestrator orchestrator;
    private final RentalCrudService crud;
    private final RentalRepresentationAssembler assembler;

    public RentalController(
            RentalOrchestrator orchestrator,
            RentalCrudService crud,
            RentalRepresentationAssembler assembler
    ) {
        this.orchestrator = orchestrator;
        this.crud = crud;
        this.assembler = assembler;
    }

    @PostMapping
    public ResponseEntity<EntityModel<RentalResponse>> register(@RequestBody @Valid RegisterPropertyRequest req) {
        var reg = orchestrator.register(req.propertyId(), req.ownerId(), req.tenantId(), req.rent(), req.expiry());
        var response = RentalApiMapper.toResponse(reg);
        return ResponseEntity.ok(assembler.toModel(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<RentalResponse>> get(@PathVariable UUID id) {
        var response = RentalApiMapper.toResponse(crud.get(id));
        return ResponseEntity.ok(assembler.toModel(response));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<RentalResponse>>> list() {
        List<RentalResponse> responses = crud.list().stream()
                .map(RentalApiMapper::toResponse)
                .toList();

        return ResponseEntity.ok(assembler.toCollectionModel(responses));
    }

    @PostMapping("/{id}/renew")
    public ResponseEntity<EntityModel<RentalResponse>> renew(
            @PathVariable UUID id,
            @RequestBody @Valid RenewRentalRequest req
    ) {
        var reg = crud.renew(id, req.newExpiry());
        var response = RentalApiMapper.toResponse(reg);
        return ResponseEntity.ok(assembler.toModel(response));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<EntityModel<RentalResponse>> cancel(@PathVariable UUID id) {
        var reg = crud.cancel(id);
        var response = RentalApiMapper.toResponse(reg);
        return ResponseEntity.ok(assembler.toModel(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        crud.delete(id);
        return ResponseEntity.noContent().build();
    }
}