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

public class UpdateDoctorController {

    @FXML
    private TextField DoctorIDTF;

    @FXML
    private TextField NameTF;

    @FXML
    private TextField PhoneNumberTF;

    @FXML
    private TextField SpecializationTF;

    @FXML
    private Button UpdateDoctorB;

    @FXML
    void DoctorIDTF(ActionEvent event) {
        // Handle action if needed
    }

    @FXML
    void NameTF(ActionEvent event) {
        // Handle action if needed
    }

    @FXML
    void PhoneNumberTF(ActionEvent event) {
        // Handle action if needed
    }

    @FXML
    void SpecializationTF(ActionEvent event) {
        // Handle action if needed
    }

    @FXML
    void UpdateDoctorB(ActionEvent event) {
        try {
            int doctorID = Integer.parseInt(DoctorIDTF.getText());
            if (!isDoctorIdExists(doctorID)) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Doctor ID Not Found");
                alert.setHeaderText(null);
                alert.setContentText("Doctor ID does not exist. Please enter a valid ID.");
                alert.showAndWait();
                return;
            }

            updateDoctor(doctorID);

        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid Doctor ID.");
            alert.showAndWait();
        }
    }

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
            ex.printStackTrace();
        }
        return false;
    }

    private void updateDoctor(int doctorID) {
        String currentName = null;
        String currentPhoneNumber = null;
        String currentSpecialization = null;

        String fetchQuery = "SELECT name, phoneNumber, specialization FROM DOCTOR WHERE doctorID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(fetchQuery)) {
            preparedStatement.setInt(1, doctorID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                currentName = resultSet.getString("name");
                currentPhoneNumber = resultSet.getString("phoneNumber");
                currentSpecialization = resultSet.getString("specialization");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        String updatedName = NameTF.getText().isEmpty() ? currentName : NameTF.getText();
        String updatedPhoneNumber = PhoneNumberTF.getText().isEmpty() ? currentPhoneNumber : PhoneNumberTF.getText();
        String updatedSpecialization = SpecializationTF.getText().isEmpty() ? currentSpecialization : SpecializationTF.getText();

        String updateQuery = "UPDATE DOCTOR SET name = ?, phoneNumber = ?, specialization = ? WHERE doctorID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, updatedName);
            preparedStatement.setString(2, updatedPhoneNumber);
            preparedStatement.setString(3, updatedSpecialization);
            preparedStatement.setInt(4, doctorID);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Doctor data updated successfully.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Failure");
                alert.setHeaderText(null);
                alert.setContentText("Failed to update doctor data.");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
