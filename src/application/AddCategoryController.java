package application;

import java.sql.SQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddCategoryController {

    @FXML
    private Button btnCancel;

    @FXML
    private Button add;

    @FXML
    private TextField addCat_id;

    @FXML
    private TextField addCat_Name;

    @FXML
    private TextField addDescription;

    @FXML
    void CancelOnAction(ActionEvent event) {
    	Stage stage;
		stage = (Stage) btnCancel.getScene().getWindow();
		stage.close();
    }

    @FXML
    void addOnAction(ActionEvent event) {
    	try {
			Category rc;
			rc = new Category(Integer.parseInt(addCat_id.getText()), addCat_Name.getText(), addDescription.getText());
			Category.cat= rc;
			insertData(rc);
			addCat_id.clear();
			addCat_Name.clear();
			addDescription.clear();
				
		} catch (Exception e) {
			showDialog(null, "Wrong input!!", "Please check the input again", AlertType.ERROR);
		}
	}
    public void showDialog(String title, String header, String body, AlertType type) {
		Alert alert = new Alert(type); // infotrmation or error or..
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(body);

		alert.show();

	}
    private void insertData(Category rc) {
        try {
            Connector.a.connectDB();
            String sql = "INSERT INTO categories (cat_id, categores_name, Description) VALUES (?, ?, ?)";
            PreparedStatement ps = Connector.a.connectDB().prepareStatement(sql);
            ps.setInt(1, rc.getCat_id());
            ps.setString(2, rc.getCategores_name());
            ps.setString(3, rc.getDescription());
            ps.execute();
            Stage stage;
            stage = (Stage) add.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
