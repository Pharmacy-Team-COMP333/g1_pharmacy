package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
public class payment_methodController {

    @FXML
    private TableColumn<payment_method, Integer> PMID;

    @FXML
    private Button delete;

    @FXML
    private Button insert;

    @FXML
    private TableColumn<payment_method, String> name;

    @FXML
    private TextField newID;

    @FXML
    private TextField newname;

    @FXML
    private TextField oldId;

    @FXML
    private TableView<payment_method> tableview;

    @FXML
    private Button update;

    @FXML
    private TextField updateId;

    @FXML
    private TextField updatename;
    private ArrayList<payment_method> data;
	private ObservableList<payment_method> dataList;
	public void initialize() {
		data = new ArrayList<>();
		dataList = FXCollections.observableArrayList(data);
		tableview.setEditable(true);
		
		PMID.setCellValueFactory(new PropertyValueFactory<payment_method, Integer>("paymentMethodID"));
		PMID.setCellFactory(TextFieldTableCell.<payment_method, Integer>forTableColumn(new IntegerStringConverter()));
		PMID.setOnEditCommit((CellEditEvent<payment_method, Integer> t) -> {
			int d = t.getRowValue().getPaymentMethodID();
			((payment_method) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setPaymentMethodID(t.getNewValue()); // display
			// only

			updatePMID(d, t.getNewValue());
		});


		name.setCellValueFactory(new PropertyValueFactory<payment_method, String>("name"));
		name.setCellFactory(TextFieldTableCell.<payment_method>forTableColumn());
		name.setOnEditCommit((CellEditEvent<payment_method, String> t) -> {
			((payment_method) t.getTableView().getItems().get(t.getTablePosition().getRow()))
			.setName(t.getNewValue()); // display
			// only

			updateName(t.getRowValue().getPaymentMethodID(), t.getNewValue());
		});

		
		getData();
		tableview.setItems(dataList);
	}
	private void updatePMID(int d, Integer newValue) {
	    try {
	        Connector.a.connectDB();
	        String sql = "UPDATE payment_method SET paymentMethodID = ? WHERE paymentMethodID = ?";
	        PreparedStatement statement = Connector.a.connectDB().prepareStatement(sql);
	        statement.setInt(1, newValue);
	        statement.setInt(2, d);
	        statement.executeUpdate();
	        Connector.a.connectDB().close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	private void updateName(int paymentMethodID, String newValue) {
	    try {
	        Connector.a.connectDB();
	        String sql = "UPDATE payment_method SET name = ? WHERE paymentMethodID = ?";
	        PreparedStatement statement = Connector.a.connectDB().prepareStatement(sql);
	        statement.setString(1, newValue);
	        statement.setInt(2, paymentMethodID);
	        statement.executeUpdate();
	        Connector.a.connectDB().close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	private void getData() {
		String SQL = "SELECT * FROM payment_method ORDER BY paymentMethodID";
		try {
			Connector.a.connectDB();
			java.sql.Statement state = Connector.a.connectDB().createStatement();
			ResultSet rs = state.executeQuery(SQL);
			while (rs.next()) {
				payment_method cat = new payment_method(rs.getInt(1),
						rs.getString(2).toString());
				dataList.add(cat);
			}
			rs.close();
			state.close();
			Connector.a.connectDB().close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
    @FXML
    void deleteonAction(ActionEvent event) {
    	ObservableList<payment_method> selectedRows = tableview.getSelectionModel().getSelectedItems();
		ArrayList<payment_method> rows = new ArrayList<>(selectedRows);
		if(rows.size()==0) {
			return;
		}
	
		deleteRow(rows.get(0).getPaymentMethodID());
		initialize();

    }
    private void deleteRow(int id) {
        try {
            Connector.a.connectDB();
            String sql = "DELETE FROM payment_method WHERE paymentMethodID = ?";
            PreparedStatement statement = Connector.a.connectDB().prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            Connector.a.connectDB().close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void insertonAction(ActionEvent event) {
    	try {
    		payment_method rc;
			rc = new payment_method(Integer.parseInt(newID.getText()), newname.getText());
			payment_method.pay= rc;
			insertData(rc);
			newID.clear();
			newname.clear();
		} catch (Exception e) {
			showDialog(null, "Wrong input!!", "Please check the input again", AlertType.ERROR);
		}
    }
    private void insertData(payment_method rc) {
        try {
            Connector.a.connectDB();
            String sql = "INSERT INTO payment_method (paymentMethodID, name) VALUES (?, ?)";
            PreparedStatement ps = Connector.a.connectDB().prepareStatement(sql);
            ps.setInt(1, rc.getPaymentMethodID());
            ps.setString(2, rc.getName()); // corrected method name
            ps.execute();
            Stage stage;
            stage = (Stage) insert.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void updateonAction(ActionEvent event) {
    	try {
			if (oldId.getText() != null) {
				int d = Integer.parseInt(oldId.getText()); 
				if (updatename.getText().length() > 0) {
					updateName(d, updatename.getText());
				}
				
				if (updateId.getText().length() > 0) {
					updatePMID(d, Integer.parseInt(updateId.getText())); 
				
				}
					
				
			//	refresh();
			    oldId.clear();
			    updatename.clear();
				updateId.clear();
				initialize();

			}
		} catch (Exception e) {
			 oldId.clear();
			    updatename.clear();
				updateId.clear();
			showDialog("Watch out!!", "", "you entered a rong format", AlertType.ERROR);
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
