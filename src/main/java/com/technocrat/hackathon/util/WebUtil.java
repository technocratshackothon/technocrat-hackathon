package com.technocrat.hackathon.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by vikas on 04-04-2020.
 */
@Component
@Slf4j
public class WebUtil<T,V> {

    private RestTemplate restTemplate;

    private ObjectMapper objectMapper;

    private int statusCode;

    @Inject
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Inject
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public V execute(String requestUrl, T data, Class<V> genericClass, HttpMethod httpMethod)
            throws Exception {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<T> entity = new HttpEntity<T>(data, headers);
        ResponseEntity<V> response = restTemplate.exchange(requestUrl,httpMethod,
                entity, genericClass);
        setStatusCode(response.getStatusCode().value());
        return response.getBody();
    }

    public String generatePathVariableUrl(String mainUrl, List<String> pathVariableToAppend){
        StringBuilder str = new StringBuilder(mainUrl.trim());
        pathVariableToAppend.forEach(t->str.append(t.trim()).append("/"));
        return str.toString();
    }
}
