package com.shujaa.parking_system.application.utils;

import com.shujaa.parking_system.application.dtos.response.ReniecResponse;
import com.shujaa.parking_system.domain.entity.Cliente;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReniecUtils {

    private final RestTemplate restTemplate;

    @Value("${apis.reniec.url}")
    private String reniecUrl;

    @Value("${apis.reniec.token}")
    private String reniecToken;

    public Cliente buscarPersonaPorDni(String dni) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + reniecToken);
            headers.set("Accept", "application/json");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            var response = restTemplate.exchange(
                    reniecUrl + "?numero=" + dni,
                    HttpMethod.GET,
                    entity,
                    ReniecResponse.class);

            if (response.getBody() != null) {
                ReniecResponse reniecData = response.getBody();
                return Cliente.builder()
                        .dni(dni)
                        .nombres(reniecData.getNombres())
                        .apePaterno(reniecData.getApellidoPaterno())
                        .apeMaterno(reniecData.getApellidoMaterno())
                        .build();
            }
        } catch (Exception e) {
            log.error("Error al consultar RENIEC: {}", e.getMessage());
        }
        return null;
    }

}