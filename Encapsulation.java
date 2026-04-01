class BankAccount{
    private double balance;
    public void setBalance(double balance){
        if (balance>0){
            this.balance = balance;
        }else{
            System.out.println("Input not valid");
        }
    }

    public double getBalance(){
        return balance;
    }

    public void withdraw (double amount, BankAccount obj){
        if (amount <= balance && amount >= 0){
            balance -= amount;
            System.out.println("Rs."+amount+" debited succesfully");
            System.out.println("Current Balance : "+obj.getBalance());
        } else {
            if (amount > balance){
                System.out.println("Not enough Balance!");
            } else {
                System.out.println("Invalid amount");
            }
        }
    }

    public void deposit (double amount, BankAccount obj){
        if (amount >= 0){
            balance += amount;
            System.out.println("Rs."+amount+" credited succesfully");
            System.out.println("Current Balance : "+obj.getBalance());
        } else {
            System.out.println("Invalid amount");
        }
    }
        
}


public class Encapsulation{
    public static void main(String[] args){
        BankAccount b = new BankAccount();
        b.setBalance(1000.34);
        System.out.println(b.getBalance());
        b.withdraw(20.00    ,b);
        b.deposit(500.00,b);
    }
}