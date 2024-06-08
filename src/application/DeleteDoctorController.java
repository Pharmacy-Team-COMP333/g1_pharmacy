package application;

// Import Statements
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteDoctorController {

    // FXML Declarations for UI Components
    @FXML private TextField ByDoctorID;
    @FXML private Button DeleteDoctor;

    // Action event handler for deleting a doctor
    @FXML
    void DeleteDoctor(ActionEvent event) {
        String idText = ByDoctorID.getText();
        if (!idText.isEmpty()) {
            try {
                int doctorID = Integer.parseInt(idText);
                // Call the deleteDoctorByID method to delete the doctor
                boolean deleted = deleteDoctorByID(doctorID);
                // Show an alert based on whether the doctor was successfully deleted or not
                if (deleted) {
                    showAlert("Doctor Deleted Successfully");
                } else {
                    showAlert("Failed to delete doctor. Doctor ID not found.");
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid Doctor ID. Please enter a valid integer.");
            } catch (SQLException e) {
                showAlert("Error occurred while deleting the doctor.");
            }
        } else {
            showAlert("Please enter Doctor ID.");
        }
    }

    // delete a doctor from the database by its ID
    private boolean deleteDoctorByID(int doctorID) throws SQLException {
        // Check if the doctor ID exists in the prescription table
        boolean existsInPrescriptionTable = false;
        try (Statement statement = Connector.getConnection().createStatement()) {
            String query = "SELECT * FROM prescription WHERE doctorID = " + doctorID;
            existsInPrescriptionTable = statement.executeQuery(query).next();
        }

        // If the doctor ID exists in the prescription table, show a message box and return false
        if (existsInPrescriptionTable) {
            showAlert("Doctor cannot be deleted because they have prescriptions.");
            return false;
        }

        // Delete the doctor record based on the ID
        try (Statement statement = Connector.getConnection().createStatement()) {
            String query = "DELETE FROM doctor WHERE doctorID = " + doctorID;
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
