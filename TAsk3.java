
class Product{
    int id;
    String name;
    double price;
    int quantity;

    Product (int id, String name, double price, int quantity){
        this.id = id;
        this.name = name; 
        this.price = price;
        this.quantity = quantity;
    }

}
public class TAsk3{

    public static double getTotalPrice (Product[] cart){
        int sum = 0;
        for (Product ind : cart){
            sum += ind.price;
        }
        return sum;
    }
    public static void display (Product [] cart){
        int i = 1;
        System.out.println("-------------SHOPING CART------------");
        for (Product ind : cart){
            System.out.println("~ PRODUCT "+i+" ID: "+ind.id+" NAME: "+ind.name+" PRICE: "+ind.price+" QUANTITY: "+ind.quantity);
            i++;
        }
        System.out.println("~GRAND TOTAL: "+ getTotalPrice(cart));
    }

    public static void main(String[] args){
        Product p1 = new Product(001, "Pencil", 20.00, 2);
        Product p2 = new Product(010, "Eraser", 10.00, 2);
        Product p3 = new Product(013, "Sharpener", 15.00, 3);
        Product[] cart = new Product[3];
        cart[0] = p1;
        cart[1] = p2;
        cart[2] = p3;
        display(cart);
    }
}