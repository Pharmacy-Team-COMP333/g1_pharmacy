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

public class BillController {
	
    // FXML Declarations for UI Components
    @FXML private Button AddBill;
    @FXML private TableView<Bill> BillTable;
    @FXML private TableColumn<Bill, String> BillTypeColumn;
    @FXML private TextField BillTypeTF;
    @FXML private TableColumn<Bill, Integer> Bill_IDColumn;
    @FXML private TextField Bill_IDTF;
    @FXML private Button DeleteBill;
    @FXML private Button DeleteSelected;
    @FXML private TableColumn<Bill, Integer> OrderIDColumn;
    @FXML private TextField OrderIDTF;
    @FXML private TableColumn<Bill, Integer> PaymentMethodIDColumn;
    @FXML private TextField PaymentMethodIDTF;
    @FXML private TableColumn<Bill, Integer> ProfitColumn;
    @FXML private TextField ProfitTF;
    @FXML private Button Refresh;
    @FXML private Button SearchB;
    @FXML private TextField SearchTF;
    @FXML private Button SelectBill;
    @FXML private TableColumn<Bill, Integer> TotalPriceColumn;
    @FXML private TextField TotalPriceTF;
    @FXML private Button UpdateBill;
    @FXML private Button UpdateSelected;
    
    
    // ObservableList to store bill data
    private ObservableList<Bill> billList = FXCollections.observableArrayList();

    //Automatically after the FXML file is loaded
    @FXML
    void initialize() {
        Bill_IDColumn.setCellValueFactory(new PropertyValueFactory<>("billID"));
        TotalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        ProfitColumn.setCellValueFactory(new PropertyValueFactory<>("profit"));
        BillTypeColumn.setCellValueFactory(new PropertyValueFactory<>("billType"));
        OrderIDColumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        PaymentMethodIDColumn.setCellValueFactory(new PropertyValueFactory<>("paymentMethodID"));

        // Load data into the table
        loadBillData();
    }
    
