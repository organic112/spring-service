package com.potato112.springservice.domain.foo.api;


import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = fooApi.ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class fooApi {

    static final String ENDPOINT = "/api/v1/foo";

    @GetMapping(value = "test")
    public String getUserByName() {

        System.out.println("Test response from test endpoint (request with no params)");
        return "Test response from test endpoint (request with no params)";
    }
}
