
public class Patient extends Thread {

    private static int id = 1;

    private int patientId;
    private String name;

    private WaitingRoom waitingRoom;
    private boolean m_isNull;
    public Patient(WaitingRoom waitingRoom) {
        
        this.patientId = id;
        this.name = "P" + id;
        id++;
        this.m_isNull = false;
        this.waitingRoom = waitingRoom;
    }

    private Patient() {
        this.patientId = -1;
        this.name = "POISON";
        this.waitingRoom = null;
        this.m_isNull = true;
    }

    public static Patient createNull() {
        return new Patient();
    }

    public boolean isNull() {
        return m_isNull;
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
