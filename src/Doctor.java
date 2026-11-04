import java.util.Random;

public class Doctor {
    int doctorid;
    WaitingRoom waitingRoom;
    TreatmentRooms treatmentRooms;
    Random random = new Random();
    public Doctor(int id,WaitingRoom wr,TreatmentRooms tr){
        this.doctorid=id;
        this.treatmentRooms=tr;
        this.waitingRoom=wr;
    }
    void log(String msg){
        System.out.println(msg);
    }
   public void runDoctor(){
     try {
         while (true){

             Patient patient = waitingRoom.takepatient();
             if(patient==null){
                 break;
             }
             log("Doctor "+doctorid+ " is assigned to P"+Patient.getid());
             log("Doctor "+doctorid+" is trying to acquire a treatment room");

             boolean acquired = treatmentRooms.tryAcquireRoom();
             if(!acquired){
                 log("Doctor " + doctorid + " could not acquire a treatment room.");
                 log("Doctor " + doctorid + " is sending P" + patient.getId() + " back to the waiting room.");
                 waitingRoom.returnPatient(patient);
                 log("Doctor " + doctorid + " is now available.");
                 continue;
             }
             try {
                 log("Doctor " + doctorid + " acquired a treatment room.");
                 log("Doctor " + doctorid + " begins treating P" + patient.getId() + ".");
                 Thread.sleep(2000 + random.nextInt(3000)); // 2 to 5 seconds
                 log("Doctor " + doctorid + " finished treating P" + patient.getId() + ".");
             }
             finally {
                 treatmentRooms.releaseRoom();
                 log("Doctor " + doctorid + " released the treatment room.");
             }
             log("Doctor " + id + " is now available.");
             log("P" + patient.getId() + " left.");
             waitingRoom.patientTreated();
         }

     }
     catch (InterruptedException e) {
         Thread.currentThread().interrupt();
     }

    }
}
