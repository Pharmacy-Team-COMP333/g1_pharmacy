package application;

// Import Statements
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class PrescriptionController {

    // FXML Declarations for UI Components
    @FXML private Button AddPrescription;
    @FXML private TableColumn<Prescription, Date> DateColumn;
    @FXML private DatePicker DateTF;
    @FXML private Button DeletePrescription;
    @FXML private Button DeleteSelected;
    @FXML private TableColumn<Prescription, Integer> DoctorIDColumn;
    @FXML private TextField DoctorIDTF;
    @FXML private TableColumn<Prescription, String> DosageColumn;
    @FXML private TextField DosageTF;
    @FXML private TableColumn<Prescription, String> InstructionsColumn;
    @FXML private TextField InstructionsTF;
    @FXML private TableColumn<Prescription, Integer> CustomerIDColumn;
    @FXML private TextField CustomerIDTF;
    @FXML private TableColumn<Prescription, Integer> PrescriptionIDColumn;
    @FXML private TextField PrescriptionIDTF;
    @FXML private TableView<Prescription> PrescriptionTable;
    @FXML private Button Refresh;
    @FXML private TextField Search;
    @FXML private Button SearchB;
    @FXML private Button SelectPrescription;
    @FXML private Button UpdatePrescription;
    @FXML private Button UpdateSelected;
    private ObservableList<Prescription> prescriptionList = FXCollections.observableArrayList();

    
    //Automatically after the fxml file is loaded
    @FXML
    void initialize() {
        // Set cell value factories for the table columns
        PrescriptionIDColumn.setCellValueFactory(new PropertyValueFactory<>("prescriptionID"));
        DateColumn.setCellValueFactory(new PropertyValueFactory<>("prescriptionDate"));
        DosageColumn.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        InstructionsColumn.setCellValueFactory(new PropertyValueFactory<>("instructions"));
        DoctorIDColumn.setCellValueFactory(new PropertyValueFactory<>("doctorID"));
        CustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        // Load data into the table
        loadPrescriptionData();
    }

    
    //Load prescription data from the database into the table
    private void loadPrescriptionData() {
        prescriptionList.clear();
        try (Connection conn = Connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PRESCRIPTION");
             ResultSet rs = stmt.executeQuery()) {

            // Iterate through the result set and add prescriptions to the list
            while (rs.next()) {
                prescriptionList.add(new Prescription(
                        rs.getInt("prescriptionID"),
                        rs.getDate("prescriptionDate"),
                        rs.getString("dosage"),
                        rs.getString("instructions"),
                        rs.getInt("doctorID"),
                        rs.getInt("customerID")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Set the items in the table to the prescription list
        PrescriptionTable.setItems(prescriptionList);
    }

    //Refresh the table data
    @FXML
    void Refresh(ActionEvent event) {
        loadPrescriptionData();
    }

    //Open the Add Prescription window
    @FXML
    void AddPrescription(ActionEvent event) {
        openStage("/AddPrescription.fxml", "Add Prescription");
    }

    //Open the Delete Prescription window
    @FXML
    void DeletePrescription(ActionEvent event) {
        openStage("/DeletePrescription.fxml", "Delete Prescription");
    }

    //Open the Update Prescription window
    @FXML
    void UpdatePrescription(ActionEvent event) {
        openStage("/UpdatePrescription.fxml", "Update Prescription");
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

    //Search for prescriptions based on user input
    @FXML
    void SearchB(ActionEvent event) {
        String searchText = Search.getText();
        prescriptionList.clear();
        try (Connection conn = Connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PRESCRIPTION WHERE prescriptionID LIKE ? OR prescriptionDate LIKE ? OR dosage LIKE ? OR instructions LIKE ? OR doctorID LIKE ? OR customerID LIKE ?")) {
            stmt.setString(1, "%" + searchText + "%");
            stmt.setString(2, "%" + searchText + "%");
            stmt.setString(3, "%" + searchText + "%");
            stmt.setString(4, "%" + searchText + "%");
            stmt.setString(5, "%" + searchText + "%");
            stmt.setString(6, "%" + searchText + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                prescriptionList.add(new Prescription(
                        rs.getInt("prescriptionID"),
                        rs.getDate("prescriptionDate"),
                        rs.getString("dosage"),
                        rs.getString("instructions"),
                        rs.getInt("doctorID"),
                        rs.getInt("customerID")
                ));
            }
            PrescriptionTable.setItems(prescriptionList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Select a prescription from the table and populate the text fields with its data
    @FXML
    void SelectPrescription(ActionEvent event) {
        // Get the selected prescription from the table
        Prescription selectedPrescription = PrescriptionTable.getSelectionModel().getSelectedItem();

        // Check if a prescription is selected
        if (selectedPrescription != null) {
            // Populate the text fields with the selected prescription's data
            PrescriptionIDTF.setText(String.valueOf(selectedPrescription.getPrescriptionID()));
            DateTF.setValue(selectedPrescription.getPrescriptionDate().toLocalDate()); 
            DosageTF.setText(selectedPrescription.getDosage());
            InstructionsTF.setText(selectedPrescription.getInstructions());
            DoctorIDTF.setText(String.valueOf(selectedPrescription.getDoctorID())); 
            CustomerIDTF.setText(String.valueOf(selectedPrescription.getCustomerID())); 
        } else {
            showAlert("Please select a Prescription from the table.");
        }
    }

    //Update the selected prescription
    @FXML
    private void UpdateSelected(ActionEvent event) {
        // Get the selected prescription from the table
        Prescription selectedPrescription = PrescriptionTable.getSelectionModel().getSelectedItem();

        // Check if a prescription is selected
        if (selectedPrescription != null) {
            String prescriptionDate = DateTF.getValue() == null ? selectedPrescription.getPrescriptionDate().toString() : DateTF.getValue().toString();
            String dosage = DosageTF.getText();
            String instructions = InstructionsTF.getText();
            int doctorID = DoctorIDTF.getText().isEmpty() ? selectedPrescription.getDoctorID() : Integer.parseInt(DoctorIDTF.getText());
            int customerID = CustomerIDTF.getText().isEmpty() ? selectedPrescription.getCustomerID() : Integer.parseInt(CustomerIDTF.getText());

            // Update the selected prescription with the new data
            selectedPrescription.setPrescriptionDate(Date.valueOf(prescriptionDate));
            selectedPrescription.setDosage(dosage);
            selectedPrescription.setInstructions(instructions);
            selectedPrescription.setDoctorID(doctorID);
            selectedPrescription.setCustomerID(customerID);

            // Update the prescription record in the database
            if (updatePrescription(selectedPrescription)) {
                loadPrescriptionData();
                clearTextFields();
                showAlert("Prescription updated successfully.");
            } else {
                showAlert("Failed to update Prescription.");
            }
        } else {
            showAlert("Please select a Prescription from the table.");
        }
    }

    //Update a prescription in the database
    private boolean updatePrescription(Prescription prescription) {
        String updateQuery = "UPDATE PRESCRIPTION SET prescriptionDate = ?, dosage = ?, instructions = ?, doctorID = ?, customerID = ? WHERE prescriptionID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(updateQuery)) {
            preparedStatement.setDate(1, prescription.getPrescriptionDate());
            preparedStatement.setString(2, prescription.getDosage());
            preparedStatement.setString(3, prescription.getInstructions());
            preparedStatement.setInt(6, prescription.getPrescriptionID());

            // Check if the doctor exists
            if (isDoctorExists(prescription.getDoctorID())) {
                preparedStatement.setInt(4, prescription.getDoctorID());
            } else {
                showAlert("Doctor with ID " + prescription.getDoctorID() + " does not exist.");
                return false;
            }

            // Check if the customer exists
            if (isCustomerExists(prescription.getCustomerID())) {
                preparedStatement.setInt(5, prescription.getCustomerID());
            } else {
                showAlert("Customer with ID " + prescription.getCustomerID() + " does not exist.");
                return false;
            }

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            return false;
        }
    }

    //Delete the selected prescription
    @FXML
    void DeleteSelected(ActionEvent event) {
        // Get the selected prescription from the table
        Prescription selectedPrescription = PrescriptionTable.getSelectionModel().getSelectedItem();

        if (selectedPrescription != null) {
            try (Connection conn = Connector.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM PRESCRIPTION WHERE prescriptionID = ?")) {

                stmt.setInt(1, selectedPrescription.getPrescriptionID());
                int rowsAffected = stmt.executeUpdate();

                // Check if the deletion was successful
                if (rowsAffected > 0) {
                    loadPrescriptionData();
                    clearTextFields();
                    showAlert("Prescription deleted successfully.");
                } else {
                    showAlert("Failed to delete Prescription.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("An error occurred while deleting the Prescription.");
            }
        } else {
            showAlert("No Prescription selected. Please select a Prescription in the table.");
        }
    }

    //Clear the text fields
    private void clearTextFields() {
        PrescriptionIDTF.clear();
        DateTF.setValue(null);
        DosageTF.clear();
        InstructionsTF.clear();
        DoctorIDTF.clear();
        CustomerIDTF.clear();
    }

    //Show an alert with the specified message
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //Check if a doctor exists in the database
    private boolean isDoctorExists(int doctorID) {
        String query = "SELECT COUNT(*) FROM DOCTOR WHERE doctorID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, doctorID);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() && resultSet.getInt(1) > 0;
        } catch (SQLException ex) {
            return false; // Unable to determine if the doctor exists, assume it does not
        }
    }

    //Check if a customer exists in the database
    private boolean isCustomerExists(int customerID) {
        String query = "SELECT COUNT(*) FROM CUSTOMER WHERE customerID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, customerID);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() && resultSet.getInt(1) > 0;
        } catch (SQLException ex) {
            return false; // Unable to determine if the customer exists, assume it does not
        }
    }
}
