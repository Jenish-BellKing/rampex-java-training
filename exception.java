package day11;
//Exceptional handling ->thing that disrupts the normal flow of the program
//Open GPAY
//QR Scanner
//Enter the amount 
//pay GPAY Backend-> NPC_> Account balance check-> if balance is less than the amount then throw an exception

//java.lang.object
//java.lang.Throwable
//error&exception
//error-> JVM related issue-> OutOfMemoryError, StackOverflowError , VirtualMachineError
  //System level error developer cannot handle this error
//exception-> user defined exception,
    // ->checked exception, (compile time exception) -> file not found exception, class not found exception, SQL exception
    // ->unchecked exception(runtime exception)-> null pointer exception, array index out of bound exception, arithmetic exception, number format exception
//Program or environament around causing error
//developers must handle this exception
//try->Execute the risky part of the code
//Catch->work when risky part of code fails
//Finally->Always execute at last
//throw->used to manually creating 
//throws->used to declare the exception that a method can throw

public class exception {
    public static void main(String[] args) {
        try{
        /*int a=10;
        int b=0;
        int c=a/b;*/
        String s=null;
        System.out.println(s.length());
    }catch (ArithmeticException e){
        System.out.println("Cannot divide by zero.");
    }
        catch(NullPointerException e){
            System.out.println("Null pointer exception occurred.");
        }
        finally{
            System.out.println("This block will always execute.");
        }
    }
}
