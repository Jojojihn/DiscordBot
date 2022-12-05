package databaseStuff;

public class ShopItem {
    public String name;
    public String description;
    public int price;
    public String image;
    public String id;

    public ShopItem(String name, String description, int price, String image, String id) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.id = id;
    }
}
