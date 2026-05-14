package pro.ms.simulador.infrastructure.DTO.respond;

public record DeviceRespondDTO(String deviceId, String userId, String deviceType, Integer interval, String status) {
    public String getDeviceId() {return deviceId;}
    public String getUserId() {return userId;}
    public String getDeviceType() {return deviceType;}
    public Integer getDeviceInterval() {
        return interval;
    }
    public String getStatus() {return status;}
}
