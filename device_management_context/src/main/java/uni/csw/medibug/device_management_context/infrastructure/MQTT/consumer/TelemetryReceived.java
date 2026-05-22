package uni.csw.medibug.device_management_context.infrastructure.MQTT.consumer;

public record TelemetryReceived<T> (String topic, T messageToDeserialize){}
