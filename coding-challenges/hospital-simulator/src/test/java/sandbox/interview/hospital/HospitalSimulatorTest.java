package sandbox.interview.hospital;

import lombok.val;
import org.hamcrest.Matchers;
import org.junit.Test;
import sandbox.interview.hospital.domain.Patient;
import sandbox.interview.hospital.domain.PatientState;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static sandbox.interview.hospital.domain.PatientState.DEAD;
import static sandbox.interview.hospital.domain.PatientState.DIABETES;
import static sandbox.interview.hospital.domain.PatientState.FEVER;
import static sandbox.interview.hospital.domain.PatientState.HEALTHY;
import static sandbox.interview.hospital.domain.PatientState.TUBERCULOSIS;
import static sandbox.interview.hospital.tools.ArgumentParser.parseDrugs;
import static sandbox.interview.hospital.tools.ArgumentParser.parsePatients;

public class HospitalSimulatorTest {

    @Test
    public void aspirinCuresFever() {
        val patients = runSimulation("F,F,H,T", "As");
        assertThat(patients, not(contains(FEVER)));
        assertThat(countPatientsWithState(patients, HEALTHY), is(3L));
        assertThat(countPatientsWithState(patients, TUBERCULOSIS), is(1L));
    }

    @Test
    public void antibioticCuresTuberculosis() {
        val patients = runSimulation("T,H,T,X", "An");
        assertThat(patients, not(contains(TUBERCULOSIS)));
        assertThat(countPatientsWithState(patients, HEALTHY), is(3L));
        assertThat(countPatientsWithState(patients, DEAD), is(1L));
    }

    @Test
    public void insulinPreventsDiabeticSubjectFromDying() {
        val patients = runSimulation("T,H,D,X", "I");
        assertThat(countPatientsWithState(patients, TUBERCULOSIS), is(1L));
        assertThat(countPatientsWithState(patients, HEALTHY), is(1L));
        assertThat(countPatientsWithState(patients, DIABETES), is(1L));
        assertThat(countPatientsWithState(patients, DEAD), is(1L));
    }

    @Test
    public void ifInsulinIsMixedWithAntibioticsHealthyPeopleCatchFever() {
        val patients = runSimulation("F,H,D,X", "I,An");
        assertThat(countPatientsWithState(patients, FEVER), is(2L));
        assertThat(countPatientsWithState(patients, DIABETES), is(1L));
        assertThat(countPatientsWithState(patients, DEAD), is(1L));
    }

    @Test
    public void paracetamolCuresFever() {
        val patients = runSimulation("F,F,H,T", "P");
        assertThat(patients, not(contains(FEVER)));
        assertThat(countPatientsWithState(patients, HEALTHY), is(3L));
        assertThat(countPatientsWithState(patients, TUBERCULOSIS), is(1L));
    }

    @Test
    public void paracetamolKillsSubjectIfMixedWithAspirin() {
        val patients = runSimulation("F,H,D,X", "P,As");
        assertThat(countPatientsWithState(patients, DEAD), is(4L));
    }

    @Test
    public void oneTimeInAMillionADeathPatientIsResurrected() {
        val patients = runSimulation("X,H,F,T", "");
        assertThat(countPatientsWithState(patients, DEAD) <= 1L, Matchers.is(true));
        assertThat(countPatientsWithState(patients, HEALTHY) >= 1L, Matchers.is(true));
        assertThat(countPatientsWithState(patients, FEVER), is(1L));
        assertThat(countPatientsWithState(patients, TUBERCULOSIS), is(1L));
    }

    private List<Patient> runSimulation(String patients, String drugs) {
        val simulator = new HospitalSimulator(parsePatients(patients), parseDrugs(drugs));
        simulator.curePatients();
        return simulator.getPatients();
    }

    private long countPatientsWithState(List<Patient> patients, PatientState patientState) {
        return patients.stream()
                .filter(patient -> patientState.equals(patient.getState()))
                .count();
    }
}