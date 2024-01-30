package pl.pjatk.mprProject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@ComponentScan(basePackages = "pl.pjatk.mprProject")
public class Config {

    @Bean
    public RestClient getRestClient() {
        return RestClient.builder().build();
    }
}