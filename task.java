package day11;
//Task:Ecommerce Order
//DEsign System where user place order,while oderining
//System must handel failures using Exception handling
//System must handel failures using Exception handling
//1.Product out of stock
//2.Payment failure
//3.Order processing failed
//Classes
//Product (name,stock,price)
//user(name,address)
//order(product,user,Quantity)
//orderService(placeOrder(order))
class Product{
    private String name;
    private int stock;
    private double price;

    public Product(String name, int stock, double price) {
        this.name = name;
        this.stock = stock;
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public int getStock() {
        return stock;
    }
    public double getPrice() {
        return price;
    }
    public void reduceStock(int quantity) {
        this.stock -= quantity;
    }
}
class User{
    private String name;
    private String address;

    public User(String name, String address) {
        this.name = name;
        this.address = address;
    }
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
}
class Order{
    private Product product;
    private User user;
    private int quantity;

    public Order(Product product, User user, int quantity) {
        this.product = product;
        this.user = user;
        this.quantity = quantity;
    }
    public Product getProduct() {
        return product;
    }
    public User getUser() {
        return user;
    }
    public int getQuantity() {
        return quantity;
    }
}
class OrderService{
    public void placeOrder(Order order) throws Exception {
        Product product = order.getProduct();
        if (product.getStock() < order.getQuantity()) {
            throw new Exception("Product out of stock.");
        }
        // Simulate payment failure
        if (Math.random() < 0.5) {
            throw new Exception("Payment failure.");
        }
        // Simulate order processing failure
        if (Math.random() < 0.5) {
            throw new Exception("Order processing failed.");
        }
        product.reduceStock(order.getQuantity());
        System.out.println("Order placed successfully for " + order.getQuantity() + " " + product.getName());
    }
}
public class task {
    public static void main(String[] args) {
        Product product = new Product("Laptop", 19, 999.99);
        User user = new User("Alice", "123 Main St");
        Order order = new Order(product, user, 19);  
        OrderService orderService = new OrderService();
        try {
            orderService.placeOrder(order);
        } catch (Exception e) {
            System.out.println("Failed to place order: " + e.getMessage());

        }
}
}
