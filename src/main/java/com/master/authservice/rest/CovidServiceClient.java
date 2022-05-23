package com.master.authservice.rest;

import com.master.authservice.exceptions.ExternalServiceException;
import com.master.authservice.rest.dto.InstitutionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CovidServiceClient {
    private static Logger logger = LoggerFactory.getLogger(CovidServiceClient.class);

    private final RestTemplate restTemplate;

    private final String BASE_URL;

    private final String PATH;

    @Autowired
    public CovidServiceClient(
            RestTemplate restTemplate,
            @Value("${covid-service.base-url}") String baseUrl,
            @Value("${covid-service.path}") String path
    ) {
        this.restTemplate = restTemplate;
        this.BASE_URL = baseUrl;
        this.PATH = path;
    }

    public void addInstitution(InstitutionDto dto) {

        String uri = BASE_URL.concat(PATH);

        ResponseEntity<InstitutionDto> response = restTemplate.postForEntity(uri, dto, InstitutionDto.class);

        if (response.getStatusCode() != HttpStatus.CREATED) {
            logger.error(response.toString());
            throw new ExternalServiceException("Create institution on covid service returned bad status code.");
        }

    }

    public void updateInstitution(InstitutionDto dto, String id, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", token);
        HttpEntity<InstitutionDto> requestEntity = new HttpEntity<>(dto, headers);

        String uri = BASE_URL.concat(PATH).concat(String.format("/%s", id));

        ResponseEntity<InstitutionDto> response = restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, InstitutionDto.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            logger.error(response.toString());
            throw new ExternalServiceException("Update institution on covid service returned bad status code.");
        }
    }

    public void deleteInstitution(String id, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", token);
        HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);

        String uri = BASE_URL.concat(PATH).concat(String.format("/%s", id));

        ResponseEntity<InstitutionDto> response = restTemplate.exchange(uri, HttpMethod.DELETE, requestEntity, InstitutionDto.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            logger.error(response.toString());
            throw new ExternalServiceException("Delete institution on covid service returned bad status code.");
        }
    }
}
