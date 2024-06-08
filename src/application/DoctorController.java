package application;

//Import Statements
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

    // FXML Declarations for UI Components
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

    // ObservableList to store doctor data
    private ObservableList<Doctor> doctorsList = FXCollections.observableArrayList();

    //Automatically after the fxml file is loaded
    @FXML
    void initialize() {
        DoctorIDColumn.setCellValueFactory(new PropertyValueFactory<>("doctorID"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        SpecializationColumn.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        PhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        // Load data into the table
        loadDataIntoTable();
    }

    //Load doctor data from the database into the table
    private void loadDataIntoTable() {
        doctorsList.setAll(fetchDataFromDatabase());

        // Set the items in the table to the doctorsList
        DoctorTable.setItems(doctorsList);
    }

    //Fetch doctor data from the database
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
                doctors.add(new Doctor(doctorID, name, specialization, phoneNumber));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return doctors;
    }

    //Open the Add Doctor window
    @FXML
    void AddDoctor(ActionEvent event) {
        openStage("/AddDoctor.fxml", "Add New Doctor");
    }

    //Open the Delete Doctor window
    @FXML
    void DeleteDoctor(ActionEvent event) {
        openStage("/DeleteDoctor.fxml", "Delete Doctor");
    }

    //Open the Update Doctor window
    @FXML
    void UpdateDoctor(ActionEvent event) {
        openStage("/UpdateDoctor.fxml", "Update Doctor");
    }

    //Open a new window with the specified FXML file and title
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

    //Select a doctor from the table and populate the text fields with its data
    @FXML
    private void SelectDoctor() {
        // Get the selected doctor from the table
        Doctor selectedDoctor = DoctorTable.getSelectionModel().getSelectedItem();

        // Check if a doctor is selected
        if (selectedDoctor != null) {
            DoctorIDTF.setText(String.valueOf(selectedDoctor.getDoctorID()));
            NameTF.setText(selectedDoctor.getName());
            SpecializationTF.setText(selectedDoctor.getSpecialization());
            PhoneNumberTF.setText(selectedDoctor.getPhoneNumber());
        } else {
            showAlert("Please select a doctor from the table.");
        }
    }

    //Show an alert with the specified message
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //Refresh the table data
    @FXML
    private void Refresh() {
        loadDataIntoTable();
    }

    //Search for doctors based on user input
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

    //Update the selected doctor
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
                loadDataIntoTable();
                clearTextFields();
                showAlert("Doctor updated successfully.");
            } else {
                showAlert("Failed to update doctor.");
            }
        } else {
            showAlert("Please select a doctor from the table.");
        }
        clearTextFields();
    }

    //Update a doctor in the database
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
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // Close the resources
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //Delete the selected doctor
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
                    showAlert("Doctor deleted successfully.");
                    loadDataIntoTable();
                    clearTextFields();
                } else {
                    showAlert("Failed to delete doctor.");
                }
            }
        } else {
            showAlert("Please select a doctor from the table.");
        }
        clearTextFields();
    }

    //Delete a doctor from the database
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

            // Execute the delete statement
            int rowsAffected = statement.executeUpdate();

            // Check if the delete operation was successful
            return rowsAffected > 0;
        } catch (SQLException e) {
            // Check if the exception is due to foreign key constraint violation
            if (e.getMessage().toLowerCase().contains("foreign key constraint")) {
                showAlert("Cannot delete doctor because it is referenced in other tables.");
            } else {
                e.printStackTrace();
            }
            return false;
        } finally {
            // Close the resources
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //Clear the text fields
    private void clearTextFields() {
        DoctorIDTF.clear();
        NameTF.clear();
        SpecializationTF.clear();
        PhoneNumberTF.clear();
    }
}
