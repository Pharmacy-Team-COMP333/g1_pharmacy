package application;

public class customer {
          private int customerID;
          private String name;
          private String contactInfo;
          static customer cus = null;
		public customer() {
			super();
		}
		
		
		public customer(int customerID, String name, String contactInfo) {
			super();
			this.customerID = customerID;
			this.name = name;
			this.contactInfo = contactInfo;
		}
		public int getCustomerID() {
			return customerID;
		}
		public void setCustomerID(int customerID) {
			this.customerID = customerID;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getContactInfo() {
			return contactInfo;
		}
		public void setContactInfo(String contactInfo) {
			this.contactInfo = contactInfo;
		}
          
}
