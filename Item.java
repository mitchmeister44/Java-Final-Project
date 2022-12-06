public class Item {
    private String name;
    private double price;
    private int stock;

    Item (String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void restock () {
        this.stock = 10;
    }
    
    public String toString() {
        return String.format("Item Name: %s\nItem Price: $%.2f\nItem Stock: %d",name,price,stock);
    }
}