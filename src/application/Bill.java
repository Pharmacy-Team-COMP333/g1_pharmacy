package application;

public class Bill {
    private int  billID ;
    private int totalPrice;
    private int  profit;
    private String billType;
    private int orderID;
    private int paymentMethodID;

    public Bill() {
        super();
    }

    public Bill(int  billID ,int totalPrice ,int  profit, String billType , int orderID , int paymentMethodID) {
        super();
        this.setBillID(billID);
        this.setTotalPrice(totalPrice);
        this.setProfit(profit);
        this.setBillType(billType) ;
        this.setOrderID(orderID);
        this.setPaymentMethodID(paymentMethodID);
        
    }

	public int getBillID() {
		return billID;
	}

	public void setBillID(int billID) {
		this.billID = billID;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getProfit() {
		return profit;
	}

	public void setProfit(int profit) {
		this.profit = profit;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public int getPaymentMethodID() {
		return paymentMethodID;
	}

	public void setPaymentMethodID(int paymentMethodID) {
		this.paymentMethodID = paymentMethodID;
	}

}
