import java.util.Scanner;

public class Hospital {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Waiting room capacity: ");
        int waitingRoomCapacity = scanner.nextInt();

        System.out.print("Number of doctors: ");
        int numDoctors = scanner.nextInt();

        System.out.print("Number of patients: ");
        int numPatients = scanner.nextInt();
        scanner.close();

        StringBuilder sb = new StringBuilder("Patients arriving:\n");
        Patient[] patients = new Patient[numPatients];
        for (int i = 1; i <= numPatients; i++) {
            sb.append("P").append(i).append(i == numPatients ? "" : " ");
        }
        System.out.println(sb.toString().trim() + "\n");

        WaitingRoom waitingRoom = new WaitingRoom(waitingRoomCapacity);
        TreatmentRooms treatmentRooms = new TreatmentRooms(2);

        Thread[] doctorThreads = new Thread[numDoctors];
        for (int i = 1; i <= numDoctors; i++) {
            Doctor doctor = new Doctor(i, waitingRoom, treatmentRooms);
            doctorThreads[i - 1] = new Thread(doctor);
            doctorThreads[i - 1].start();
        }

        Thread[] patientThreads = new Thread[numPatients];
        for (int i = 1; i <= numPatients; i++) {
            patients[i - 1] = new Patient(waitingRoom);
            patientThreads[i - 1] = new Thread(patients[i - 1]);
            patientThreads[i - 1].start();
            Thread.sleep(200);
        }

        while (waitingRoom.numOfWR() < waitingRoomCapacity || treatmentRooms.numOfTR() < 2) {
            Thread.sleep(1000);
        }

        System.out.println("All patients have been treated.");
        System.out.println("Simulation completed.");
    }
}