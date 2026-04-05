package day11;
class InvalidAgeException extends Exception{
    InvalidAgeException(String message){
        super(message);
    }
}
public class ManualException {
    static void checkAge(int age)throws InvalidAgeException{
        if(age<18){
            throw new InvalidAgeException("Age must be at least 18 to vote.");
        }
        else{
            System.out.println("You are eligible to vote.");
        }
    }
    public static void main(String[]args){
        try{
            checkAge(16);
        }catch(InvalidAgeException e){
            System.out.println(e.getMessage());
        }
    }
}
