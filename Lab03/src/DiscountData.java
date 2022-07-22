import java.util.Date;

public class DiscountData {
    String name;
    String shop;
    int discountPercent;
    Date expirationDate;

    public DiscountData(String name, String shop, int discountPercent, Date expirationDate) {
        this.name = name;
        this.shop = shop;
        this.discountPercent = discountPercent;
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return String.format("Name: %s\n\nShop: %s\n\nDiscount: %d%%\n\nExpiration Date: %d/%d/%d", name, shop, discountPercent,
                expirationDate.getYear() + 1900, expirationDate.getMonth() + 1, expirationDate.getDate());
    }
}
