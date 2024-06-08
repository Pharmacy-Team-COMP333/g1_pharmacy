package application;

//Import Statements
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AddDoctorController {

    // FXML Declarations for UI Components
    @FXML private Button AddDoctor2;
    @FXML private TextField DoctorIDTF;
    @FXML private TextField NameTF;
    @FXML private TextField PhoneNumberTF;
    @FXML private TextField SpecializationTF;

    
    @FXML
    void AddDoctor2(ActionEvent event) {
        // Extracting information entered by the user from text fields for doctor details
        int doctorID;
        String name = NameTF.getText();
        String specialization = SpecializationTF.getText();
        String phoneNumber = PhoneNumberTF.getText();

        // Check if any text field is empty
        if (name.isEmpty() || specialization.isEmpty() || phoneNumber.isEmpty()) {
            showAlert("Empty Fields", "Please fill in all fields.");
            return;
        }

        try {
            doctorID = Integer.parseInt(DoctorIDTF.getText());
        } catch (NumberFormatException e) {
            // Handle the case where the doctorID is not a valid integer
            showAlert("Invalid Doctor ID", "Please enter a valid integer for Doctor ID.");
            return;
        }

        // Check if doctorID already exists
        if (isDoctorIdExists(doctorID)) {
            return;
        }

        // Create a new Doctor object
        Doctor newDoctor = new Doctor(doctorID, name, specialization, phoneNumber);
        boolean insertedSuccessfully = insertDoctor(newDoctor);

        if (insertedSuccessfully) {
            showAlert("Doctor Added Successfully", "New doctor added successfully!");
        } else {
            showAlert("Failed to Add Doctor", "Failed to add new doctor.");
        }
    }

    // Check if a doctor with the given ID exists
    private boolean isDoctorIdExists(int doctorID) {
        String query = "SELECT COUNT(*) FROM DOCTOR WHERE doctorID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, doctorID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                // Doctor ID exists, show message box
                showAlert("Duplicate Doctor ID", "Doctor ID already exists. Please enter a different ID.");
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Database Error", "An error occurred while accessing the database.");
        }
        return false;
    }

    // Insert a new doctor into the database
    public boolean insertDoctor(Doctor doctor) {
        String query = "INSERT INTO DOCTOR (doctorID, name, specialization, phoneNumber) VALUES (?, ?, ?, ?)";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, doctor.getDoctorID());
            preparedStatement.setString(2, doctor.getName());
            preparedStatement.setString(3, doctor.getSpecialization());
            preparedStatement.setString(4, doctor.getPhoneNumber());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Database Error", "An error occurred while accessing the database.");
            return false;
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
