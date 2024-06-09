package application;

public class ItemSales {
    private String itemName;
    private int numberOfSales;
    private double salesValue;

    public ItemSales(String itemName, int numberOfSales, double salesValue) {
        this.itemName = itemName;
        this.numberOfSales = numberOfSales;
        this.salesValue = salesValue;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getNumberOfSales() {
        return numberOfSales;
    }

    public void setNumberOfSales(int numberOfSales) {
        this.numberOfSales = numberOfSales;
    }

    public double getSalesValue() {
        return salesValue;
    }

    public void setSalesValue(double salesValue) {
        this.salesValue = salesValue;
    }
}
