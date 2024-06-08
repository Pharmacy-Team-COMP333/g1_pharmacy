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
public class CustomerController {
	
    @FXML
    private TableView<customer> tableview;

    @FXML
    private TextField Newcontact;

    @FXML
    private TableColumn<customer, String> contable;

    @FXML
    private Button delete;

    @FXML
    private TableColumn<customer, Integer> idtable;

    @FXML
    private Button insert;

    @FXML
    private TableColumn<customer, String> nametabel;

    @FXML
    private TextField newID;

    @FXML
    private TextField newname;

    @FXML
    private TextField oldId;

    @FXML
    private Button update;

    @FXML
    private TextField updateId;

    @FXML
    private TextField updatecont;

    @FXML
    private TextField updatename;
    
    private ArrayList<customer> data;
	private ObservableList<customer> dataList;

	
	public void initialize() {
		data = new ArrayList<>();
		dataList = FXCollections.observableArrayList(data);
		tableview.setEditable(true);
		
		idtable.setCellValueFactory(new PropertyValueFactory<customer, Integer>("customerID"));
		idtable.setCellFactory(TextFieldTableCell.<customer, Integer>forTableColumn(new IntegerStringConverter()));
		idtable.setOnEditCommit((CellEditEvent<customer, Integer> t) -> {
			int d = t.getRowValue().getCustomerID();
			((customer) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setCustomerID(t.getNewValue()); // display
			// only

			customerID(d, t.getNewValue());
		});


		nametabel.setCellValueFactory(new PropertyValueFactory<customer, String>("name"));
		nametabel.setCellFactory(TextFieldTableCell.<customer>forTableColumn());
		nametabel.setOnEditCommit((CellEditEvent<customer, String> t) -> {
			((customer) t.getTableView().getItems().get(t.getTablePosition().getRow()))
			.setName(t.getNewValue()); // display
			// only

			updateName(t.getRowValue().getCustomerID(), t.getNewValue());
		});

		
		contable.setCellValueFactory(new PropertyValueFactory<customer, String>("contactInfo"));
		contable.setCellFactory(TextFieldTableCell.<customer>forTableColumn());
		contable.setOnEditCommit((CellEditEvent<customer, String> t) -> {
			((customer) t.getTableView().getItems().get(t.getTablePosition().getRow()))
		    .setContactInfo(t.getNewValue());

			// only

			updateContactInfo(t.getRowValue().getCustomerID(), t.getNewValue());
		});

		getData();
		tableview.setItems(dataList);
	}
	private void customerID(int d, Integer newValue) {
	    try {
	        Connector.a.connectDB();
	        String sql = "UPDATE customer SET customerID = ? WHERE customerID = ?";
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
	private void updateName(int customerID, String newValue) {
	    try {
	        Connector.a.connectDB();
	        String sql = "UPDATE customer SET name = ? WHERE customerID = ?";
	        PreparedStatement statement = Connector.a.connectDB().prepareStatement(sql);
	        statement.setString(1, newValue);
	        statement.setInt(2, customerID);
	        statement.executeUpdate();
	        Connector.a.connectDB().close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	private void updateContactInfo(int customerID, String newValue) {
	    try {
	        Connector.a.connectDB();
	        String sql = "UPDATE customer SET contactInfo = ? WHERE customerID = ?";
	        PreparedStatement statement = Connector.a.connectDB().prepareStatement(sql);
	        statement.setString(1, newValue);
	        statement.setInt(2, customerID);
	        statement.executeUpdate();
	        Connector.a.connectDB().close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	private void getData() {
		String SQL = "SELECT * FROM customer ORDER BY customerID";
		try {
			Connector.a.connectDB();
			java.sql.Statement state = Connector.a.connectDB().createStatement();
			ResultSet rs = state.executeQuery(SQL);
			while (rs.next()) {
				customer cat = new customer(rs.getInt(1),
						rs.getString(2).toString(),rs.getString(2).toString());
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
	
	public void showDialog(String title, String header, String body, AlertType type) {
    	Alert alert = new Alert(type); // infotrmation or error or..
    	alert.setTitle(title);
    	alert.setHeaderText(header);
    	alert.setContentText(body);
    	alert.show();

    }
    @FXML
    void deleteonAction(ActionEvent event) {
    	ObservableList<customer> selectedRows = tableview.getSelectionModel().getSelectedItems();
		ArrayList<customer> rows = new ArrayList<>(selectedRows);
		if(rows.size()==0) {
			return;
		}
	
		deleteRow(rows.get(0).getCustomerID());
		initialize();

    }
    private void deleteRow(int id) {
        try {
            Connector.a.connectDB();
            String sql = "DELETE FROM customer WHERE customerID = ?";
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
    		customer rc;
			rc = new customer(Integer.parseInt(newID.getText()), newname.getText(), Newcontact.getText());
			customer.cus= rc;
			insertData(rc);
			newID.clear();
			newname.clear();
			Newcontact.clear();
				
		} catch (Exception e) {
			showDialog(null, "Wrong input!!", "Please check the input again", AlertType.ERROR);
		}
    }
    
    private void insertData(customer rc) {
        try {
            Connector.a.connectDB();
            String sql = "INSERT INTO customer (customerID, name, contactInfo) VALUES (?, ?, ?)";
            PreparedStatement ps = Connector.a.connectDB().prepareStatement(sql);
            ps.setInt(1, rc.getCustomerID());
            ps.setString(2, rc.getName()); // corrected method name
            ps.setString(3, rc.getContactInfo());
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
					customerID(d, Integer.parseInt(updateId.getText())); 
					
				if (updatecont.getText().length() > 0) {
					updateContactInfo(d, updatecont.getText());
				}
				}
					
				
			//	refresh();
			    oldId.clear();
			    updatename.clear();
				updateId.clear();
				updatecont.clear();
				initialize();

			}
		} catch (Exception e) {
			 oldId.clear();
			    updatename.clear();
				updateId.clear();
				updatecont.clear();
			showDialog("Watch out!!", "", "you entered a rong format", AlertType.ERROR);
		}
    }

}
