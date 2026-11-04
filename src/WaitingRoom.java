import java.util.LinkedList;
import java.util.Queue;

public class WaitingRoom {

    private Queue<Patient> patients;

    private Semaphore EmptyWaitingRoom;
    private Semaphore fullWaitingRoom;
    private Semaphore mutex;

    public WaitingRoom(int capacity) {

        patients = new LinkedList<>();

        EmptyWaitingRoom = new Semaphore(capacity);
        fullWaitingRoom = new Semaphore(0);
        mutex = new Semaphore(1);
    }

    public void addPatient(Patient patient) throws InterruptedException {

        if (!EmptyWaitingRoom.TryAcquire()) {

           if (!patient.isNull()) {
                System.out.println("The waiting room is full.");
                System.out.println(patient.getPatientName() + " is waiting outside for an available seat.");
            }
            EmptyWaitingRoom.Acquire();
        }

        mutex.Acquire();
        patients.add(patient);

        if (!patient.isNull()) {
            System.out.println(patient.getPatientName() + " entered the waiting room.");
        }

        mutex.Release();

        fullWaitingRoom.Release();
    }

    public Patient removePatient() throws InterruptedException {

        fullWaitingRoom.Acquire();
        mutex.Acquire();

        Patient patient = patients.remove();

        mutex.Release();
        EmptyWaitingRoom.Release();
        return patient;
    }

    public int numOfWR() {
        return EmptyWaitingRoom.numberOfRooms();
    }
}