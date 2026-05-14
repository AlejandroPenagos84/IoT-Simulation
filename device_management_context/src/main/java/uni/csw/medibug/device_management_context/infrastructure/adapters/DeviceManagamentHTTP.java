package uni.csw.medibug.device_management_context.infrastructure.adapters;

import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;
import uni.csw.medibug.device_management_context.application.DTO.request.DeviceRequestDTO;
import uni.csw.medibug.device_management_context.application.DTO.respond.DeviceRespondDTO;
import uni.csw.medibug.device_management_context.application.ports.out.DeviceManagament;
import uni.csw.medibug.device_management_context.infrastructure.config.GatewayProperties;
import uni.csw.medibug.device_management_context.infrastructure.errors.DownstreamClientException;
import uni.csw.medibug.device_management_context.infrastructure.errors.DownstreamServerException;
import uni.csw.medibug.device_management_context.infrastructure.errors.DownstreamTimeoutException;
import uni.csw.medibug.device_management_context.infrastructure.errors.DownstreamUnavailableException;

import io.netty.handler.timeout.ReadTimeoutException;

import java.util.concurrent.TimeoutException;

@Component
public class DeviceManagamentHTTP implements DeviceManagament {

    private final WebClient webClient;
    private final GatewayProperties gatewayProperties;

    public DeviceManagamentHTTP(WebClient webClient, GatewayProperties gatewayProperties) {
        this.webClient = webClient;
        this.gatewayProperties = gatewayProperties;
    }

    @Override
    public Mono<DeviceRespondDTO> register(DeviceRequestDTO deviceRequestDTO) {
        return webClient.post()
                .uri(gatewayProperties.endpoints().activate())
                .bodyValue(deviceRequestDTO)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(String.class)
                                .defaultIfEmpty("Error 4xx del microservicio externo")
                                .map(error -> new DownstreamClientException(response.statusCode().value(), error))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        response.bodyToMono(String.class)
                                .defaultIfEmpty("Error 5xx del microservicio externo")
                                .map(error -> new DownstreamServerException(response.statusCode().value(), error))
                )
                .bodyToMono(DeviceRespondDTO.class)
                .onErrorMap(ReadTimeoutException.class,
                        ex -> new DownstreamTimeoutException("Timeout consumiendo el microservicio externo", ex))
                .onErrorMap(TimeoutException.class,
                        ex -> new DownstreamTimeoutException("Timeout consumiendo el microservicio externo", ex))
                .onErrorMap(WebClientRequestException.class,
                        ex -> new DownstreamUnavailableException("No se pudo conectar con el microservicio externo", ex));
    }

}


