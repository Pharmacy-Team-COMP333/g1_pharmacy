package application;

import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class AddDoctorController {

    @FXML
    private Button AddDoctor2;

    @FXML
    private TextField DoctorIDTF;

    @FXML
    private TextField NameTF;

    @FXML
    private TextField PhoneNumberTF;

    @FXML
    private TextField SpecializationTF;

    @FXML
    void AddDoctor2(ActionEvent event) {
        int doctorID;
        String name = NameTF.getText();
        String specialization = SpecializationTF.getText();
        String phoneNumber = PhoneNumberTF.getText();

        // Check if any text field is empty
        if (name.isEmpty() || specialization.isEmpty() || phoneNumber.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Empty Fields");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }

        try {
            doctorID = Integer.parseInt(DoctorIDTF.getText());
        } catch (NumberFormatException e) {
            // Handle the case where the doctorID is not a valid integer
            System.out.println("Invalid doctor ID. Please enter a valid integer.");
            return;
        }
        
        // Check if doctorID already exists
        if (isDoctorIdExists(doctorID)) {
            return; // Exit method if doctorID exists
        }

        Doctor newDoctor = new Doctor(doctorID, name, specialization, phoneNumber);
        boolean insertedSuccessfully = insertDoctor(newDoctor);
        
        if (insertedSuccessfully) {
            // Show message box with the added doctor's information
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Doctor Added Successfully");
            alert.setHeaderText(null);
            alert.setContentText("New doctor added successfully!");
            alert.showAndWait();
        } else {
            System.out.println("Failed to add new doctor.");
            // Optionally, you can show an error dialog box or a message to the user here
        }
    }

    private boolean isDoctorIdExists(int doctorID) {
        String query = "SELECT COUNT(*) FROM DOCTOR WHERE doctorID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, doctorID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                // Doctor ID exists, show message box
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Duplicate Doctor ID");
                alert.setHeaderText(null);
                alert.setContentText("Doctor ID already exists. Please enter a different ID.");
                alert.showAndWait();
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false; // Doctor ID does not exist
    }

    public boolean insertDoctor(Doctor doctor) {
        String query = "INSERT INTO DOCTOR (doctorID, name, specialization, phoneNumber) VALUES (?, ?, ?, ?)";

        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, doctor.getDoctorID());
            preparedStatement.setString(2, doctor.getName());
            preparedStatement.setString(3, doctor.getSpecialization());
            preparedStatement.setString(4, doctor.getPhoneNumber());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                return true; // Indicates success
            } else {
                return false; // Indicates failure
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false; // Indicates failure
        }
    }

    public ObservableList<Doctor> getDoctor(String query) {
        ObservableList<Doctor> doctorsList = FXCollections.observableArrayList();
        Connection conn = Connector.getConnection();

        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Doctor doctor;

            if (!rs.isBeforeFirst()) {
                System.out.println("No records found for the query: " + query);
                return null;
            } else {
                while (rs.next()) {
                    doctor = new Doctor(rs.getInt("doctorID"), rs.getString("name"), rs.getString("specialization"), rs.getString("phoneNumber"));
                    doctorsList.add(doctor);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return doctorsList;
    }
}
