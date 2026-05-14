package pro.ms.simulador.infrastructure.DTO.request;

import pro.ms.simulador.domain.DeviceType;

public record DeviceRequestDTO(String deviceId, String userId, String deviceType, Integer interval) {
    public String getDeviceId() {return deviceId;}
    public String getUserId() {return userId;}
    public DeviceType getDeviceType() {
        return DeviceType.fromCode(deviceType);
    }
    public Integer getDeviceInterval() {
        return interval;
    }
}
