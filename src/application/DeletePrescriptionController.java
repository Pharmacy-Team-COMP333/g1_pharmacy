package application;

// Import Statements
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DeletePrescriptionController {

    // FXML Declarations for UI Components
    @FXML private TextField ByPrescriptionID;
    @FXML private Button DeletePrescription;

    // Action event handler for deleting a prescription
    @FXML
    void DeletePrescription(ActionEvent event) {
        String idText = ByPrescriptionID.getText();
        if (!idText.isEmpty()) {
            try {

            	int prescriptionID = Integer.parseInt(idText);
                // Call the deletePrescriptionByID method to delete the prescription
                boolean deleted = deletePrescriptionByID(prescriptionID);
                // Show an alert based on whether the prescription was successfully deleted or not
                if (deleted) {
                    showAlert("Prescription Deleted Successfully");
                } else {
                    showAlert("Failed to delete Prescription. Prescription ID not found.");
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid Prescription ID. Please enter a valid integer.");
            } catch (SQLException e) {
                showAlert("Error occurred while deleting the Prescription.");
            }
        } else {
            showAlert("Please enter Prescription ID.");
        }
    }
    
    // Delete a prescription from the database by its ID
    private boolean deletePrescriptionByID(int prescriptionID) throws SQLException {
        // Delete the prescription record based on the ID
        try (Statement statement = Connector.getConnection().createStatement()) {
            String query = "DELETE FROM PRESCRIPTION WHERE prescriptionID = " + prescriptionID;
            int rowsAffected = statement.executeUpdate(query);
            return rowsAffected > 0;
        }
    }
    
    // Show an alert message to the user
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Delete Prescription");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
