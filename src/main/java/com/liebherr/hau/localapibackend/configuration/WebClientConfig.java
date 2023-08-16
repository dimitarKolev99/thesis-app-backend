package com.liebherr.hau.localapibackend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

    @Value("${appliance.url}")
    private String applianceServiceUrl;

    @Value("${voucher.url}")
    private String voucherServiceUrl;

    @Bean
    public WebClient applianceClient() {
        return WebClient.builder()
                .baseUrl(applianceServiceUrl)
                .clientConnector(this.createClientHttpConnector())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public WebClient voucherClient() {
        return WebClient.builder()
                .baseUrl(voucherServiceUrl)
                .clientConnector(this.createClientHttpConnector())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }


    /**
     * Utility to create the client http connector. Needed to plugin the HttpClient into the
     * ClientHttpConnector.
     *
     * @return
     */
    private ClientHttpConnector createClientHttpConnector() {
        HttpClient client = HttpClient.create();

        ClientHttpConnector connector = new ReactorClientHttpConnector(client);

        return connector;
    }
}
