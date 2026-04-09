class Product{
    String name;
    double rating;
    int price;
    Product(String name, double rating, int price){
        this.name = name;
        this.rating = rating;
        this.price = price;
    }
    @Override
    public String toString(){
        return name + " - " + price;
    }
}
public class Task {
    public static void main(String[] args) {
        List<Product> list = new ArrayList<>();
        list.add(new Product("Laptop", 4.5, 50000));
        list.add(new Product("Phone", 4.2, 20000));
        list.add(new Product("Tablet", 4.8, 10000));
        Collections.sort(list);
        for (Product p : list) {
            System.out.println(p.name + " " + p.rating + " " + p.price);
        }
    }
}

