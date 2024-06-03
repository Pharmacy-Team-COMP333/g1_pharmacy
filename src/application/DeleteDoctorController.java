package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.sql.Statement;

public class DeleteDoctorController {

    @FXML
    private TextField ByDoctorID;

    @FXML
    private Button DeleteDoctor;

    @FXML
    void DeleteDoctor(ActionEvent event) {
        String idText = ByDoctorID.getText();
        if (!idText.isEmpty()) {
            try {
                int doctorID = Integer.parseInt(idText);
                boolean deleted = deleteDoctorByID(doctorID);
                if (deleted) {
                    showAlert("Doctor Deleted Successfully");
                } else {
                    showAlert("Failed to delete doctor. Doctor ID not found.");
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid Doctor ID. Please enter a valid integer.");
            } catch (SQLException e) {
                showAlert("Error occurred while deleting the doctor: " + e.getMessage());
            }
        } else {
            showAlert("Please enter Doctor ID.");
        }
    }

    private boolean deleteDoctorByID(int doctorID) throws SQLException {
        // Check if the doctor ID exists in the prescription table
        boolean existsInPrescriptionTable = false;
        try (Statement statement = Connector.getConnection().createStatement()) {
            String query = "SELECT * FROM prescription WHERE doctorID = " + doctorID;
            existsInPrescriptionTable = statement.executeQuery(query).next();
        }

        // If the doctor ID exists in the prescription table, show a message box and return false
        if (existsInPrescriptionTable) {
            // Show a message box indicating that the doctor cannot be deleted
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

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Delete Doctor");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
