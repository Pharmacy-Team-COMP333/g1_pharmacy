package application;

// Import Statements
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class UpdatePrescriptionController {

    // FXML Declarations for UI Components
    @FXML private TextField CustomerID;
    @FXML private DatePicker Date;
    @FXML private TextField DoctorID;
    @FXML private TextField Dosage;
    @FXML private TextField Instructions;
    @FXML private TextField PrescriptionID;
    @FXML private Button UpdateP;

    // Action event handler for updating Prescription information
    @FXML
    void UpdateP(ActionEvent event) {
        try {
            int prescriptionID = Integer.parseInt(PrescriptionID.getText());
            if (!isPrescriptionIdExists(prescriptionID)) {
                showAlert("Prescription ID Not Found", "Prescription ID does not exist. Please enter a valid ID.");
                return;
            }

            updatePrescription(prescriptionID);

        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid Prescription ID.");
        }

    }

    // Check if a prescription with the given ID exists in the database
    private boolean isPrescriptionIdExists(int prescriptionID) {
        String query = "SELECT COUNT(*) FROM PRESCRIPTION WHERE prescriptionID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, prescriptionID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException ex) {
            showAlert("Database Error", "An error occurred while accessing the database.");
        }
        return false;
    }

 // Update the prescription information in the database based on the given prescription ID
    private void updatePrescription(int prescriptionID) {
        // Initialize variables to hold current values of prescription attributes
        Date currentDate = null;
        String currentDosage = null;
        String currentInstructions = null;
        Integer currentDoctorID = 0;
        Integer currentCustomerID = 0;

        // SQL query to fetch the current values of the prescription attributes
        String fetchQuery = "SELECT prescriptionDate, dosage, instructions, doctorID, customerID FROM PRESCRIPTION WHERE prescriptionID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(fetchQuery)) {
            preparedStatement.setInt(1, prescriptionID);
            ResultSet resultSet = preparedStatement.executeQuery();

            // If the result set has a next row, retrieve the current values of prescription attributes
            if (resultSet.next()) {
                currentDate = resultSet.getDate("prescriptionDate");
                currentDosage = resultSet.getString("dosage");
                currentInstructions = resultSet.getString("instructions");
                currentDoctorID = resultSet.getInt("doctorID");
                currentCustomerID = resultSet.getInt("customerID");
            }
        } catch (SQLException ex) {
            showAlert("Database Error", "An error occurred while accessing the database.");
        }

        // Create a StringBuilder to hold the names of fields that are not updated
        StringBuilder notUpdatedFields = new StringBuilder();

        // Determine the updated values for the prescription attributes based on user input
        String updatedDate = Date.getValue() == null ? currentDate.toString() : Date.getValue().toString();
        if (Date.getValue() == null) {
            notUpdatedFields.append("Date, ");
        }

        String updatedDosage = Dosage.getText().isEmpty() ? currentDosage : Dosage.getText();
        if (Dosage.getText().isEmpty()) {
            notUpdatedFields.append("Dosage, ");
        }

        String updatedInstructions = Instructions.getText().isEmpty() ? currentInstructions : Instructions.getText();
        if (Instructions.getText().isEmpty()) {
            notUpdatedFields.append("Instructions, ");
        }

        Integer updatedDoctorID = DoctorID.getText().isEmpty() ? currentDoctorID : Integer.parseInt(DoctorID.getText());
        if (DoctorID.getText().isEmpty()) {
            notUpdatedFields.append("DoctorID, ");
        }

        Integer updatedCustomerID = CustomerID.getText().isEmpty() ? currentCustomerID : Integer.parseInt(CustomerID.getText());
        if (CustomerID.getText().isEmpty()) {
            notUpdatedFields.append("CustomerID, ");
        }

        // Check if the updated doctor ID exists in the database
        if (!isDoctorExists(updatedDoctorID)) {
            // Show an alert if the doctor with the updated ID does not exist
            showAlert("Doctor Not Found", "Doctor with ID " + updatedDoctorID + " does not exist.");
            return;
        }

        // Check if the updated customer ID exists in the database
        if (!isCustomerExists(updatedCustomerID)) {
            // Show an alert if the customer with the updated ID does not exist
            showAlert("Customer Not Found", "Customer with ID " + updatedCustomerID + " does not exist.");
            return;
        }

        // SQL query to update the prescription information in the database
        String updateQuery = "UPDATE PRESCRIPTION SET prescriptionDate = ?, dosage = ?, instructions = ?, doctorID = ?, customerID = ? WHERE prescriptionID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, updatedDate);
            preparedStatement.setString(2, updatedDosage);
            preparedStatement.setString(3, updatedInstructions);
            preparedStatement.setInt(4, updatedDoctorID);
            preparedStatement.setInt(5, updatedCustomerID);
            preparedStatement.setInt(6, prescriptionID);

            // Execute the update query and retrieve the number of rows affected
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Success", "Prescription data updated successfully.");

                // If any fields were not updated, show an alert with the names of those fields
                if (notUpdatedFields.length() > 0) {
                    Alert notUpdatedAlert = new Alert(AlertType.INFORMATION);
                    notUpdatedAlert.setTitle("Fields Not Updated");
                    notUpdatedAlert.setHeaderText(null);
                    notUpdatedAlert.setContentText("The following fields were not updated and kept as old: " + notUpdatedFields.substring(0, notUpdatedFields.length() - 2));
                    notUpdatedAlert.showAndWait();
                }
            } else {
                showAlert("Failure", "Failed to update prescription data.");
            }
        } catch (SQLException ex) {
            showAlert("Database Error", "An error occurred while updating the prescription data.");
        }
    }


        // Check if a doctor with the given ID exists in the database
        private boolean isDoctorExists(int doctorID) {
            String query = "SELECT COUNT(*) FROM DOCTOR WHERE doctorID = ?";
            try (Connection conn = Connector.getConnection();
                 PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, doctorID);
                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next() && resultSet.getInt(1) > 0;
            } catch (SQLException ex) {
                showAlert("Database Error", "An error occurred while accessing the database.");
                return false; // Unable to determine if the doctor exists, assume it does not
            }
        }

        // Check if a customer with the given ID exists in the database
        private boolean isCustomerExists(int customerID) {
            String query = "SELECT COUNT(*) FROM CUSTOMER WHERE customerID = ?";
            try (Connection conn = Connector.getConnection();
                 PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, customerID);
                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next() && resultSet.getInt(1) > 0;
            } catch (SQLException ex) {
                showAlert("Database Error", "An error occurred while accessing the database.");
                return false; // Unable to determine if the customer exists, assume it does not
            }
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

       
