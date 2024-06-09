package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DBController {

    // FXML components injected from the FXML file
    @FXML
    private Button Bill;
    @FXML
    private Button Disease;
    @FXML
    private Button Doctor;
    @FXML
    private Label Label1;
    @FXML
    private Label Label2;
    @FXML
    private Label Label3;
    @FXML
    private Button Prescription;
    @FXML
    private Button Refresh;
    @FXML
    private LineChart<String, Double> Chart;
    @FXML
    private TableColumn<EmployeeOrder, String> EmployeeName;
    @FXML
    private TableColumn<EmployeeOrder, Integer> OrdersNumber;
    @FXML
    private TableView<EmployeeOrder> Table1;
    @FXML
    private TableColumn<ItemSales, String> Item;
    @FXML
    private TableColumn<ItemSales, Integer> NumberOfSales;
    @FXML
    private TableColumn<ItemSales, Double> SalesValue;
    @FXML
    private TableView<ItemSales> Table2;

    // Initialize method called after FXML components are injected
    @FXML
    public void initialize() {
        // Configure columns for Table1
        EmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        OrdersNumber.setCellValueFactory(new PropertyValueFactory<>("ordersNumber"));
        // Configure columns for Table2
        Item.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        NumberOfSales.setCellValueFactory(new PropertyValueFactory<>("numberOfSales"));
        SalesValue.setCellValueFactory(new PropertyValueFactory<>("salesValue"));
        // Refresh tables and chart
        refreshTable();
        refreshTable2();
        populateChart();
    }

    // Action method for the Bill button
    @FXML
    void Bill(ActionEvent event) {
        openStage("/Bill.fxml", "Bill Management");
    }

    // Action method for the Disease button
    @FXML
    void Disease(ActionEvent event) {
        openStage("/Disease.fxml", "Disease Management");
    }

    // Action method for the Doctor button
    @FXML
    void Doctor(ActionEvent event) {
        openStage("/Doctor.fxml", "Doctor Management");
    }

    // Action method for the Prescription button
    @FXML
    void Prescription(ActionEvent event) {
        openStage("/Prescription.fxml", "Prescription Management");
    }

    // Action method for the Refresh button
    @FXML
    void Refresh(ActionEvent event) {
        refreshTable();
        refreshTable2();
        double totalSales = getTotalSalesLastMonth();
        Label3.setText(String.format("        Total Sales Last  \n                Month: \n                  $%.2f", totalSales));
        double avgItemPrice = getAverageItemPrice();
        Label2.setText(String.format("       Average Item Price: \n         $%.2f", avgItemPrice));
        double highestBillValue = getHighestBillValueThisMonth();
        Label1.setText(String.format("        Highest Bill Value \n       This Month:\n    $%.2f", highestBillValue));
    }

    // Method to open a new stage with the specified FXML file
    private void openStage(String fxmlPath, String title) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to refresh Table1 with employee order data
    private void refreshTable() {
        ObservableList<EmployeeOrder> data = getEmployeeOrders();
        Table1.setItems(data);
    }

    // Method to retrieve employee order data from the database
    private ObservableList<EmployeeOrder> getEmployeeOrders() {
        ObservableList<EmployeeOrder> employeeOrders = FXCollections.observableArrayList();
        // SQL query to get employee order data
        String query = "SELECT e.employee_name, COUNT(o.orderID) AS ordersNumber " +
                       "FROM EMPLOYEE e " +
                       "LEFT JOIN ORDER_TABLE o ON e.id = o.employeeID " +
                       "GROUP BY e.employee_name " +
                       "ORDER BY ordersNumber DESC";
        try (Connection connection = Connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            // Process query results and populate employeeOrders list
            while (resultSet.next()) {
                String employeeName = resultSet.getString("employee_name");
                int ordersNumber = resultSet.getInt("ordersNumber");
                EmployeeOrder employeeOrder = new EmployeeOrder(employeeName, ordersNumber);
                employeeOrders.add(employeeOrder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeOrders;
    }

    // Method to refresh Table2 with item sales data
    private void refreshTable2() {
        ObservableList<ItemSales> data = getItemSales();
        Table2.setItems(data);
    }

    // Method to retrieve item sales data from the database
    private ObservableList<ItemSales> getItemSales() {
        ObservableList<ItemSales> itemSalesList = FXCollections.observableArrayList();
        // SQL query to get item sales data
        String query = "SELECT i.name, COUNT(oi.itemID) AS numberOfSales, SUM(oi.fullSalePrice * oi.quantity) AS salesValue " +
                "FROM ITEM i " +
                "JOIN ORDER_ITEM oi ON i.itemID = oi.itemID " +
                "GROUP BY i.name " +
                "ORDER BY salesValue DESC";
        try (Connection connection = Connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            // Process query results and populate itemSalesList
            while (resultSet.next()) {
                String item = resultSet.getString("name");
                int numberOfSales = resultSet.getInt("numberOfSales");
                double salesValue = resultSet.getDouble("salesValue");
                ItemSales itemSales = new ItemSales(item, numberOfSales, salesValue);
                itemSalesList.add(itemSales);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemSalesList;
    }

    // Method to retrieve total sales from the previous month
    private double getTotalSalesLastMonth() {
        double totalSales = 0.0;
        YearMonth lastMonth = YearMonth.now().minusMonths(1);
        LocalDate startDate = lastMonth.atDay(1);
        LocalDate endDate = lastMonth.atEndOfMonth();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // SQL query to get total sales for the previous month
        String query = "SELECT SUM(totalPrice) " +
                       "FROM BILL " +
                       "WHERE orderID IN (" +
                       "    SELECT orderID " +
                       "    FROM ORDER_TABLE " +
                       "    WHERE orderDate BETWEEN ? AND ?" +
                       ")";
        try (Connection connection = Connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, startDate.format(formatter));
            statement.setString(2, endDate.format(formatter));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                totalSales = resultSet.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalSales;
    }

    // Method to retrieve the average item price
    private double getAverageItemPrice() {
        double avgPrice = 0.0;
        // SQL query to calculate the average item price
        String query = "SELECT AVG(salePrice) FROM ITEM";
        try (Connection connection = Connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                avgPrice = resultSet.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avgPrice;
    }

    // Method to retrieve the highest bill value for the current month
    private double getHighestBillValueThisMonth() {
        double highestValue = 0.0;
        YearMonth thisMonth = YearMonth.now();
        LocalDate startDate = thisMonth.atDay(1);
        LocalDate endDate = thisMonth.atEndOfMonth();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // SQL query to get the highest bill value for the current month
        String query = "SELECT MAX(totalPrice) " +
                "FROM BILL " +
                "WHERE orderID IN (" +
                "    SELECT orderID " +
                "    FROM ORDER_TABLE " +
                "    WHERE orderDate BETWEEN ? AND ?" +
                ")";
        try (Connection connection = Connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, startDate.format(formatter));
            statement.setString(2, endDate.format(formatter));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                highestValue = resultSet.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return highestValue;
    }

    // Method to populate the line chart with sales data by category
    private void populateChart() {
        ObservableList<XYChart.Series<String, Double>> data = FXCollections.observableArrayList();
        // SQL query to get sales data by category
        String query = "SELECT c.categores_name AS Category, SUM(oi.fullSalePrice * oi.quantity) AS TotalSales " +
                       "FROM CATEGORY c " +
                       "JOIN ITEM i ON c.cat_id = i.cat_id " +
                       "JOIN ORDER_ITEM oi ON i.itemID = oi.itemID " +
                       "GROUP BY c.categores_name " +
                       "ORDER BY TotalSales DESC";
        try (Connection connection = Connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            // Process query results and populate the chart data
            while (resultSet.next()) {
                String category = resultSet.getString("Category");
                double totalSales = resultSet.getDouble("TotalSales");
                series.getData().add(new XYChart.Data<>(category, totalSales));
            }
            data.add(series);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Set the chart data
        Chart.setData(data);
    }
}
