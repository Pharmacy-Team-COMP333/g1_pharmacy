package application;

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

    @FXML
    private Button AddDisease;

    @FXML
    private Button DeleteDisease;

    @FXML
    private Button DeleteSelected;

    @FXML
    private TableColumn<Disease, String> DescriptionColumn;

    @FXML
    private TextField DescriptionTF;

    @FXML
    private TableColumn<Disease, Integer> DiseaseIDColumn;

    @FXML
    private TextField DiseaseIDTF;

    @FXML
    private TableView<Disease> DiseaseTable;

    @FXML
    private TableColumn<Disease, String> NameColumn;

    @FXML
    private TextField NameTF;

    @FXML
    private Button Refresh;

    @FXML
    private Button SearchB;

    @FXML
    private TextField SearchTF;

    @FXML
    private Button SelectDisease;

    @FXML
    private TableColumn<Disease, String> TreatmentColumn;

    @FXML
    private TextField TreatmentTF;

    @FXML
    private Button UpdateDisease;

    @FXML
    private Button UpdateSelected;

    private ObservableList<Disease> diseaseList = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        DiseaseIDColumn.setCellValueFactory(new PropertyValueFactory<>("diseaseID"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        DescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        TreatmentColumn.setCellValueFactory(new PropertyValueFactory<>("treatment"));

        loadDiseaseData();
    }

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

    @FXML
    void AddDisease(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/AddDisease.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Add New Disease");
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void DeleteDisease(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/DeleteDisease.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Delete Disease");
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void UpdateDisease(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/UpdateDisease.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Update Disease");
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void DeleteSelected(ActionEvent event) {
        Disease selectedDisease = DiseaseTable.getSelectionModel().getSelectedItem();
        if (selectedDisease != null) {
            try (Connection conn = Connector.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM DISEASE WHERE diseaseID = ?")) {

                stmt.setInt(1, selectedDisease.getDiseaseID());
                stmt.executeUpdate();
                loadDiseaseData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("No Disease Selected , Please select a disease in the table.");
        }
    }

    @FXML
    private void UpdateSelected() {
        // Get the selected disease from the table
    	Disease selectedDisease= DiseaseTable.getSelectionModel().getSelectedItem();

        // Check if a disease is selected
        if (selectedDisease != null) {
            // Retrieve the updated data from the text fields
            String name = NameTF.getText();
            String description = DescriptionTF.getText();
            String treatment = TreatmentTF.getText();

            // Update the selected disease with the new data
            selectedDisease.setName(name);
            selectedDisease.setDescription(description);
            selectedDisease.setTreatment(treatment);

            // Update the doctor record in the database
            if (updateDisease(selectedDisease)) {
                // If the update is successful, refresh the table
            	loadDiseaseData();
                showAlert("Disease updated successfully.");
            } else {
                showAlert("Failed to update Disease.");
            }
        } else {
            // If no disease is selected, show an alert
            showAlert("Please select a disease from the table.");
        }
    }

    private boolean updateDisease(Disease disease) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = Connector.getConnection();
            String query = "UPDATE disease SET name = ?, description = ?, treatment = ? WHERE diseaseID = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, disease.getName());
            statement.setString(2, disease.getDescription());
            statement.setString(3, disease.getTreatment());
            statement.setInt(4, disease.getDiseaseID());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
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
    }

    @FXML
    void Refresh(ActionEvent event) {
        loadDiseaseData();
    }

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

    @FXML
    void SelectDisease(ActionEvent event) {
        // Get the selected doctor from the table
    	Disease selectedDisease = DiseaseTable.getSelectionModel().getSelectedItem();

        // Check if a Disease is selected
        if (selectedDisease != null) {
            // Populate the text fields with the selected doctor's data
        	DiseaseIDTF.setText(String.valueOf(selectedDisease.getDiseaseID()));
            NameTF.setText(selectedDisease.getName());
            DescriptionTF.setText(selectedDisease.getDescription());
            TreatmentTF.setText(selectedDisease.getTreatment());
        } else {
            // If no disease is selected, show an alert
            showAlert("Please select a disease from the table.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
