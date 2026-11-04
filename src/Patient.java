
public class Patient extends Thread {

    private static int id = 1;

    private int patientId;
    private String name;

    private WaitingRoom waitingRoom;

    public Patient(WaitingRoom waitingRoom) {

        this.patientId = id;
        this.name = "P" + id;

        id++;

        this.waitingRoom = waitingRoom;
    }

    public long getId() {
        return patientId;
    }

    public String getPatientName() {
        return name;
    }

    @Override
    public void run() {

        System.out.println(name + " arrived.");

        try {

            waitingRoom.addPatient(this);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            System.out.println(name + " was interrupted.");
        }
    }
}
