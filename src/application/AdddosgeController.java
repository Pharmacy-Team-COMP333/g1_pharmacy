package application;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AdddosgeController {

    @FXML
    private Button add;

    @FXML
    private TextField addCat_Name;

    @FXML
    private TextField addCat_id;

    @FXML
    private Button btnCancel;

    @FXML
    void CancelOnAction(ActionEvent event) {
    	Stage stage;
		stage = (Stage) btnCancel.getScene().getWindow();
		stage.close();
    }

    @FXML
    void addOnAction(ActionEvent event) {
    	try {
    		dosgeData dos;
    		dos = new dosgeData(Integer.parseInt(addCat_id.getText()), addCat_Name.getText());
    		dosgeData.dos= dos;
			insertData(dos);
			addCat_id.clear();
			addCat_Name.clear();
				
		} catch (Exception e) {
			showDialog(null, "Wrong input!!", "Please check the input again", AlertType.ERROR);
		}
	}
    
    private void insertData(dosgeData dos) {
        try {
            Connector.a.connectDB();
            String sql = "INSERT INTO dosage_form (dosageFormID, name) VALUES (?, ?)";
            PreparedStatement ps = Connector.a.connectDB().prepareStatement(sql);
            ps.setInt(1, dos.getDosageFormID());
            ps.setString(2, dos.getName());
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
    
    public void showDialog(String title, String header, String body, AlertType type) {
		Alert alert = new Alert(type); // infotrmation or error or..
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(body);

		alert.show();

	}

}
