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

    private boolean isDiseaseIdExists(int id) {
        try (Connection conn = Connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM disease WHERE Disease_ID = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertDisease(Disease disease) {
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO DISEASE (diseaseID, name, description, treatment) VALUES (?, ?, ?, ?)")
        ) {
            preparedStatement.setInt(1, disease.getDiseaseID());
            preparedStatement.setString(2, disease.getName());
            preparedStatement.setString(3, disease.getDescription());
            preparedStatement.setString(4, disease.getTreatment());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0; // Indicates success if rows were inserted
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false; // Indicates failure
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return false; // Indicates failure
        }
    }

    // You can add other methods for handling database operations here
}
