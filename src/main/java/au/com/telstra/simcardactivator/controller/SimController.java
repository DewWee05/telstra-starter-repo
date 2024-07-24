package au.com.telstra.simcardactivator.controller;

import au.com.telstra.simcardactivator.model.ActuatorRequest;
import au.com.telstra.simcardactivator.model.ActuatorResponse;
import au.com.telstra.simcardactivator.model.SimActivationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@RestController
public class SimController {

    private static final String restTemplateURL = "http://localhost:8444/actuate";

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/activate")
    public ResponseEntity<ActuatorResponse> activateSim (@RequestBody SimActivationRequest simActivationRequest) {
        ActuatorResponse response = new ActuatorResponse();

        if (simActivationRequest.getIccid() == null || simActivationRequest.getEmail() == null) {
            response.setSuccess(false);
            return ResponseEntity.badRequest().body(response);
        }


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(Collections.singletonMap("iccid", simActivationRequest.getIccid()), headers);

        try {
            ResponseEntity<Boolean> verificationResponse = restTemplate.postForEntity(restTemplateURL, entity, Boolean.class);
            boolean isActivated = verificationResponse.getBody();
            response.setSuccess(isActivated);
        } catch (Exception e) {
            response.setSuccess(false);
        }
        return ResponseEntity.ok(response);
    }
}
