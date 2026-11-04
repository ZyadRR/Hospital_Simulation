public class TreatmentRooms {
    private final Semaphore rooms;
    private static final long TIMEOUT_SECONDS = 3;

    public TreatmentRooms(int numberOfRooms) {
        rooms = new Semaphore(numberOfRooms);
    }

    public boolean tryAcquireRoom() throws InterruptedException {
        return rooms.TryAcquireIn(TIMEOUT_SECONDS);
    }

    public void releaseRoom() {
        rooms.Release();
    }

    public int numOfTR() {
        return rooms.numberOfRooms();
    }
}
