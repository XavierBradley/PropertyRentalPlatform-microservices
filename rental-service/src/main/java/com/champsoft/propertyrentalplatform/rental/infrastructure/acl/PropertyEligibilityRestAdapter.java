package com.champsoft.propertyrentalplatform.rental.infrastructure.acl;

import com.champsoft.propertyrentalplatform.rental.application.exception.CrossContextValidationException;
import com.champsoft.propertyrentalplatform.rental.application.port.out.PropertyEligibilityPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class PropertyEligibilityRestAdapter implements PropertyEligibilityPort {

    private final RestTemplate restTemplate;

    @Value("${service.properties.base-url}")
    private String propertiesBaseUrl;

    public PropertyEligibilityRestAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean isEligible(String propertyId) {
        String url = propertiesBaseUrl + "api/properties/" + propertyId + ".eligibility";

        try {
            Boolean result = restTemplate.getForObject(url, Boolean.class);

            return Boolean.TRUE.equals(result);
        } catch (HttpClientErrorException.NotFound ex) {

            throw new CrossContextValidationException(" Property not found : " + propertyId);
        }catch (HttpClientErrorException ex) {

            throw new CrossContextValidationException("Property validation failed : " + propertyId);
        }catch (Exception ex) {

            throw new CrossContextValidationException("Property service is unavailable");
        }
    }
}
