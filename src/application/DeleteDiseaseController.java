package application;

// Import Statements
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DeleteDiseaseController {

    // FXML Declarations for UI Components
    @FXML private TextField ByDiseaseID;
    @FXML private Button DeleteDisease2;

    // Action event handler for deleting a disease
    @FXML
    void DeleteDisease2(ActionEvent event) {

    	String idText = ByDiseaseID.getText();
        if (!idText.isEmpty()) {
            try {

            	int diseaseID = Integer.parseInt(idText);
                // Call the deleteDiseaseByID method to delete the disease
                boolean deleted = deleteDiseaseByID(diseaseID);
                // Show an alert based on whether the disease was successfully deleted or not
                if (deleted) {
                    showAlert("Disease Deleted Successfully");
                } else {
                    showAlert("Failed to delete disease. Disease ID not found.");
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid Disease ID. Please enter a valid integer.");
            } catch (SQLException e) {
                showAlert("Error occurred while deleting the disease.");
            }
        } else {
            showAlert("Please enter Disease ID.");
        }
    }
    
    // delete a disease from the database by its ID
    private boolean deleteDiseaseByID(int diseaseID) throws SQLException {
        // Delete the disease record based on the ID
        try (Statement statement = Connector.getConnection().createStatement()) {
            String query = "DELETE FROM DISEASE WHERE diseaseID = " + diseaseID;
            int rowsAffected = statement.executeUpdate(query);
            return rowsAffected > 0;
        }
    }
    
    //  show an alert message
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Delete Disease");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
