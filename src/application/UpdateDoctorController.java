package application;

//Import Statements
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class UpdateDoctorController {

    // FXML Declarations for UI Components
    @FXML private TextField DoctorIDTF;
    @FXML private TextField NameTF;
    @FXML private TextField PhoneNumberTF;
    @FXML private TextField SpecializationTF;
    @FXML private Button UpdateDoctorB;



    // Action event handler for updating Doctor information
    @FXML
    void UpdateDoctorB(ActionEvent event) {
        try {
            int doctorID = Integer.parseInt(DoctorIDTF.getText());
            if (!isDoctorIdExists(doctorID)) {
                showAlert("Doctor ID Not Found", "Doctor ID does not exist. Please enter a valid ID.");
                return;
            }

            updateDoctor(doctorID);

        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid Doctor ID.");
        }
    }
    

    // Check if a doctor with the given ID exists in the database
    private boolean isDoctorIdExists(int doctorID) {
        String query = "SELECT COUNT(*) FROM DOCTOR WHERE doctorID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, doctorID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException ex) {
            showAlert("Database Error", "An error occurred while accessing the database.");
        }
        return false;
    }

    
 // Update the doctor information in the database based on the given doctor ID
    private void updateDoctor(int doctorID) {
        // Initialize variables to hold current values of doctor attributes
        String currentName = null;
        String currentPhoneNumber = null;
        String currentSpecialization = null;

        // SQL query to fetch the current values of the doctor attributes
        String fetchQuery = "SELECT name, phoneNumber, specialization FROM DOCTOR WHERE doctorID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(fetchQuery)) {
            // Set the parameter in the prepared statement to the given doctor ID
            preparedStatement.setInt(1, doctorID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                currentName = resultSet.getString("name");
                currentPhoneNumber = resultSet.getString("phoneNumber");
                currentSpecialization = resultSet.getString("specialization");
            }
        } catch (SQLException ex) {
            showAlert("Database Error", "An error occurred while accessing the database.");
        }

        // Determine the updated values for the doctor attributes based on user input
        String updatedName = NameTF.getText().isEmpty() ? currentName : NameTF.getText();
        String updatedPhoneNumber = PhoneNumberTF.getText().isEmpty() ? currentPhoneNumber : PhoneNumberTF.getText();
        String updatedSpecialization = SpecializationTF.getText().isEmpty() ? currentSpecialization : SpecializationTF.getText();

        // SQL query to update the doctor information in the database
        String updateQuery = "UPDATE DOCTOR SET name = ?, phoneNumber = ?, specialization = ? WHERE doctorID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(updateQuery)) {
        	
            preparedStatement.setString(1, updatedName);
            preparedStatement.setString(2, updatedPhoneNumber);
            preparedStatement.setString(3, updatedSpecialization);
            preparedStatement.setInt(4, doctorID);

            // Execute the update query and retrieve the number of rows affected
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Success", "Doctor data updated successfully.");
            } else {
                showAlert("Failure", "Failed to update doctor data.");
            }
        } catch (SQLException ex) {
            showAlert("Database Error", "An error occurred while updating the doctor data.");
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
