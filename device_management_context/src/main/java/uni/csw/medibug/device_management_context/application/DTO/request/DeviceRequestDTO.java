package uni.csw.medibug.device_management_context.application.DTO.request;

public record DeviceRequestDTO(String deviceId, String userId, String deviceType, Integer interval) {
    public String getDeviceId() {return deviceId;}
    public String getUserId() {return userId;}
    public String getDeviceType() {
        return deviceType;
    }
    public Integer getDeviceInterval() {
        return interval;
    }
}
