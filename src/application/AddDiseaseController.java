package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddDiseaseController {

    @FXML
    private Button AddDisease2;

    @FXML
    private TextField Description;

    @FXML
    private TextField DiseaseID;

    @FXML
    private TextField Name;

    @FXML
    private TextField Treatment;

    @FXML
    void AddDisease2(ActionEvent event) {
        int diseaseID;
        String name = Name.getText();
        String description = Description.getText();
        String treatment = Treatment.getText();

        // Check if any text field is empty
        if (name.isEmpty() || description.isEmpty() || treatment.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Empty Fields");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }

        try {
            diseaseID = Integer.parseInt(DiseaseID.getText());
        } catch (NumberFormatException e) {
            // Handle the case where the diseaseID is not a valid integer
            System.out.println("Invalid disease ID. Please enter a valid integer.");
            return;
        }

        // Check if diseaseID already exists
        if (isDiseaseIdExists(diseaseID)) {
            return; // Exit method if diseaseID exists
        }

        Disease newDisease = new Disease(diseaseID, name, description, treatment);
        boolean insertedSuccessfully = insertDisease(newDisease);

        if (insertedSuccessfully) {
            // Show message box with the added disease's information
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Disease Added Successfully");
            alert.setHeaderText(null);
            alert.setContentText("New disease added successfully!");
            alert.showAndWait();
        } else {
            System.out.println("Failed to add new disease.");
            // Optionally, you can show an error dialog box or a message to the user here
        }
    }

    private boolean isDiseaseIdExists(int diseaseID) {
        String query = "SELECT COUNT(*) FROM DISEASE WHERE diseaseID = ?";
        try (Connection conn = Connector.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, diseaseID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                // disease ID exists, show message box
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Duplicate Disease ID");
                alert.setHeaderText(null);
                alert.setContentText("Disease ID already exists. Please enter a different ID.");
                alert.showAndWait();
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false; // Disease ID does not exist
    }

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

    // You can add other methods for handling database operations here
}
