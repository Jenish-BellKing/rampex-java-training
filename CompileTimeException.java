package day11;

public class CompileTimeException {
    public static void main(String[] args) {
    Thread T=new Thread(()->{
        try{
            System.out.println("Thread sleep");
            Thread.sleep(2000);
            System.out.println("Thread wake up");
        }
        catch(InterruptedException e){
            System.out.println(e);
        }
    });
    T.start();
    T.interrupt();
     try{
         Thread.sleep(4000);
     }
     catch(Exception e){
         System.out.println("Not T");
}
T.interrupt();
}
}