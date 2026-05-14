package uni.csw.medibug.device_management_context.infrastructure.errors;

public class DownstreamClientException extends RuntimeException {

    private final int downstreamStatus;

    public DownstreamClientException(int downstreamStatus, String message) {
        super(message);
        this.downstreamStatus = downstreamStatus;
    }

    public int getDownstreamStatus() {
        return downstreamStatus;
    }
}

