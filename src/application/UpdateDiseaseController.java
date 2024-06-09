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
import javafx.scene.control.Alert.AlertType;

public class UpdateDiseaseController {

    // FXML Declarations for UI Components
    @FXML private TextField Description;
    @FXML private TextField DiseaseID;
    @FXML private TextField Name;
    @FXML private TextField Treatment;
    @FXML private Button UpdateDisease2;

    // Action event handler for updating disease information
    @FXML
    void UpdateDisease2(ActionEvent event) {
        try {
            int diseaseID = Integer.parseInt(DiseaseID.getText());
            // Check if the entered disease ID exists
            if (!isDiseaseIdExists(diseaseID)) {
                showAlert("Disease ID Not Found", "Disease ID does not exist. Please enter a valid ID.");
                return;
            }

            // Update the disease with the provided disease ID
            updateDisease(diseaseID);

        } catch (NumberFormatException e) {
            // Handle the case where the input for disease ID is not a valid integer
            showAlert("Invalid Input", "Please enter a valid Disease ID.");
        }
    }

    // Check if a disease with the given ID exists in the database
    private boolean isDiseaseIdExists(int diseaseID) {
        String query = "SELECT COUNT(*) FROM DISEASE WHERE diseaseID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, diseaseID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            showAlert("Database Error", "An error occurred while accessing the database.");
        }
        return false;
    }

    // Update the disease information in the database based on the given disease ID
    private void updateDisease(int diseaseID) {
        String currentName = null;
        String currentDescription = null;
        String currentTreatment = null;

        // SQL query to fetch the current values of the disease attributes
        String fetchQuery = "SELECT name, description, treatment FROM DISEASE WHERE diseaseID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(fetchQuery)) {
            preparedStatement.setInt(1, diseaseID);
            ResultSet resultSet = preparedStatement.executeQuery();

            // If the result set has a next row, retrieve the current values of disease attributes
            if (resultSet.next()) {
                currentName = resultSet.getString("name");
                currentDescription = resultSet.getString("description");
                currentTreatment = resultSet.getString("treatment");
            }
        } catch (SQLException ex) {
            showAlert("Database Error", "An error occurred while accessing the database.");
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Determine the updated values for the disease attributes based on user input
        String updatedName = Name.getText().isEmpty() ? currentName : Name.getText();
        String updatedDescription = Description.getText().isEmpty() ? currentDescription : Description.getText();
        String updatedTreatment = Treatment.getText().isEmpty() ? currentTreatment : Treatment.getText();

        // SQL query to update the disease information in the database
        String updateQuery = "UPDATE DISEASE SET name = ?, description = ?, treatment = ? WHERE diseaseID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(updateQuery)) {
            // Set parameters in the prepared statement to the updated values
            preparedStatement.setString(1, updatedName);
            preparedStatement.setString(2, updatedDescription);
            preparedStatement.setString(3, updatedTreatment);
            preparedStatement.setInt(4, diseaseID);

            int rowsAffected = preparedStatement.executeUpdate(); // Execute the update query and retrieve the number of rows affected

            if (rowsAffected > 0) {
                showAlert("Success", "Disease data updated successfully.");
            } else {
                showAlert("Failure", "Failed to update disease data.");
            }
        } catch (SQLException ex) {
            showAlert("Database Error", "An error occurred while updating the disease data.");
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
