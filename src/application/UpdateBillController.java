package application;

//Import Statements
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class UpdateBillController {

    // FXML Declarations for UI Components
    @FXML private TextField BillType; 
    @FXML private TextField Bill_ID; 
    @FXML private TextField OrderID; 
    @FXML private TextField PaymentMethodID; 
    @FXML private TextField Profit; 
    @FXML private TextField TotalPrice; 
    @FXML private Button UpdateBill;
    
    // Action event handler for updating a bill
    @FXML
    void UpdateBill(ActionEvent event) {
        try {
            int billID = Integer.parseInt(Bill_ID.getText());
            // Check if the entered bill ID exists
            if (!isBillIdExists(billID)) {
                // Show an alert if the bill ID does not exist
                showAlert("Bill ID Not Found", "Bill ID does not exist. Please enter a valid ID.");
                return; 
            }

            // Update the bill with the provided bill ID
            updateBill(billID);

        } catch (NumberFormatException e) {
            // Handle the case where the input for bill ID is not a valid integer
            showAlert("Invalid Input", "Please enter a valid Bill ID.");
        }
    }

    // Check if a bill with the given ID exists in the database
    private boolean isBillIdExists(int billID) {
        String query = "SELECT COUNT(*) FROM BILL WHERE billID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, billID);
            
            ResultSet resultSet = preparedStatement.executeQuery(); // Execute the query and retrieve the result set
            // Check if the result set has a next row and the count of records is greater than 0
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException ex) {
            // Show an alert for database errors
            showAlert("Database Error", "An error occurred while accessing the database.");
        }
        return false;
    }

    
    // Update the bill information in the database based on the given bill ID
    private void updateBill(int billID) {
        // Variables to store the current values of bill attributes
        int currentTotalPrice = 0;
        int currentProfit = 0;
        String currentBillType = null;
        int currentOrderID = 0;
        int currentPaymentMethodID = 0;

        // SQL query to fetch the current values of the bill attributes
        String fetchQuery = "SELECT totalPrice, profit, billType, orderID, paymentMethodID FROM BILL WHERE billID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(fetchQuery)) {
            preparedStatement.setInt(1, billID);
            ResultSet resultSet = preparedStatement.executeQuery(); // Execute the query and retrieve the result set

            // If the result set has a next row, retrieve the current values of bill attributes
            if (resultSet.next()) {
                currentTotalPrice = resultSet.getInt("totalPrice");
                currentProfit = resultSet.getInt("profit");
                currentBillType = resultSet.getString("billType");
                currentOrderID = resultSet.getInt("orderID");
                currentPaymentMethodID = resultSet.getInt("paymentMethodID");
            }
        } catch (SQLException ex) {
            // Show an alert for database errors
            showAlert("Database Error", "An error occurred while accessing the database.");
        }

        // Determine the updated values for the bill attributes based on user input
        String updatedTotalPrice = TotalPrice.getText().isEmpty() ? String.valueOf(currentTotalPrice) : TotalPrice.getText();
        String updatedProfit = Profit.getText().isEmpty() ? String.valueOf(currentProfit) : Profit.getText();
        String updatedBillType = BillType.getText().isEmpty() ? currentBillType : BillType.getText();
        int updatedOrderID = OrderID.getText().isEmpty() ? currentOrderID : Integer.parseInt(OrderID.getText());
        int updatedPaymentMethodID = PaymentMethodID.getText().isEmpty() ? currentPaymentMethodID : Integer.parseInt(PaymentMethodID.getText());

        // Check if the updated Order ID exists in the database
        if (!isOrderIdExists(updatedOrderID)) {
            // Show an alert if the order ID does not exist
            showAlert("Order ID Not Found", "The specified Order ID does not exist.");
            return;
        }

        // Check if the updated Payment Method ID exists in the database
        if (!isPaymentMethodIdExists(updatedPaymentMethodID)) {
            // Show an alert if the payment method ID does not exist
            showAlert( "Payment Method ID Not Found", "The specified Payment Method ID does not exist.");
            return;
        }

        // SQL query to update the bill information in the database
        String updateQuery = "UPDATE BILL SET totalPrice = ?, profit = ?, billType = ?, orderID = ?, paymentMethodID = ? WHERE billID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(updateQuery)) {
            // Set parameters in the prepared statement to the updated values
            preparedStatement.setString(1, updatedTotalPrice);
            preparedStatement.setString(2, updatedProfit);
            preparedStatement.setString(3, updatedBillType);
            preparedStatement.setInt(4, updatedOrderID);
            preparedStatement.setInt(5, updatedPaymentMethodID);
            preparedStatement.setInt(6, billID);

            int rowsAffected = preparedStatement.executeUpdate(); // Execute the update query and retrieve the number of rows affected

            if (rowsAffected > 0) {
                showAlert("Success", "Bill data updated successfully.");
            } else {
                showAlert("Failure", "Failed to update bill data.");
            }
        } catch (SQLException ex) {
            // Handle different types of SQL exceptions and show appropriate alerts
            showAlert("Database Error", "An error occurred while updating the bill data.");
        }
    }


    // Check if an order with the given ID exists in the ORDER_TABLE
    private boolean isOrderIdExists(int orderId) {
        // SQL query to count the number of records with the specified order ID
        String query = "SELECT COUNT(*) FROM ORDER_TABLE WHERE orderID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException ex) {
            // Show an alert for database errors
            showAlert("Database Error", "An error occurred while accessing the database.");
        }
        return false;
    }

    
    // Check if a payment method with the given ID exists in the PAYMENT_METHOD table
    private boolean isPaymentMethodIdExists(int paymentMethodId) {
        // SQL query to count the number of records with the specified payment method ID
        String query = "SELECT COUNT(*) FROM PAYMENT_METHOD WHERE paymentMethodID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, paymentMethodId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException ex) {
            // Show an alert for database errors
            showAlert("Database Error", "An error occurred while accessing the database.");
        }
        return false;
    }

    // Show an alert dialog with the given title and content
    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

