package pro.ms.simulador.domain.medicalPayload;

import java.util.Objects;

import pro.ms.simulador.domain.medicalPayload.electrolyte.ElectrolytePanel;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountPanel;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPanel;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPanel;
import pro.ms.simulador.domain.payload.Payload;

public record MedicalPayload(
		MetabolicPanel metabolic,
		LipidPanel lipids,
		ElectrolytePanel electrolytes,
		BloodCountPanel bloodCount
) implements Payload {

	public MedicalPayload {
		Objects.requireNonNull(metabolic, "metabolic must not be null");
		Objects.requireNonNull(lipids, "lipids must not be null");
		Objects.requireNonNull(electrolytes, "electrolytes must not be null");
		Objects.requireNonNull(bloodCount, "bloodCount must not be null");
	}
}

