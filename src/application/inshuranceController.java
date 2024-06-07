package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class inshuranceController {

	private ArrayList<inshuranceData> data;
	private ObservableList<inshuranceData> dataList;

	@FXML
	private TextField searchID;

	@FXML
	private Button add;

	@FXML
	private TextField addID;

	@FXML
	private TextField addName;

	@FXML
	private Button delete;

	@FXML
	private TextField deleteID;

	@FXML
	private Button update;

	@FXML
	private TextField updateOldID;

	@FXML
	private TextField updateNewID;

	@FXML
	private TextField updateName;

	@FXML
	private TextField updateCompanyName;

	@FXML
	private TextField addCompanyName;

	@FXML
	private TableColumn<inshuranceData, Integer> customerID;

	@FXML
	private TableColumn<inshuranceData, String> customerName;

	@FXML
	private TableColumn<inshuranceData, String> companyName;

	@FXML
	private TableView<inshuranceData> TableData;
	@FXML
	private Button btnback;

	@FXML
	public void initialize() {
		data = new ArrayList<>();
		dataList = FXCollections.observableArrayList(data);
		TableData.setEditable(true);

		customerID.setCellFactory(
				TextFieldTableCell.<inshuranceData, Integer>forTableColumn(new IntegerStringConverter()));
		customerID.setCellValueFactory(new PropertyValueFactory<inshuranceData, Integer>("customerID"));
		customerID.setOnEditCommit((CellEditEvent<inshuranceData, Integer> t) -> {
			((inshuranceData) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setCustomerID(t.getNewValue());

			updateID(t.getRowValue().getCustomerID(), t.getNewValue());

		});

		customerName.setCellValueFactory(new PropertyValueFactory<inshuranceData, String>("coustumerName"));
		customerName.setCellFactory(TextFieldTableCell.<inshuranceData>forTableColumn());
		customerName.setOnEditCommit((CellEditEvent<inshuranceData, String> t) -> {
			((inshuranceData) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setCoustumerName(t.getNewValue());

			updateName(t.getRowValue().getCustomerID(), t.getNewValue());

		});

		companyName.setCellValueFactory(new PropertyValueFactory<inshuranceData, String>("inshurance_companyName"));
		companyName.setCellFactory(TextFieldTableCell.<inshuranceData>forTableColumn());
		companyName.setOnEditCommit((CellEditEvent<inshuranceData, String> t) -> {
			((inshuranceData) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setInshurance_companyName(t.getNewValue());

			updateCompanyName(t.getRowValue().getCustomerID(), t.getNewValue());

		});

		getData();
		TableData.setItems(dataList);
		search();
	}

	public void getData() {
		String SQL = "select * from insurance";
		try {
			Connector.a.connectDB();
			java.sql.Statement state = Connector.a.connectDB().createStatement();
			ResultSet rs = state.executeQuery(SQL);
			while (rs.next()) {
				inshuranceData em = new inshuranceData(Integer.parseInt(rs.getString(1).toString()),
						rs.getString(2).toString(), rs.getString(3).toString());
				dataList.add(em);
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
	void back(ActionEvent event) {

		try { // open new stage
			Stage stage;
			Parent root;
			stage = (Stage) btnback.getScene().getWindow();
			stage.close();
			root = FXMLLoader.load(getClass().getResource("Start.fxml"));
			Scene scene = new Scene(root, 901, 649);
			stage.setScene(scene);
			stage.setTitle("Pharmacy");
			stage.show();

		} catch (IOException e1) {

		}
	}

	public void updateID(int oldID, int newID) {
	    String sql = "UPDATE insurance SET customerID = ? WHERE customerID = ?";
	    try (Connection conn = Connector.a.connectDB();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, newID);
	        ps.setInt(2, oldID);
	        ps.executeUpdate();
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}

	public void updateName(int id, String name) {
	    String sql = "UPDATE insurance SET customerName = ? WHERE customerID = ?";
	    try (Connection conn = Connector.a.connectDB();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, name);
	        ps.setInt(2, id);
	        ps.executeUpdate();
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}

	public void updateCompanyName(int id, String name) {
	    String sql = "UPDATE insurance SET insurance_companyName = ? WHERE customerID = ?";
	    try (Connection conn = Connector.a.connectDB();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, name);
	        ps.setInt(2, id);
	        ps.executeUpdate();
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}


	@FXML
	void updateOnAction(ActionEvent event) {

		try {
			if (updateOldID.getText() != null) {
				int id = Integer.parseInt(updateOldID.getText());
				if (updateName.getText().length() > 0) {
//					System.out.println(newDateBirth.getText());
					updateName(id, updateName.getText());
				}
				if (updateCompanyName.getText().length() > 0) {
//					System.out.println(Newdateofemployment.getText());
					updateCompanyName(id, updateCompanyName.getText());
				}
				if (updateNewID.getText().length() > 0) {
//					System.out.println(newName.getText());
					updateID(id, Integer.parseInt(updateNewID.getText()));
				}
				initialize();

				updateOldID.clear();
				updateNewID.clear();
				updateName.clear();
				updateCompanyName.clear();

			}
		} catch (Exception e) {

		}
		initialize();

	}

	private void insertData(inshuranceData rc) {
	    String updateCompanySQL = "UPDATE insurance_company SET numberOfCustomer = numberOfCustomer + 1 WHERE insurance_companyName = ?";
	    String insertSQL = "INSERT INTO insurance (customerID, customerName, insurance_companyName) VALUES (?, ?, ?)";
	    try (Connection conn = Connector.a.connectDB();
	         PreparedStatement updatePs = conn.prepareStatement(updateCompanySQL);
	         PreparedStatement insertPs = conn.prepareStatement(insertSQL)) {

	        // Update company
	        updatePs.setString(1, rc.getInshurance_companyName());
	        updatePs.executeUpdate();

	        // Insert new record
	        insertPs.setInt(1, rc.getCustomerID());
	        insertPs.setString(2, rc.getCoustumerName());
	        insertPs.setString(3, rc.getInshurance_companyName());
	        insertPs.executeUpdate();

	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}


	@FXML
	void addOnAction(ActionEvent event) {

		try {
			inshuranceData rc;
			rc = new inshuranceData(Integer.parseInt(addID.getText()), addName.getText(), addCompanyName.getText());
			dataList.add(rc);
			insertData(rc);
			initialize();
			addID.clear();
			addName.clear();
			addCompanyName.clear();
		} catch (Exception e) {

		}

	}

	private void deleteRow(int id) {
	    String sql = "DELETE FROM insurance WHERE customerID = ?";
	    try (Connection conn = Connector.a.connectDB();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, id);
	        ps.executeUpdate();
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}


	@FXML
	void deleteOnAction(ActionEvent event) {

		try {
			if (deleteID.getText() != null) {
				int id = Integer.parseInt(deleteID.getText());
				deleteRow(id);
			}
			deleteID.clear();
		} catch (Exception e) {

		}
		initialize();

	}

	private void search() {
		// TODO Auto-generated method stub
		FilteredList<inshuranceData> filteredData = new FilteredList<>(dataList, b -> true);
		searchID.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(inshuranceData -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (inshuranceData.getCoustumerName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches car Id
				} else
					return false; // Does not match.
			});
		});
		SortedList<inshuranceData> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(TableData.comparatorProperty());
		TableData.setItems(sortedData);
	}

}
