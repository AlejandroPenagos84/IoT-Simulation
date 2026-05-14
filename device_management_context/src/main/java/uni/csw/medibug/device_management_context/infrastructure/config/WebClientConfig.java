package uni.csw.medibug.device_management_context.infrastructure.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
@EnableConfigurationProperties(GatewayProperties.class)
public class WebClientConfig {

    @Bean
    public WebClient webClient(GatewayProperties properties){
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofMillis(properties.timeout().read()));

        return WebClient.builder()
                .baseUrl(properties.baseUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
