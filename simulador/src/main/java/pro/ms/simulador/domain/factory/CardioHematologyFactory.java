package pro.ms.simulador.domain.factory;

import org.springframework.stereotype.Component;
import pro.ms.simulador.domain.DeviceType;
import pro.ms.simulador.domain.medicalPayload.CardioHematologyPayload;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountMedicalPayload;
import pro.ms.simulador.domain.medicalPayload.bloodCount.BloodCountPanel;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPanel;
import pro.ms.simulador.domain.medicalPayload.lipids.LipidPayload;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPanel;
import pro.ms.simulador.domain.medicalPayload.metabolic.MetabolicPayload;
import pro.ms.simulador.domain.medicalPayload.units.ConcentrationUnit;
import pro.ms.simulador.domain.medicalPayload.units.CountingUnit;
import pro.ms.simulador.domain.payloadGenerator.CardioHematologyGenerator;
import pro.ms.simulador.domain.payloadGenerator.PayloadGenerator;

@Component
public class CardioHematologyFactory implements PayloadFactory<CardioHematologyPayload> {
    @Override
    public CardioHematologyPayload initialState() {
        throw new IllegalArgumentException("CARDIO_HEMATOLOGY requiere userId");
    }

    @Override
    public CardioHematologyPayload initialState(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId es obligatorio para CARDIO_HEMATOLOGY");
        }

        MetabolicPanel metabolic = new MetabolicPanel(
                95.0,
                ConcentrationUnit.MG_DL,
                1.0,
                14.0,
                5.2,
                7.40,
                9.4
        );

        LipidPanel lipids = new LipidPanel(
                185.0,
                ConcentrationUnit.MG_DL,
                135.0,
                ConcentrationUnit.MG_DL
        );

        BloodCountPanel bloodCount = new BloodCountPanel(
                14.0,
                ConcentrationUnit.G_DL,
                7200,
                CountingUnit.CELLS_MCL,
                250000,
                CountingUnit.CELLS_MCL,
                105.0,
                ConcentrationUnit.MG_DL
        );

        return new CardioHematologyPayload(new MetabolicPayload(metabolic),
                                           new LipidPayload(lipids),
                                           new BloodCountMedicalPayload(bloodCount));
    }

    @Override
    public PayloadGenerator<CardioHematologyPayload> generator() {
        return new CardioHematologyGenerator();
    }

    @Override
    public DeviceType deviceType() {
        return DeviceType.CARDIO_HEMATOLOGY;
    }
}
