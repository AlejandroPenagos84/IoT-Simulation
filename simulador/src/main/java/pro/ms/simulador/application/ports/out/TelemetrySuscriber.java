package pro.ms.simulador.application.ports.out;

public interface TelemetrySuscriber <T>{
    Class<T> getClassType();
    void handle(String topic,T message);
}
