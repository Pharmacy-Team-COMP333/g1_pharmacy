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
public class side_effectController {

    @FXML
    private TextField Newsev;

    @FXML
    private Button delete;

    @FXML
    private TableColumn<side_effect, Integer> idtable;

    @FXML
    private Button insert;

    @FXML
    private TableColumn<side_effect, String> nametabel;

    @FXML
    private TextField newID;

    @FXML
    private TextField newname;

    @FXML
    private TextField oldId;

    @FXML
    private TableColumn<side_effect, String> sevtable;

    @FXML
    private TableView<side_effect> tableview;

    @FXML
    private Button update;

    @FXML
    private TextField updateId;

    @FXML
    private TextField updatename;

    @FXML
    private TextField updatesev;
    private ArrayList<side_effect> data;
   	private ObservableList<side_effect> dataList;
   	public void initialize() {
		data = new ArrayList<>();
		dataList = FXCollections.observableArrayList(data);
		tableview.setEditable(true);
		
		idtable.setCellValueFactory(new PropertyValueFactory<side_effect, Integer>("sideEffectID"));
		idtable.setCellFactory(TextFieldTableCell.<side_effect, Integer>forTableColumn(new IntegerStringConverter()));
		idtable.setOnEditCommit((CellEditEvent<side_effect, Integer> t) -> {
			int d = t.getRowValue().getSideEffectID();
			((side_effect) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setSideEffectID(t.getNewValue()); // display
			// only

			updateID(d, t.getNewValue());
		});


		nametabel.setCellValueFactory(new PropertyValueFactory<side_effect, String>("name"));
		nametabel.setCellFactory(TextFieldTableCell.<side_effect>forTableColumn());
		nametabel.setOnEditCommit((CellEditEvent<side_effect, String> t) -> {
			((side_effect) t.getTableView().getItems().get(t.getTablePosition().getRow()))
			.setName(t.getNewValue()); // display
			// only

			updateName(t.getRowValue().getSideEffectID(), t.getNewValue());
		});

		
		sevtable.setCellValueFactory(new PropertyValueFactory<side_effect, String>("severity"));
		sevtable.setCellFactory(TextFieldTableCell.<side_effect>forTableColumn());
		sevtable.setOnEditCommit((CellEditEvent<side_effect, String> t) -> {
			((side_effect) t.getTableView().getItems().get(t.getTablePosition().getRow()))
		    .setSeverity(t.getNewValue());

			// only

			updateseverity1(t.getRowValue().getSideEffectID(), t.getNewValue());
		});

		getData();
		tableview.setItems(dataList);
		//searchRentalCategory();
	}
   	private void updateID(int d, Integer newValue) {
	    try {
	        Connector.a.connectDB();
	        String sql = "UPDATE side_effect SET sideEffectID = ? WHERE sideEffectID = ?";
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
   	private void updateName(int sideEffectID, String newValue) {
	    try {
	        Connector.a.connectDB();
	        String sql = "UPDATE side_effect SET name = ? WHERE sideEffectID = ?";
	        PreparedStatement statement = Connector.a.connectDB().prepareStatement(sql);
	        statement.setString(1, newValue);
	        statement.setInt(2, sideEffectID);
	        statement.executeUpdate();
	        Connector.a.connectDB().close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}
   	private void updateseverity1(int sideEffectID, String newValue) {
	    try {
	        Connector.a.connectDB();
	        String sql = "UPDATE side_effect SET severity = ? WHERE sideEffectID = ?";
	        PreparedStatement statement = Connector.a.connectDB().prepareStatement(sql);
	        statement.setString(1, newValue);
	        statement.setInt(2, sideEffectID);
	        statement.executeUpdate();
	        Connector.a.connectDB().close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}
   	private void getData() {
		String SQL = "SELECT * FROM side_effect ORDER BY sideEffectID";
		try {
			Connector.a.connectDB();
			java.sql.Statement state = Connector.a.connectDB().createStatement();
			ResultSet rs = state.executeQuery(SQL);
			while (rs.next()) {
				side_effect cat = new side_effect(rs.getInt(1),
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
    	ObservableList<side_effect> selectedRows = tableview.getSelectionModel().getSelectedItems();
		ArrayList<side_effect> rows = new ArrayList<>(selectedRows);
		if(rows.size()==0) {
			return;
		}
	
		deleteRow(rows.get(0).getSideEffectID());
		initialize();
    }
    private void deleteRow(int id) {
        try {
            Connector.a.connectDB();
            String sql = "DELETE FROM side_effect WHERE sideEffectID = ?";
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
    		side_effect rc;
			rc = new side_effect(Integer.parseInt(updateId.getText()), updatename.getText(), updatesev.getText());
			side_effect.cus= rc;
			insertData(rc);
			newID.clear();
			newname.clear();
			Newsev.clear();
				
		} catch (Exception e) {
			showDialog(null, "Wrong input!!", "Please check the input again", AlertType.ERROR);
		}
    }
    private void insertData(side_effect rc) {
        try {
            Connector.a.connectDB();
            String sql = "INSERT INTO side_effect (sideEffectID, name, severity) VALUES (?, ?, ?)";
            PreparedStatement ps = Connector.a.connectDB().prepareStatement(sql);
            ps.setInt(1, rc.getSideEffectID());
            ps.setString(2, rc.getName()); // corrected method name
            ps.setString(3, rc.getSeverity());
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
					updateID(d, Integer.parseInt(updateId.getText())); 
					
				if (updatesev.getText().length() > 0) {
					updateseverity1(d, updatesev.getText());
				}
				}
					
				
			//	refresh();
			    oldId.clear();
			    updatename.clear();
			    updateId.clear();
			    updatesev.clear();
				initialize();

			}
		} catch (Exception e) {
			 oldId.clear();
			    updatename.clear();
			    updateId.clear();
			    updatesev.clear();
			showDialog("Watch out!!", "", "you entered a rong format", AlertType.ERROR);
		}
    }

}
