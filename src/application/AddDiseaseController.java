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

public class AddDiseaseController {

    // FXML Declarations for UI Components
    @FXML private Button AddDisease2;
    @FXML private TextField Description;
    @FXML private TextField DiseaseID;
    @FXML private TextField Name;
    @FXML private TextField Treatment;

    // Action event handler for adding a new disease
    @FXML
    void AddDisease2(ActionEvent event) {
        // Extracting information entered by the user from text fields for disease details
        int diseaseID;
        String name = Name.getText();
        String description = Description.getText();
        String treatment = Treatment.getText();

        // Check if any text field is empty
        if (name.isEmpty() || description.isEmpty() || treatment.isEmpty()) {
            showAlert("Empty Fields", "Please fill in all fields.");
            return;
        }

        try {
            diseaseID = Integer.parseInt(DiseaseID.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid Disease ID", "Please enter a valid integer for Disease ID.");
            return;
        }

        // Check if diseaseID already exists
        if (isDiseaseIdExists(diseaseID)) {
            showAlert("Duplicate Disease ID", "Disease ID already exists. Please enter a different ID.");
            return;
        }

        // Create a new Disease object
        Disease newDisease = new Disease(diseaseID, name, description, treatment);
        boolean insertedSuccessfully = insertDisease(newDisease);

        if (insertedSuccessfully) {
            showAlert("Disease Added Successfully", "New disease added successfully!");
        } else {
            showAlert("Failed to Add Disease", "Failed to add new disease.");
        }
    }

    // Check if a disease with the given ID exists
    private boolean isDiseaseIdExists(int diseaseID) {
        String query = "SELECT COUNT(*) FROM DISEASE WHERE diseaseID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, diseaseID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                // disease ID exists
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false; // Disease ID does not exist
    }

    // Insert a new disease into the database
    public boolean insertDisease(Disease disease) {
        String query = "INSERT INTO DISEASE (diseaseID, name, description, treatment) VALUES (?, ?, ?, ?)";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, disease.getDiseaseID());
            preparedStatement.setString(2, disease.getName());
            preparedStatement.setString(3, disease.getDescription());
            preparedStatement.setString(4, disease.getTreatment());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0; // Indicates success if rows were inserted
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false; // Indicates failure
        }
    }

    // Show an alert dialog with the given title and content
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
