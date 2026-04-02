class Locker{
    private int lockerID;
    private int pin;
    private boolean isLocked;
    private String item;

    public void unlock(int pin){
        if (this.pin == pin){
            isLocked = false;
            System.out.println("Lock open!");
        }else{
            System.out.println("Invalid Pin");
        }   
    }

    public void lock(){
        isLocked = true;
    }

    public void store(String item){
        if (!isLocked ){
            this.item = item;
        } else if (isLocked) {
            System.out.println("Locker Locked");
        } 
    }

    public void retrieve(){
        if (!isLocked && item != null){
            System.out.println("Item : "+item+" retrieved");
            item=null;
        } else if (isLocked) {
            System.out.println("Locker Locked");
        } 
    }

    Locker(int lockerID, int pin){
        this.lockerID = lockerID;
        this.pin = pin;
        isLocked = true;
    }
}
public class LockerTask{
    public static void main(String[] args){
        System.out.println("Locker System");
        Locker l1 = new Locker(1, 776);
        l1.unlock(776);
        l1.store("Ball");
        l1.retrieve();
        l1.lock();
    }
}