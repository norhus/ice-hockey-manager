package cz.muni.fi.pa165.leaguetable.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${core.url}")
    private String coreUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.create(coreUrl);
    }
}
