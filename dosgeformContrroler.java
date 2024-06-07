package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.PreparedStatement;



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

public class dosgeformContrroler {

    @FXML
    private Button Delete;

    @FXML
    private Button Delete2;

    @FXML
    private TextField SearchFiled;

    @FXML
    private TableView<dosgeData> TableData;

    @FXML
    private Button Update;

    @FXML
    private Button add;

    @FXML
    private Button btnback;

    @FXML
    private TextField deleteID;

    @FXML
    private TableColumn<dosgeData, String> dosage_form_Name;

    @FXML
    private TableColumn<dosgeData, Integer> dosage_form_id;

    @FXML
    private TextField newID;

    @FXML
    private TextField newName;

    @FXML
    private TextField oldID;
    
    private ArrayList<dosgeData> data;
	private ObservableList<dosgeData> dataList;
	
	

	public void initialize() {
		data = new ArrayList<>();
		dataList = FXCollections.observableArrayList(data);
		TableData.setEditable(true);
		
		dosage_form_id.setCellValueFactory(new PropertyValueFactory<dosgeData, Integer>("dosageFormID"));
		dosage_form_id.setCellFactory(TextFieldTableCell.<dosgeData, Integer>forTableColumn(new IntegerStringConverter()));
		dosage_form_id.setOnEditCommit((CellEditEvent<dosgeData, Integer> t) -> {
			int d = t.getRowValue().getDosageFormID();
			((dosgeData) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setDosageFormID(t.getNewValue()); // display
			// only

			updatedosgeID(d, t.getNewValue());
		});


		dosage_form_Name.setCellValueFactory(new PropertyValueFactory<dosgeData, String>("name"));
		dosage_form_Name.setCellFactory(TextFieldTableCell.<dosgeData>forTableColumn());
		dosage_form_Name.setOnEditCommit((CellEditEvent<dosgeData, String> t) -> {
			((dosgeData) t.getTableView().getItems().get(t.getTablePosition().getRow()))
			.setName(t.getNewValue()); // display
			// only

			updateName(t.getRowValue().getDosageFormID(), t.getNewValue());
		});


		getData();
		TableData.setItems(dataList);
		searchRentaldosge();
	}
	
	private void searchRentaldosge() {
		FilteredList<dosgeData> filteredData = new FilteredList<>(dataList, b -> true);
		SearchFiled.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(dosgeData -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (String.valueOf(dosgeData.getDosageFormID()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches car Id
				} else if (dosgeData.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches password
				}
				else
					return false; // Does not match.
			});
		});
		SortedList<dosgeData> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(TableData.comparatorProperty());
		TableData.setItems(sortedData);
	}

	
	
	private void updatedosgeID(int d, Integer newValue) {
	    try {
	        Connector.a.connectDB();
	        String sql = "UPDATE dosage_form SET dosageFormID = ? WHERE dosageFormID = ?";
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
	
	private void getData() {
		String SQL = "SELECT * FROM dosage_form ORDER BY dosageFormID";
		try {
			Connector.a.connectDB();
			java.sql.Statement state = Connector.a.connectDB().createStatement();
			ResultSet rs = state.executeQuery(SQL);
			while (rs.next()) {
				dosgeData dosge = new dosgeData(rs.getInt(1),
						rs.getString(2).toString());
				dataList.add(dosge);
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
	
	
	
	
	private void updateName(int cat_id, String newValue) {
	    try {
	        Connector.a.connectDB();
	        String sql = "UPDATE dosage_form SET name = ? WHERE dosageFormID = ?";
	        PreparedStatement statement = Connector.a.connectDB().prepareStatement(sql);
	        statement.setString(1, newValue);
	        statement.setInt(2, cat_id);
	        statement.executeUpdate();
	        Connector.a.connectDB().close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}

    @FXML
    void addOnAction(ActionEvent event) {
    	try {

			Stage stage = new Stage();
			Parent root;
			root = FXMLLoader.load(getClass().getResource("adddosgeform.fxml"));
			Scene scene = new Scene(root, 448, 263);
			stage.setScene(scene);
			stage.setTitle("Add Category");
			stage.showAndWait();

		} catch (Exception e) {
			// TODO: handle exception
		}
		if (dosgeData.dos != null) {
			dataList.add(dosgeData.dos);
		}
		dosgeData.dos = null;
		initialize();
	}

    @FXML
    void back(ActionEvent event) {
    	try { // open new stage
    		Stage stage;
    		Parent root;
    		stage = (Stage) btnback.getScene().getWindow();
    		stage.close();
    		root = FXMLLoader.load(getClass().getResource("Start.fxml"));
    		Scene scene = new Scene(root, 901, 649);
    		stage.setScene(scene);
    		stage.setTitle("Chose One");
    		stage.show();
    		
    	} catch (IOException e1) {
    		
    	}
    }

    @FXML
    void btnRefresh(ActionEvent event) {
        initialize();

    }

    @FXML
    void deleteOnAction(ActionEvent event) {
    	try {
			if (newID.getText() != null) {
				int id = Integer.parseInt(newID.getText());
				deleteRow(id);
			}
		
			newID.clear();
		} catch (Exception e) {

		}
		initialize();

    }

    @FXML
	void deleteOnAction2(ActionEvent event) {

		ObservableList<dosgeData> selectedRows = TableData.getSelectionModel().getSelectedItems();
		ArrayList<dosgeData> rows = new ArrayList<>(selectedRows);
		if(rows.size()==0) {
			return;
		}
	
		deleteRow(rows.get(0).getDosageFormID());
		initialize();

	}
    
    private void deleteRow(int id) {
        try {
            Connector.a.connectDB();
            String sql = "DELETE FROM dosage_form WHERE dosageFormID = ?";
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
    void updateOnAction(ActionEvent event) {
    	try {
			if (oldID.getText() != null) {
				int d = Integer.parseInt(oldID.getText()); 
				if (newName.getText().length() > 0) {
					updateName(d, newName.getText());
				}
				
				if (newID.getText().length() > 0) {
					updatedosgeID(d, Integer.parseInt(newID.getText())); 
					
				}
					
				
			//	refresh();
			    oldID.clear();
				newName.clear();
				newID.clear();
				initialize();

			}
		} catch (Exception e) {
			  oldID.clear();
				newName.clear();
				newID.clear();
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
