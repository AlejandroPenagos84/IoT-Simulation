package pro.ms.simulador.infrastructure.MQTT.consumer;

public record TelemetryReceived<T> (String typeEvent, String topic, T messageToDeserialize){}
