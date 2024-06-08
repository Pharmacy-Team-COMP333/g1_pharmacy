package application;

public class payment_method {
             private int paymentMethodID;
             private String name;
             static payment_method pay = null;
             
			public payment_method() {
				super();
				// TODO Auto-generated constructor stub
			}

			public payment_method(int paymentMethodID, String name) {
				super();
				this.paymentMethodID = paymentMethodID;
				this.name = name;
			}

			public int getPaymentMethodID() {
				return paymentMethodID;
			}

			public void setPaymentMethodID(int paymentMethodID) {
				this.paymentMethodID = paymentMethodID;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public static payment_method getPay() {
				return pay;
			}

			public static void setPay(payment_method pay) {
				payment_method.pay = pay;
			}
			
             
}
