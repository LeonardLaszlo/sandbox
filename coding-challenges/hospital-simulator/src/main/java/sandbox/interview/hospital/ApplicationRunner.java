package sandbox.interview.hospital;

import lombok.val;

import static sandbox.interview.hospital.tools.ArgumentParser.parseDrugs;
import static sandbox.interview.hospital.tools.ArgumentParser.parsePatients;
import static sandbox.interview.hospital.tools.StatePrinter.printState;

class ApplicationRunner {
    public static void main(String[] args) {
        if (args.length == 2) {
            startSimulation(args[0], args[1]);
        } else if (args.length == 1) {
            startSimulation(args[0], "");
        } else {
            System.err.println("Please provide the list of patients and optionally the list of drugs for treatment");
        }
    }

    private static void startSimulation(String patients, String drugs) {
        val simulator = new HospitalSimulator(parsePatients(patients), parseDrugs(drugs));
        simulator.curePatients();
        printState(simulator.getPatients());
    }
}