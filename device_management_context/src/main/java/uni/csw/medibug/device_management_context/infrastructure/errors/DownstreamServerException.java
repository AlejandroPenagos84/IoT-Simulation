package uni.csw.medibug.device_management_context.infrastructure.errors;

public class DownstreamServerException extends RuntimeException {

    private final int downstreamStatus;

    public DownstreamServerException(int downstreamStatus, String message) {
        super(message);
        this.downstreamStatus = downstreamStatus;
    }

    public int getDownstreamStatus() {
        return downstreamStatus;
    }
}

