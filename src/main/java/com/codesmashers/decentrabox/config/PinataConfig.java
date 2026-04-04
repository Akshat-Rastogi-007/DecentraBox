package com.codesmashers.decentrabox.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class PinataConfig {

    @Value("${pinata.jwt}")
    private String jwt;

    @Value("${pinata.base-url}")
    private String baseUrl;

    @Value("${pinata.upload-url}")
    private String uploadUrl;

    @Value("${pinata.gateway}")
    private String gateway;

    @Value("${pinata.signed-url-expiry}")
    private Long signedUrlExpiry;

}