    //Load bill data from the database into the table
    private void loadBillData() {
        billList.clear();
        try (Connection conn = Connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM BILL");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                billList.add(new Bill(
                        rs.getInt("billID"),
                        rs.getInt("totalPrice"),
                        rs.getInt("profit"),
                        rs.getString("billType"),
                        rs.getInt("orderID"),
                        rs.getInt("paymentMethodID")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        BillTable.setItems(billList);
    }

    //Open the Add Bill window
    @FXML
    void AddBill(ActionEvent event) {
        openStage("/AddBill.fxml", "Add New Bill");
    }

    //Open the Delete Bill window
    @FXML
    void DeleteBill(ActionEvent event) {
        openStage("/DeleteBill.fxml", "Delete Bill");
    }
    
    //Open the Update Bill window
    @FXML
    void UpdateBill(ActionEvent event) {
        openStage("/UpdateBill.fxml", "Update Bill");
    }

    //Delete the selected bill
    @FXML
    void DeleteSelected(ActionEvent event) {
        Bill selectedBill = BillTable.getSelectionModel().getSelectedItem();
        if (selectedBill != null) {
            try (Connection conn = Connector.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM BILL WHERE billID = ?")) {

                stmt.setInt(1, selectedBill.getBillID());
                stmt.executeUpdate();
                loadBillData();
                showAlert("Bill Deleted Successfully");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error occurred while deleting the bill: " + e.getMessage());
            }
        } else {
            showAlert("No Bill Selected. Please select a bill in the table.");
        }
        clearTextFields();
    }

    //Refresh the table data
    @FXML
    void Refresh(ActionEvent event) {
        loadBillData();
    }

    //Search for bills based on user input
    @FXML
    void SearchB(ActionEvent event) {
        String searchText = SearchTF.getText();
        billList.clear();
        try (Connection conn = Connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM BILL WHERE billID LIKE ? OR totalPrice LIKE ? OR profit LIKE ? OR billType LIKE ? OR orderID LIKE ? OR paymentMethodID LIKE ?")) {
            stmt.setString(1, "%" + searchText + "%");
            stmt.setString(2, "%" + searchText + "%");
            stmt.setString(3, "%" + searchText + "%");
            stmt.setString(4, "%" + searchText + "%");
            stmt.setString(5, "%" + searchText + "%");
            stmt.setString(6, "%" + searchText + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                billList.add(new Bill(
                        rs.getInt("billID"),
                        rs.getInt("totalPrice"),
                        rs.getInt("profit"),
                        rs.getString("billType"),
                        rs.getInt("orderID"),
                        rs.getInt("paymentMethodID")
                ));
            }
            BillTable.setItems(billList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Select a bill from the table and populate the text fields with its data
    @FXML
    void SelectBill(ActionEvent event) {
        Bill selectedBill = BillTable.getSelectionModel().getSelectedItem();
        if (selectedBill != null) {
            Bill_IDTF.setText(String.valueOf(selectedBill.getBillID()));
            TotalPriceTF.setText(String.valueOf(selectedBill.getTotalPrice()));
            ProfitTF.setText(String.valueOf(selectedBill.getProfit()));
            BillTypeTF.setText(selectedBill.getBillType());
            OrderIDTF.setText(String.valueOf(selectedBill.getOrderID()));
            PaymentMethodIDTF.setText(String.valueOf(selectedBill.getPaymentMethodID()));
        } else {
            showAlert("Please select a bill from the table.");
        }
    }

    //Update the selected bill
    @FXML
    void UpdateSelected(ActionEvent event) {
        Bill selectedBill = BillTable.getSelectionModel().getSelectedItem();
        if (selectedBill != null) {
            try {
                int billID = selectedBill.getBillID();
                int totalPrice = Integer.parseInt(TotalPriceTF.getText());
                int profit = Integer.parseInt(ProfitTF.getText());
                String billType = BillTypeTF.getText();
                int orderID = Integer.parseInt(OrderIDTF.getText());
                int paymentMethodID = Integer.parseInt(PaymentMethodIDTF.getText());

                // Check if the updated Order ID exists in the database
                if (!isOrderIdExists(orderID)) {
                    showAlert("Order ID Not Found. The specified Order ID does not exist.");
                    return;
                }

                // Check if the updated Payment Method ID exists in the database
                if (!isPaymentMethodIdExists(paymentMethodID)) {
                    showAlert("Payment Method ID Not Found. The specified Payment Method ID does not exist.");
                    return;
                }

                // Check if the updated Order ID is already used in the BILL table
                if (isDuplicateOrderId(orderID, billID)) {
                    showAlert("Duplicate Order ID. The specified Order ID is already used in another bill.");
                    return;
                }

                // Update the selected bill with the new data
                selectedBill.setTotalPrice(totalPrice);
                selectedBill.setProfit(profit);
                selectedBill.setBillType(billType);
                selectedBill.setOrderID(orderID);
                selectedBill.setPaymentMethodID(paymentMethodID);

                // Update the bill record in the database
                if (updateBill(selectedBill)) {
                    loadBillData();
                    clearTextFields();
                    showAlert("Bill updated successfully.");
                } else {
                    showAlert("Failed to update bill.");
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid input. Please enter valid numbers for Total Price, Profit, Order ID, and Payment Method ID.");
            }
        } else {
            showAlert("Please select a bill from the table.");
        }
        clearTextFields();
    }

    //Check if an Order ID exists in the database
    private boolean isOrderIdExists(int orderID) {
        String query = "SELECT 1 FROM order_table WHERE orderID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, orderID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    //Check if a Payment Method ID exists in the database
    private boolean isPaymentMethodIdExists(int paymentMethodID) {
        String query = "SELECT 1 FROM payment_method WHERE paymentMethodID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, paymentMethodID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    //Check if an Order ID is already used in another bill record
    private boolean isDuplicateOrderId(int orderID, int currentBillID) {
        String query = "SELECT 1 FROM BILL WHERE orderID = ? AND billID != ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, orderID);
            preparedStatement.setInt(2, currentBillID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return true;
        }
    }

    //Update a bill record in the database
    private boolean updateBill(Bill bill) {
        String query = "UPDATE BILL SET totalPrice = ?, profit = ?, billType = ?, orderID = ?, paymentMethodID = ? WHERE billID = ?";
        try (Connection conn = Connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, bill.getTotalPrice());
            preparedStatement.setInt(2, bill.getProfit());
            preparedStatement.setString(3, bill.getBillType());
            preparedStatement.setInt(4, bill.getOrderID());
            preparedStatement.setInt(5, bill.getPaymentMethodID());
            preparedStatement.setInt(6, bill.getBillID());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
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
        Bill_IDTF.clear();
        TotalPriceTF.clear();
        ProfitTF.clear();
        BillTypeTF.clear();
        OrderIDTF.clear();
        PaymentMethodIDTF.clear();
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
