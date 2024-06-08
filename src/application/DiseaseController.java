package application;

//Import Statements
import java.sql.Connection;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DiseaseController {

    // FXML Declarations for UI Components
    @FXML private Button AddDisease;
    @FXML private Button DeleteDisease;
    @FXML private Button DeleteSelected;
    @FXML private TableColumn<Disease, String> DescriptionColumn;
    @FXML private TextField DescriptionTF;
    @FXML private TableColumn<Disease, Integer> DiseaseIDColumn;
    @FXML private TextField DiseaseIDTF;
    @FXML private TableView<Disease> DiseaseTable;
    @FXML private TableColumn<Disease, String> NameColumn;
    @FXML private TextField NameTF;
    @FXML private Button Refresh;
    @FXML private Button SearchB;
    @FXML private TextField SearchTF;
    @FXML private Button SelectDisease;
    @FXML private TableColumn<Disease, String> TreatmentColumn;
    @FXML private TextField TreatmentTF;
    @FXML private Button UpdateDisease;
    @FXML private Button UpdateSelected;

    
    // ObservableList to store disease data
    private ObservableList<Disease> diseaseList = FXCollections.observableArrayList();

    //Automatically after the FXML file is loaded
    @FXML
    void initialize() {
        DiseaseIDColumn.setCellValueFactory(new PropertyValueFactory<>("diseaseID"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        DescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        TreatmentColumn.setCellValueFactory(new PropertyValueFactory<>("treatment"));

        // Load data into the table
        loadDiseaseData();
    }
    

    //Load disease data from the database into the table
    private void loadDiseaseData() {
        diseaseList.clear();
        try (Connection conn = Connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM DISEASE");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                diseaseList.add(new Disease(
                        rs.getInt("DiseaseID"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getString("Treatment")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DiseaseTable.setItems(diseaseList);
    }
    

    //Open the Add Disease window
    @FXML
    void AddDisease(ActionEvent event) {
        openStage("/AddDisease.fxml", "Add New Disease");
    }

    //Open the Delete Disease window
    @FXML
    void DeleteDisease(ActionEvent event) {
        openStage("/DeleteDisease.fxml", "Delete Disease");
    }

    //Open the Update Disease window
    @FXML
    void UpdateDisease(ActionEvent event) {
        openStage("/UpdateDisease.fxml", "Update Disease");
    }

    //Delete the selected disease
    @FXML
    void DeleteSelected(ActionEvent event) {
        Disease selectedDisease = DiseaseTable.getSelectionModel().getSelectedItem();
        if (selectedDisease != null) {
            try (Connection conn = Connector.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM DISEASE WHERE diseaseID = ?")) {

                stmt.setInt(1, selectedDisease.getDiseaseID());
                stmt.executeUpdate();
                loadDiseaseData();
                showAlert("Disease deleted successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error occurred while deleting the disease: " + e.getMessage());
            }
        } else {
            showAlert("No Disease Selected. Please select a disease in the table.");
        }
        clearTextFields();
    }

    //Update the selected disease
    @FXML
    private void UpdateSelected(ActionEvent event) {
        Disease selectedDisease = DiseaseTable.getSelectionModel().getSelectedItem();
        if (selectedDisease != null) {
            try {
                String name = NameTF.getText();
                String description = DescriptionTF.getText();
                String treatment = TreatmentTF.getText();

                selectedDisease.setName(name);
                selectedDisease.setDescription(description);
                selectedDisease.setTreatment(treatment);

                if (updateDisease(selectedDisease)) {
                    loadDiseaseData();
                    clearTextFields();
                    showAlert("Disease updated successfully.");
                } else {
                    showAlert("Failed to update disease.");
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid input. Please enter valid data.");
            }
        } else {
            showAlert("Please select a disease from the table.");
        }
        clearTextFields();
    }

    //Update a disease record in the database
    private boolean updateDisease(Disease disease) {
        String query = "UPDATE disease SET name = ?, description = ?, treatment = ? WHERE diseaseID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, disease.getName());
            stmt.setString(2, disease.getDescription());
            stmt.setString(3, disease.getTreatment());
            stmt.setInt(4, disease.getDiseaseID());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Refresh the table data
    @FXML
    void Refresh(ActionEvent event) {
        loadDiseaseData();
    }

    //Search for diseases based on user input
    @FXML
    void SearchB(ActionEvent event) {
        String searchText = SearchTF.getText();
        diseaseList.clear();
        try (Connection conn = Connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM DISEASE WHERE diseaseID LIKE ? OR Name LIKE ? OR Description LIKE ? OR Treatment LIKE ?")) {
            stmt.setString(1, "%" + searchText + "%");
            stmt.setString(2, "%" + searchText + "%");
            stmt.setString(3, "%" + searchText + "%");
            stmt.setString(4, "%" + searchText + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                diseaseList.add(new Disease(
                        rs.getInt("DiseaseID"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getString("Treatment")
                ));
            }
            DiseaseTable.setItems(diseaseList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Select a disease from the table and populate the text fields with its data
    @FXML
    void SelectDisease(ActionEvent event) {
        Disease selectedDisease = DiseaseTable.getSelectionModel().getSelectedItem();
        if (selectedDisease != null) {
            DiseaseIDTF.setText(String.valueOf(selectedDisease.getDiseaseID()));
            NameTF.setText(selectedDisease.getName());
            DescriptionTF.setText(selectedDisease.getDescription());
            TreatmentTF.setText(selectedDisease.getTreatment());
        } else {
            showAlert("Please select a disease from the table.");
        }
    }

    //Show an alert with a specific message
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //Clear all text fields
    private void clearTextFields() {
        DiseaseIDTF.clear();
        NameTF.clear();
        DescriptionTF.clear();
        TreatmentTF.clear();
    }

    //Open a new stage (window)
    private void openStage(String fxmlFile, String title) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
