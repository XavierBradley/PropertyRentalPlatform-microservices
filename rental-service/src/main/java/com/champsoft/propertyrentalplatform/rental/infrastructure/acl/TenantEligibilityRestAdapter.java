package com.champsoft.propertyrentalplatform.rental.infrastructure.acl;

import com.champsoft.propertyrentalplatform.rental.application.exception.CrossContextValidationException;
import com.champsoft.propertyrentalplatform.rental.application.port.out.TenantEligibilityPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;


@Component
public class TenantEligibilityRestAdapter implements TenantEligibilityPort {

    private final RestTemplate restTemplate;

    @Value("${services.tenant.base-url}")
    private String tenantsBaseUrl;

    public TenantEligibilityRestAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean isEligible(UUID tenantId) {
        String url = tenantsBaseUrl + "/api/tenants/" + tenantId + "/eligibility";

        try {
            Boolean result = restTemplate.getForObject(url, Boolean.class);
            return Boolean.TRUE.equals(result);

        } catch (HttpClientErrorException.NotFound ex) {
            throw new CrossContextValidationException("Tenant not found : " + tenantId);

        } catch (HttpClientErrorException ex) {
            throw new CrossContextValidationException("Tenant validation failed : " + tenantId);

        } catch (HttpServerErrorException ex) {
            throw new CrossContextValidationException("Tenant service error : " + tenantId);

        } catch (ResourceAccessException ex) {
            throw new CrossContextValidationException("Tenant service is unavailable");
        }
    }
}
