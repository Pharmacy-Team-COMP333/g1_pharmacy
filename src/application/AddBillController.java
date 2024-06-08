package application;

// Import Statements
import java.sql.Connection;          
import java.sql.PreparedStatement;   
import java.sql.ResultSet;           
import java.sql.SQLException;       
import javafx.event.ActionEvent;    
import javafx.fxml.FXML;             
import javafx.scene.control.Alert;   
import javafx.scene.control.Button;  
import javafx.scene.control.TextField; 
import javafx.scene.image.ImageView; 




public class AddBillController {
    
    // FXML Declarations for UI Components
    @FXML private ImageView AddBillIcon;
    @FXML private Button AddNewBill;
    @FXML private TextField BillIDTF;
    @FXML private TextField BillTypeTF;
    @FXML private TextField OrderIDTF;
    @FXML private TextField PaymentMethodIDTF;
    @FXML private TextField ProfitTF;
    @FXML private TextField TotalPriceTF;

    
    // Action event handler for adding a new bill
    @FXML
    void AddNewBill(ActionEvent event) {
        // Variables to store values
        int billID, orderID, paymentMethodID, profit, totalPrice;
        String billType;

        try {
            // Converting text field values to integers
            billID = Integer.parseInt(BillIDTF.getText());
            orderID = Integer.parseInt(OrderIDTF.getText());
            paymentMethodID = Integer.parseInt(PaymentMethodIDTF.getText());
            profit = Integer.parseInt(ProfitTF.getText());
            totalPrice = Integer.parseInt(TotalPriceTF.getText());
        } catch (NumberFormatException e) {
            // Handling invalid input
            showAlert("Invalid Input", "Please enter valid numbers for Bill ID, Order ID, Payment Method ID, Profit, and Total Price.");
            return;
        }
        
        // Getting the billType from text field
        billType = BillTypeTF.getText();

        // Check if any text field is empty
        if (billType.isEmpty() || BillIDTF.getText().isEmpty() || OrderIDTF.getText().isEmpty() || PaymentMethodIDTF.getText().isEmpty() || ProfitTF.getText().isEmpty() || TotalPriceTF.getText().isEmpty()) {
            showAlert("Empty Fields", "Please fill in all fields.");
            return;
        }

        // Check if the billID is unique
        if (billExists(billID)) {
            showAlert("Duplicate Bill ID", "A bill with this Bill ID already exists. Please use a different Bill ID.");
            return;
        }

        // Check if the orderID exists in the order_table
        if (!orderExists(orderID)) {
            showAlert("Order Not Found", "The specified Order ID does not exist.");
            return;
        }

        // Check if the paymentMethodID exists in the payment_method table
        if (!paymentMethodExists(paymentMethodID)) {
            showAlert("Payment Method Not Found", "The specified Payment Method ID does not exist.");
            return;
        }

        // Check if the orderID is unique in the BILL table
        if (orderIDExistsInBill(orderID)) {
            showAlert("Duplicate Order ID", "The specified Order ID already exists in the BILL table.");
            return;
        }

        // Create a new Bill object
        Bill newBill = new Bill(billID, totalPrice, profit, billType, orderID, paymentMethodID);
        
        // Insert the new Bill into the database
        boolean insertedSuccessfully = insertBill(newBill);

        if (insertedSuccessfully) {
            showAlert("Bill Added Successfully", "New bill added successfully!");
        } else {
            showAlert("Failed to Add Bill", "Failed to add new bill.");
        }
    }

    
    
    
    // Check if a bill with the given ID exists
    private boolean billExists(int billID) {
        String query = "SELECT 1 FROM BILL WHERE billID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, billID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if a record with the billID exists
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false; // On error, assume the bill does not exist
        }
    }

    
    
    
    // Check if an order with the given ID exists
    private boolean orderExists(int orderID) {
        String query = "SELECT 1 FROM order_table WHERE orderID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, orderID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if a record with the orderID exists
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false; // On error, assume the order does not exist
        }
    }
    
    
    
    
    // Check if an order with the given ID exists in the BILL table
    private boolean orderIDExistsInBill(int orderID) {
        String query = "SELECT 1 FROM BILL WHERE orderID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, orderID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if a record with the orderID exists in BILL
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false; // On error, assume the orderID does not exist in BILL
        }
    }

    
    
    
    // Check if a payment method with the given ID exists
    private boolean paymentMethodExists(int paymentMethodID) {
        String query = "SELECT 1 FROM payment_method WHERE paymentMethodID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, paymentMethodID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if a record with the paymentMethodID exists
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false; // On error, assume the payment method does not exist
        }
    }

    
    
    
    // Insert a new Bill into the database
    private boolean insertBill(Bill bill) {
        String query = "INSERT INTO BILL (billID, totalPrice, profit, billType, orderID, paymentMethodID) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

                    preparedStatement.setInt(1, bill.getBillID());
                    preparedStatement.setInt(2, bill.getTotalPrice());
                    preparedStatement.setInt(3, bill.getProfit());
                    preparedStatement.setString(4, bill.getBillType());
                    preparedStatement.setInt(5, bill.getOrderID());
                    preparedStatement.setInt(6, bill.getPaymentMethodID());

                    int rowsInserted = preparedStatement.executeUpdate();
                    return rowsInserted > 0; // Indicates success if rowsInserted is greater than 0
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return false; // Indicates failure
                }
            }
            
            
    
    
     //  show an alert dialog with the given title and content
     private void showAlert(String title, String content) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION); 
                alert.setTitle(title);
                alert.setHeaderText(null);
                alert.setContentText(content);
                alert.showAndWait();
            }
        }
