package model;

public class Stock {
    public long id;
    public long price;
    public long cnt;

    public Stock(long id, long price, long cnt) {
        this.id = id;
        this.price = price;
        this.cnt = cnt;
    }

    public String toString() {
        return "Stock{id: " + id + ", price: " + price + ", cnt: " + cnt + "}";
    }
}
