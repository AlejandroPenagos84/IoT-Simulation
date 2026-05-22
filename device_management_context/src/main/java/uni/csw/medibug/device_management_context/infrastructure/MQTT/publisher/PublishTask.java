package uni.csw.medibug.device_management_context.infrastructure.MQTT.publisher;
import uni.csw.medibug.device_management_context.domain.Payload;

public record PublishTask(String topic, Payload messageToSerialize) {}