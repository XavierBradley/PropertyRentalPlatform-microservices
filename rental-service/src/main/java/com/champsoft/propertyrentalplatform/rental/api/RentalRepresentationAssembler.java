package com.champsoft.propertyrentalplatform.rental.api;

import com.champsoft.propertyrentalplatform.rental.api.dto.RentalResponse;
import com.champsoft.propertyrentalplatform.rental.api.dto.RenewRentalRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RentalRepresentationAssembler {

    public EntityModel<RentalResponse> toModel(RentalResponse response) {
        EntityModel<RentalResponse> model = EntityModel.of(response);

        model.add(linkTo(methodOn(RentalController.class).get(response.id())).withSelfRel());
        model.add(linkTo(methodOn(RentalController.class).list()).withRel("rentals"));

        if ("ACTIVE".equalsIgnoreCase(response.status())) {
            model.add(
                    linkTo(methodOn(RentalController.class)
                            .renew(response.id(), new RenewRentalRequest(null)))
                            .withRel("renew")
            );

            model.add(
                    linkTo(methodOn(RentalController.class)
                            .cancel(response.id()))
                            .withRel("cancel")
            );
        }

        return model;
    }

    public CollectionModel<EntityModel<RentalResponse>> toCollectionModel(List<RentalResponse> responses) {
        List<EntityModel<RentalResponse>> items = responses.stream()
                .map(this::toModel)
                .toList();

        return CollectionModel.of(
                items,
                linkTo(methodOn(RentalController.class).list()).withSelfRel()
        );
    }
}
