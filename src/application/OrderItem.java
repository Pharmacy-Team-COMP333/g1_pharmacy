package application;

public class OrderItem {
    private int orderID;
    private int itemID;
    private int quantity;
    private double fullSalePrice;
    private double fullOriginalPrice;

    public OrderItem(int orderID, int itemID, int quantity, double fullSalePrice, double fullOriginalPrice) {
        this.orderID = orderID;
        this.itemID = itemID;
        this.quantity = quantity;
        this.fullSalePrice = fullSalePrice;
        this.fullOriginalPrice = fullOriginalPrice;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getFullSalePrice() {
        return fullSalePrice;
    }

    public void setFullSalePrice(double fullSalePrice) {
        this.fullSalePrice = fullSalePrice;
    }

    public double getFullOriginalPrice() {
        return fullOriginalPrice;
    }

    public void setFullOriginalPrice(double fullOriginalPrice) {
        this.fullOriginalPrice = fullOriginalPrice;
    }
}
