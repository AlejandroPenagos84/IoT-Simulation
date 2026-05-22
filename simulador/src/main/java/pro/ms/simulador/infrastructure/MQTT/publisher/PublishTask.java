package pro.ms.simulador.infrastructure.MQTT.publisher;

import pro.ms.simulador.domain.TelemetryMessage;

public record PublishTask(String topic, TelemetryMessage<?> messageToSerialize) {}