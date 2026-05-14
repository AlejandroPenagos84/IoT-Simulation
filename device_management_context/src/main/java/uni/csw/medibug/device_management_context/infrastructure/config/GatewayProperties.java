package uni.csw.medibug.device_management_context.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "device-management")
public record GatewayProperties(
        String baseUrl,
        Endpoints endpoints,
        Timeout timeout
) {

    public record Endpoints(
            String activate,
            String deactivate
    ) {}

    public record Timeout(
            int connect,
            int read
    ) {}
}