package sandbox.interview.hospital;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sandbox.interview.hospital.domain.Drug;
import sandbox.interview.hospital.domain.Patient;
import sandbox.interview.hospital.domain.PatientState;

import java.util.List;
import java.util.Random;

import static sandbox.interview.hospital.domain.Drug.ANTIBIOTIC;
import static sandbox.interview.hospital.domain.Drug.ASPIRIN;
import static sandbox.interview.hospital.domain.Drug.INSULIN;
import static sandbox.interview.hospital.domain.Drug.PARACETAMOL;
import static sandbox.interview.hospital.domain.PatientState.DEAD;
import static sandbox.interview.hospital.domain.PatientState.DIABETES;
import static sandbox.interview.hospital.domain.PatientState.FEVER;
import static sandbox.interview.hospital.domain.PatientState.HEALTHY;
import static sandbox.interview.hospital.domain.PatientState.TUBERCULOSIS;

@AllArgsConstructor
class HospitalSimulator {
    @Getter
    private final List<Patient> patients;
    private final List<Drug> drugs;

    void curePatients() {
        cureFeverWhenAspirinOrParacetamolIsAdministered();
        cureTuberculosisWhenAntibioticIsAdministered();
        killDiabeticPeopleWhenInsulinIsNotAdministered();
        makeHealthyPeopleHaveFeverWhenInsulinAndAntibioticIsAdministered();
        killPatientsWhenParacetamolAndAspirinIsAdministered();
        tryToResurrectDeadPatients();
    }

    private void cureFeverWhenAspirinOrParacetamolIsAdministered() {
        if (drugs.contains(ASPIRIN) || drugs.contains(PARACETAMOL)) {
            changeHealthState(FEVER, HEALTHY);
        }
    }

    private void cureTuberculosisWhenAntibioticIsAdministered() {
        if (drugs.contains(ANTIBIOTIC)) {
            changeHealthState(TUBERCULOSIS, HEALTHY);
        }
    }

    private void killDiabeticPeopleWhenInsulinIsNotAdministered() {
        if (!drugs.contains(INSULIN)) {
            changeHealthState(DIABETES, DEAD);
        }
    }

    private void makeHealthyPeopleHaveFeverWhenInsulinAndAntibioticIsAdministered() {
        if (drugs.contains(INSULIN) && drugs.contains(ANTIBIOTIC)) {
            changeHealthState(HEALTHY, FEVER);
        }
    }

    private void changeHealthState(PatientState targetDisease, PatientState newState) {
        patients.stream()
                .filter(patient -> targetDisease.equals(patient.getState()))
                .forEach(patient -> patient.setState(newState));
    }

    private void killPatientsWhenParacetamolAndAspirinIsAdministered() {
        if (drugs.contains(PARACETAMOL) && drugs.contains(ASPIRIN)) {
            patients.forEach(patient -> patient.setState(DEAD));
        }
    }

    private void tryToResurrectDeadPatients() {
        patients.stream()
                .filter(patient -> DEAD.equals(patient.getState()))
                .forEach(this::resurrectADeadPatientOneTimeInAMillion);
    }

    private void resurrectADeadPatientOneTimeInAMillion(Patient patient) {
        if (new Random().nextInt(1_000_000) == 123_456) {
            patient.setState(HEALTHY);
        }
    }
}
