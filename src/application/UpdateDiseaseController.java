package application;

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

    @FXML
    private TextField Description;

    @FXML
    private TextField DiseaseID;

    @FXML
    private TextField Name;

    @FXML
    private TextField Treatment;

    @FXML
    private Button UpdateDisease2;

    @FXML
    void Description(ActionEvent event) {

    }

    @FXML
    void DiseaseID(ActionEvent event) {

    }

    @FXML
    void Name(ActionEvent event) {

    }

    @FXML
    void Treatment(ActionEvent event) {

    }


    
    @FXML
    void UpdateDisease2(ActionEvent event) {
        try {
            int diseaseID = Integer.parseInt(DiseaseID.getText());
            if (!isDiseaseIdExists(diseaseID)) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Disease ID Not Found");
                alert.setHeaderText(null);
                alert.setContentText("Disease ID does not exist. Please enter a valid ID.");
                alert.showAndWait();
                return;
            }

            updateDisease(diseaseID);

        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid Disease ID.");
            alert.showAndWait();
        }
    }
    
    private boolean isDiseaseIdExists(int diseaseID) {
        String query = "SELECT COUNT(*) FROM DISEASE WHERE diseaseID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, diseaseID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private void updateDisease(int diseaseID) {
        String currentName = null;
        String currentDescription = null;
        String currentTreatment = null;

        String fetchQuery = "SELECT name, description, treatment FROM DISEASE WHERE diseaseID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(fetchQuery)) {
            preparedStatement.setInt(1, diseaseID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                currentName = resultSet.getString("name");
                currentDescription = resultSet.getString("description");
                currentTreatment = resultSet.getString("treatment");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        String updatedName = Name.getText().isEmpty() ? currentName : Name.getText();
        String updatedDescription = Description.getText().isEmpty() ? currentDescription : Description.getText();
        String updatedTreatment = Treatment.getText().isEmpty() ? currentTreatment : Treatment.getText();

        String updateQuery = "UPDATE DISEASE SET name = ?, description = ?, treatment = ? WHERE diseaseID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, updatedName);
            preparedStatement.setString(2, updatedDescription);
            preparedStatement.setString(3, updatedTreatment);
            preparedStatement.setInt(4, diseaseID);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Disease data updated successfully.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Failure");
                alert.setHeaderText(null);
                alert.setContentText("Failed to update disease data.");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
