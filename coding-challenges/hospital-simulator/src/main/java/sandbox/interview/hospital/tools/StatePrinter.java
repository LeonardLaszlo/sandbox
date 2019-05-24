package sandbox.interview.hospital.tools;

import lombok.val;
import sandbox.interview.hospital.domain.Patient;
import sandbox.interview.hospital.domain.PatientState;

import java.util.List;

import static java.lang.String.format;
import static sandbox.interview.hospital.domain.PatientState.DEAD;
import static sandbox.interview.hospital.domain.PatientState.DIABETES;
import static sandbox.interview.hospital.domain.PatientState.FEVER;
import static sandbox.interview.hospital.domain.PatientState.HEALTHY;
import static sandbox.interview.hospital.domain.PatientState.TUBERCULOSIS;

public class StatePrinter {
    private StatePrinter() {
    }

    public static void printState(List<Patient> patients) {
        val state = format("F:%d,H:%d,D:%d,T:%d,X:%d",
                countPatientsWithState(patients, FEVER),
                countPatientsWithState(patients, HEALTHY),
                countPatientsWithState(patients, DIABETES),
                countPatientsWithState(patients, TUBERCULOSIS),
                countPatientsWithState(patients, DEAD));
        System.out.println(state);
    }

    private static long countPatientsWithState(List<Patient> patients, PatientState patientState) {
        return patients.stream()
                .filter(patient -> patientState.equals(patient.getState()))
                .count();
    }
}
