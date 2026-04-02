class ProductOutOfStock extends Exception{
    public ProductOutOfStock (String msg){
        super(msg);
    }
}

class PaymentFailed extends Exception{
    public PaymentFailed (String msg){
        super(msg);
    }
}

class OrderProcessingFailed extends Exception{
    public OrderProcessingFailed(String msg){
        super(msg);
    }
}



class Product{
    String name;
    int stock;
    double price;
    Product(String name,int stock,double price){
        this.name=name;
        this.stock=stock;
        this.price=price;
    }

}


class User{
    String name;
    String address;
    User(String name,String address){
        this.name=name;
        this.address=address;
    }
}

class Order {
    Product product;
    User user;
    int quantity;
    Order(Product P,User U,int quantity){
        this.product=P;
        this.user=U;
        this.quantity=quantity;
    }
}

class OrderService{
 
    static void placeOrder(Order O) throws ProductOutOfStock,PaymentFailed,OrderProcessingFailed {
            if(O.product.stock <= 0 || O.quantity>O.product.stock)
                throw new ProductOutOfStock("No Stock!");
            if(Math.random()<0.4)
                throw new PaymentFailed("Payment is failed");
            if(Math.random()<0.2)
                throw new PaymentFailed("Processing is failed");
            O.product.stock-=O.quantity;
            System.out.println("Order Placed Successfully");

    }
}







public class EcomException {

     static void placeOrder(Order O) throws ProductOutOfStock,PaymentFailed,OrderProcessingFailed {
            if(O.product.stock <= 0)
                throw new ProductOutOfStock("No Stock!");
            if(Math.random()<0.4)
                throw new PaymentFailed("Payment is failed");
            if(Math.random()<0.2)
                throw new PaymentFailed("Processing is failed");
            O.product.stock-=O.quantity;
            System.out.println("Order Placed Successfully");

    }
    public static void main(String[] args) {
        Product p1=new Product("Shampoo",5,10.00);
        Product p2=new Product("comb",10,25.00);
        User u1 = new User("Doremon","dolukpur");
        Order O1 = new Order(p1,u1,3);

        try {
            placeOrder(O1);
        } catch (ProductOutOfStock e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (PaymentFailed e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (OrderProcessingFailed e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
