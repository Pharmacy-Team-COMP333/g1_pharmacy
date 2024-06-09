package application;

public class EmployeeOrder {
    private String employeeName;
    private int ordersNumber;

    public EmployeeOrder(String employeeName, int ordersNumber) {
        this.employeeName = employeeName;
        this.ordersNumber = ordersNumber;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public int getOrdersNumber() {
        return ordersNumber;
    }

    public void setOrdersNumber(int ordersNumber) {
        this.ordersNumber = ordersNumber;
    }
}
