package uni.csw.medibug.device_management_context.infrastructure.payloadHandlers;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import uni.csw.medibug.device_management_context.application.ports.out.StatusPayloadHandler;

@Component
@MqttTopic("sensor/{deviceId}/commands")
public class StatusPayloadHandlerImpl implements StatusPayloadHandler {
    private final ApplicationEventPublisher eventPublisher;

    public StatusPayloadHandlerImpl(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public String buildPayload(String payload) {
        eventPublisher.publishEvent(payload);
        return payload;
    }
}
