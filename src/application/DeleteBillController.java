package application;

// Import Statements
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DeleteBillController {

    // FXML Declarations for UI Components
    @FXML private TextField Bill_ID;
    @FXML private Button DeleteBill;


    // Action event handler for deleting a bill
    @FXML
    void DeleteBill(ActionEvent event) {

    	String idText = Bill_ID.getText();
        if (!idText.isEmpty()) {
            try {

            	int billID = Integer.parseInt(idText);
                // Call the deleteBillByID method to delete the bill
                boolean deleted = deleteBillByID(billID);
                // Show an alert based on whether the bill was successfully deleted or not
                if (deleted) {
                    showAlert("Bill Deleted Successfully");
                } else {
                    showAlert("Failed to delete bill. Bill ID not found.");
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid Bill ID. Please enter a valid integer.");
            } catch (SQLException e) {
                showAlert("Error occurred while deleting the bill ");
            }
        } else {
            showAlert("Please enter Bill ID.");
        }
    }
    
    // delete a bill from the database by its ID
    private boolean deleteBillByID(int billID) throws SQLException {
        // Delete the BILL record based on the ID
        try (Statement statement = Connector.getConnection().createStatement()) {
            String query = "DELETE FROM BILL WHERE billID = " + billID;
            int rowsAffected = statement.executeUpdate(query);
            return rowsAffected > 0;
        }
    }
    
    // show an alert message
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Delete Doctor");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
