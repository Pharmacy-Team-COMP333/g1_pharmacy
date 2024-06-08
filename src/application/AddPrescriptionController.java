package application;

//Import Statements
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date; 
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;


public class AddPrescriptionController {

    // FXML Declarations for UI Components
    @FXML private Button AddP;
    @FXML private TextField CustomerID;
    @FXML private DatePicker Date;
    @FXML private TextField DoctorID;
    @FXML private TextField Dosage;
    @FXML private TextField Instructions;
    @FXML private TextField PrescriptionID;

    
    
    
    @FXML
    void AddP(ActionEvent event) {
        // Extracting information entered by the user from text fields for prescription details	
        int prescriptionID;
        LocalDate localDate = Date.getValue();
        if (localDate == null) {
            showAlert("Empty Date", "Please select a date.");
            return;
        }
        
        Date date = java.sql.Date.valueOf(localDate); // Extracting date value from DatePicker
        String dosage = Dosage.getText();
        String instructions = Instructions.getText();
        String doctorIDText = DoctorID.getText();
        String customerIDText = CustomerID.getText();

        // Check if any text field is empty
        if (dosage.isEmpty() || instructions.isEmpty() || doctorIDText.isEmpty() || customerIDText.isEmpty()) {
            showAlert("Empty Fields", "Please fill in all fields.");
            return;
        }

        int doctorID = Integer.parseInt(doctorIDText);
        int customerID = Integer.parseInt(customerIDText);

        try {
            prescriptionID = Integer.parseInt(PrescriptionID.getText()); // Using PrescriptionID field to get prescription ID
        } catch (NumberFormatException e) {
            // Handle the case where the prescriptionID is not a valid integer
            showAlert("Invalid prescription ID", "Please enter a valid integer for Prescription ID.");
            return;
        }

        // Check if prescriptionID already exists
        if (isPrescriptionIdExists(prescriptionID)) {
            return; // Exit method if prescriptionID exists
        }
     // Check if doctorID exists
        if (!isDoctorExists(doctorID)) {
            showAlert("Doctor Not Found", "Doctor with ID " + doctorID + " does not exist.");
            return;
        }

        // Check if customerID exists
        if (!isCustomerExists(customerID)) {
            showAlert("Customer Not Found", "Customer with ID " + customerID + " does not exist.");
            return;
        }

        // Create a new Prescription object
        Prescription newPrescription = new Prescription(prescriptionID, date, dosage, instructions, doctorID, customerID);
        boolean insertedSuccessfully = insertPrescription(newPrescription);

        if (insertedSuccessfully) {
            // Show message box with the added prescription's information
            showAlert("Prescription Added Successfully", "New Prescription added successfully!");
        } else {
            // Show error message
            showAlert("Failed to Add Prescription", "Failed to add new Prescription.");
        }

    }



    // Check if a prescription with the given ID exists 
    private boolean isPrescriptionIdExists(int prescriptionID) {
        String query = "SELECT COUNT(*) FROM PRESCRIPTION WHERE prescriptionID = ?";
        try (Connection conn = Connector.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, prescriptionID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                // Prescription ID exists, show message box
                showAlert("Duplicate Prescription ID", "Prescription ID already exists. Please enter a different ID.");
                return true;
            }
        } catch (SQLException ex) {
            showAlert("Database Error", "An error occurred while accessing the database.");
        }
        return false; // Prescription ID does not exist
    }

    // Insert a new prescription into the database
    public boolean insertPrescription(Prescription prescription) {
        String query = "INSERT INTO PRESCRIPTION (prescriptionID, prescriptionDate, dosage, instructions, doctorID, customerID) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Connector.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, prescription.getPrescriptionID());
            preparedStatement.setDate(2, prescription.getPrescriptionDate());
            preparedStatement.setString(3, prescription.getDosage());
            preparedStatement.setString(4, prescription.getInstructions());
            preparedStatement.setInt(5, prescription.getDoctorID());
            preparedStatement.setInt(6, prescription.getCustomerID());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0; // Indicates success if rows were inserted
        } catch (SQLException ex) {
            showAlert("Database Error", "An error occurred while accessing the database.");
            return false; // Indicates failure
        }
    }
    
    
    // Check if a doctor with the given ID exists
    private boolean isDoctorExists(int doctorID) {
        String query = "SELECT COUNT(*) FROM DOCTOR WHERE doctorID = ?";
        try (Connection conn = Connector.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, doctorID);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() && resultSet.getInt(1) > 0;
        } catch (SQLException ex) {
            showAlert("Database Error", "An error occurred while accessing the database.");
            return false; // Unable to determine if the doctor exists, assume it does not
        }
    }

    // Check if a customer with the given ID exists
    private boolean isCustomerExists(int customerID) {
        String query = "SELECT COUNT(*) FROM CUSTOMER WHERE customerID = ?";
        try (Connection conn = Connector.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, customerID);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() && resultSet.getInt(1) > 0;
        } catch (SQLException ex) {
            showAlert("Database Error", "An error occurred while accessing the database.");
            return false; // Unable to determine if the customer exists, assume it does not
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