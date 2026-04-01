class InvaildAgeException extends Exception{
    public InvaildAgeException(String msg){
        super(msg);
    }
}
public class ManualException {
    static void checkAge(int Age) throws InvaildAgeException{
        if (Age<18)
            throw new InvaildAgeException("Age is Less than 18");
        else
            System.out.print("Eligible to vote");

    }


    public static void main(String[] args) {
        try{
            checkAge(16);
        }
        catch(Exception e){
            System.out.print(e);
        }
        
    }

}
