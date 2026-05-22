package pro.ms.simulador.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pro.ms.simulador.application.SimulationOrchestrationUseCase;
import pro.ms.simulador.domain.CommandPayload;
import pro.ms.simulador.infrastructure.MQTT.consumer.TelemetryReceived;
import jakarta.annotation.PostConstruct;

@Component
public class SpringListenerCommand {
    private final SimulationOrchestrationUseCase simulationOrchestrationUseCase;

    @Autowired
    public SpringListenerCommand(SimulationOrchestrationUseCase simulationOrchestrationUseCase) {
        this.simulationOrchestrationUseCase = simulationOrchestrationUseCase;
    }

    @PostConstruct
    public void logStartup() {
        System.out.println("[SpringListenerCommand] bean initialized");
    }

    @EventListener
    public void onApplicationEvent(TelemetryReceived<?> received) {
        System.out.println("[SpringListenerCommand] typeEvent=" + received.typeEvent() + ", topic=" + received.topic());
        System.out.println("Received command: " + received);
        if (received.messageToDeserialize() instanceof CommandPayload payload) {
            TelemetryReceived<CommandPayload> typed = new TelemetryReceived<>(
                    received.typeEvent(), received.topic(), payload
            );
            simulationOrchestrationUseCase.executeCommand(typed);
        }
    }
}
