import java.util.Scanner;

public class Hospital {

    private static int m_remainingPatients;
    private static int m_doctorsCount;
    private static WaitingRoom m_waitingRoom;

    private static Semaphore m_completionLock = new Semaphore(1);

    public static void PatientFinished() throws InterruptedException {
        boolean finished = false;

        m_completionLock.Acquire();
        try {
            m_remainingPatients--;

            if (m_remainingPatients == 0) {
                finished = true;
            }
        }
        finally {
            m_completionLock.Release();
        }

        if (finished) {
            for (int i = 0; i < m_doctorsCount; i++) {
                m_waitingRoom.addPatient(Patient.createNull());
            }
        }
    }

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
        System.out.println(sb.toString().trim());

        WaitingRoom waitingRoom = new WaitingRoom(waitingRoomCapacity);
        TreatmentRooms treatmentRooms = new TreatmentRooms(2);

        m_remainingPatients = numPatients;
        m_doctorsCount = numDoctors;
        m_waitingRoom = waitingRoom;

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
        
        for (Thread patient : patientThreads) {
            patient.join();
        }

        for (Thread doctor : doctorThreads) {
            doctor.join();
        }

        System.out.println("All patients have been treated.");
        System.out.println("Simulation completed.");
    }
}