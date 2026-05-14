package pro.ms.simulador.domain;

public enum DeviceType {
    MEDICAL("Medical"),
    METABOLIC("Metabolic"),
    LIPID("Lipid"),
    ELECTROLYTE("Electrolyte"),
    BLOOD_COUNT("BloodCount");

    private final String code;

    DeviceType(String code) {
        this.code = code;
    }
    
    public static DeviceType fromCode(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("deviceType no puede ser nulo o vacio");
        }

        for (DeviceType type : values()) {
            if (type.code.equalsIgnoreCase(value) || type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException("deviceType invalido: " + value);
    }
}

