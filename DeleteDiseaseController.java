package application;

import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DeleteDiseaseController {

    @FXML
    private TextField ByDiseaseID;

    @FXML
    private Button DeleteDisease2;

    @FXML
    void ByDiseaseID(ActionEvent event) {

    }


    
    @FXML
    void DeleteDisease2(ActionEvent event) {
        String idText = ByDiseaseID.getText();
        if (!idText.isEmpty()) {
            try {
                int diseaseID = Integer.parseInt(idText);
                boolean deleted = deleteDiseaseByID(diseaseID);
                if (deleted) {
                    showAlert("Disease Deleted Successfully");
                } else {
                    showAlert("Failed to delete disease. Disease ID not found.");
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid Disease ID. Please enter a valid integer.");
            } catch (SQLException e) {
                showAlert("Error occurred while deleting the disease: " + e.getMessage());
            }
        } else {
            showAlert("Please enter Disease ID.");
        }
    }
    
    private boolean deleteDiseaseByID(int diseaseID) throws SQLException {
        
        // Delete the doctor record based on the ID
        try (Statement statement = Connector.getConnection().createStatement()) {
            String query = "DELETE FROM DISEASE WHERE diseaseID = " + diseaseID;
            int rowsAffected = statement.executeUpdate(query);
            return rowsAffected > 0;
        }catch (SQLException ex) {
            ex.printStackTrace();
            return false; // Indicates failure
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return false; // Indicates failure
        }
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Delete Disease");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
