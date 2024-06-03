package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class DoctorController {
    @FXML private TableView<Doctor> DoctorTable;
    @FXML private TableColumn<Doctor, Integer> DoctorIDColumn;
    @FXML private TableColumn<Doctor, String> NameColumn;
    @FXML private TableColumn<Doctor, String> SpecializationColumn;
    @FXML private TableColumn<Doctor, String> PhoneNumberColumn;
    @FXML private TextField Search;
    @FXML private TextField PhoneNumberTF;
    @FXML private TextField SpecializationTF;
    @FXML private TextField DoctorIDTF;
    @FXML private TextField NameTF;
    @FXML private Button AddDoctor;
    @FXML private Button DeleteDoctor;
    @FXML private Button UpdateDoctor;
    @FXML private Button SearchB;

    private ObservableList<Doctor> doctorsList = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        // Initialize table columns
        DoctorIDColumn.setCellValueFactory(new PropertyValueFactory<>("doctorID"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        SpecializationColumn.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        PhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        // Initialize doctorsList
        doctorsList = FXCollections.observableArrayList();

        // Load data into table
        loadDataIntoTable();
    }

    private void loadDataIntoTable() {
        // Fetch data from database
        ObservableList<Doctor> doctors = fetchDataFromDatabase();

        // Populate the doctorsList with the fetched data
        doctorsList.setAll(doctors);

        // Set the items in the table view
        DoctorTable.setItems(doctorsList);
    }

    private ObservableList<Doctor> fetchDataFromDatabase() {
        ObservableList<Doctor> doctors = FXCollections.observableArrayList();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Get connection from Connector class
            connection = Connector.getConnection();

            // Prepare the SQL statement
            String query = "SELECT * FROM doctor";
            statement = connection.prepareStatement(query);

            // Execute the query
            resultSet = statement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                int doctorID = resultSet.getInt("doctorID");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                String phoneNumber = resultSet.getString("phoneNumber");

                // Create a Doctor object and add it to the list
                Doctor doctor = new Doctor(doctorID, name, specialization, phoneNumber);
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the resources
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return doctors;
    }

    @FXML
    void AddDoctor(ActionEvent event) {
        openStage("/AddDoctor.fxml", "Add New Doctor");
    }

    @FXML
    void DeleteDoctor(ActionEvent event) {
        openStage("/DeleteDoctor.fxml", "Delete Doctor");
    }

    @FXML
    void UpdateDoctor(ActionEvent event) {
        openStage("/UpdateDoctor.fxml", "Update Doctor");
    }

    private void openStage(String fxmlPath, String title) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void SelectDoctor() {
        // Get the selected doctor from the table
        Doctor selectedDoctor = DoctorTable.getSelectionModel().getSelectedItem();

        // Check if a doctor is selected
        if (selectedDoctor != null) {
            // Populate the text fields with the selected doctor's data
            DoctorIDTF.setText(String.valueOf(selectedDoctor.getDoctorID()));
            NameTF.setText(selectedDoctor.getName());
            SpecializationTF.setText(selectedDoctor.getSpecialization());
            PhoneNumberTF.setText(selectedDoctor.getPhoneNumber());
        } else {
            // If no doctor is selected, show an alert
            showAlert("Please select a doctor from the table.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void Refresh() {
        loadDataIntoTable();
    }

    @FXML
    void SearchB(ActionEvent event) {
        String searchText = Search.getText().toLowerCase();
        doctorsList.clear();
        try (Connection conn = Connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM doctor WHERE doctorID LIKE ? OR name LIKE ? OR specialization LIKE ? OR phoneNumber LIKE ?")) {
            stmt.setString(1, "%" + searchText + "%");
            stmt.setString(2, "%" + searchText + "%");
            stmt.setString(3, "%" + searchText + "%");
            stmt.setString(4, "%" + searchText + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                doctorsList.add(new Doctor(
                        rs.getInt("doctorID"),
                        rs.getString("name"),
                        rs.getString("specialization"),
                        rs.getString("phoneNumber")
                ));
            }
            DoctorTable.setItems(doctorsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void UpdateSelectedDoctor() {
        // Get the selected doctor from the table
        Doctor selectedDoctor = DoctorTable.getSelectionModel().getSelectedItem();

        // Check if a doctor is selected
        if (selectedDoctor != null) {
            // Retrieve the updated data from the text fields
            String name = NameTF.getText();
            String specialization = SpecializationTF.getText();
            String phoneNumber = PhoneNumberTF.getText();

            // Update the selected doctor with the new data
            selectedDoctor.setName(name);
            selectedDoctor.setSpecialization(specialization);
            selectedDoctor.setPhoneNumber(phoneNumber);

            // Update the doctor record in the database
            if (updateDoctor(selectedDoctor)) {
                // If the update is successful, refresh the table
                loadDataIntoTable();
                showAlert("Doctor updated successfully.");
            } else {
                showAlert("Failed to update doctor.");
            }
        } else {
            // If no doctor is selected, show an alert
            showAlert("Please select a doctor from the table.");
        }
    }

    private boolean updateDoctor(Doctor doctor) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Get connection from Connector class
            connection = Connector.getConnection();

            // Prepare the SQL statement
            String query = "UPDATE doctor SET name = ?, specialization = ?, phoneNumber = ? WHERE doctorID = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, doctor.getName());
            statement.setString(2, doctor.getSpecialization());
            statement.setString(3, doctor.getPhoneNumber());
            statement.setInt(4, doctor.getDoctorID());

            // Execute the update statement
            int rowsAffected = statement.executeUpdate();

            // Check if the update operation was successful
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the resources
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @FXML
    private void DeleteSelectedDoctor() {
        // Get the selected doctor from the table
        Doctor selectedDoctor = DoctorTable.getSelectionModel().getSelectedItem();

        // Check if a doctor is selected
        if (selectedDoctor != null) {
            // Ask for confirmation before deleting
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete the selected doctor?");
            Optional<ButtonType> result = alert.showAndWait();

            // If the user confirms the deletion, proceed with the delete operation
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Delete the selected doctor from the database
                if (deleteDoctor(selectedDoctor)) {
                    // Show success message
                    showAlert("Doctor deleted successfully.");

                    // Refresh the table data
                    loadDataIntoTable();
                } else {
                    showAlert("Failed to delete doctor.");
                }
            }
        } else {
            // If no doctor is selected, show an alert
            showAlert("Please select a doctor from the table.");
        }
    }

    private boolean deleteDoctor(Doctor doctor) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Get connection from Connector class
            connection = Connector.getConnection();

            // Prepare the SQL statement
            String query = "DELETE FROM doctor WHERE doctorID = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, doctor.getDoctorID());

            // Execute the update statement
            int rowsAffected = statement.executeUpdate();

            // Check if the delete operation was successful
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            // Check if the exception is due to foreign key constraint violation
            if (e.getMessage().toLowerCase().contains("foreign key constraint")) {
                // Show a message to the user indicating that the doctor cannot be deleted
                showAlert("Cannot delete doctor because it is referenced in other tables.");
            } else {
                e.printStackTrace();
            }
        } finally {
            // Close the resources
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
