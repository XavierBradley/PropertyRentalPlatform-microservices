package com.champsoft.propertyrentalplatform.rental.infrastructure.acl;

import com.champsoft.propertyrentalplatform.rental.application.exception.CrossContextValidationException;
import com.champsoft.propertyrentalplatform.rental.application.port.out.OwnerEligibilityPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;


@Component
public class OwnerEligibilityRestAdapter implements OwnerEligibilityPort {

    private final RestTemplate restTemplate;

    @Value("${services.owners.base-url}")
    private String ownersBaseUrl;

    public OwnerEligibilityRestAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean isEligible(UUID ownerId) {
        String url = ownersBaseUrl + "api/owners/" + ownerId + "/eligibility";

        try {
            Boolean result = restTemplate.getForObject(url, Boolean.class);

            return Boolean.TRUE.equals(result);
        } catch (HttpClientErrorException.NotFound ex) {

            throw new CrossContextValidationException(" Owner not found : " + ownerId);
        }catch (HttpClientErrorException ex) {

            throw new CrossContextValidationException("Owner validation failed : " + ownerId);
        }catch (ResourceAccessException ex) {

            throw new CrossContextValidationException("Owner service is unavailable");
        }
    }
}
