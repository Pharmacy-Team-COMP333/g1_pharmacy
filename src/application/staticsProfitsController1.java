package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

public class staticsProfitsController1 {
    @FXML
    private Label netIncome;
    @FXML
    private Label numOrders;
    @FXML
    private Label Profits;
    @FXML
    private PieChart paymentMethods;
    @FXML
    private Label employeePayments;  // Add this label to your FXML file

    @FXML
    public void initialize() {
        try {
            Connector.a.connectDB();
            // Number of orders
            PreparedStatement st2 = Connector.a.connectDB().prepareStatement("SELECT COUNT(*) FROM bill;");
            ResultSet r2 = st2.executeQuery();
            if (r2.next()) {
                numOrders.setText(r2.getInt(1) + "");
            }

            // Total profits
            PreparedStatement st3 = Connector.a.connectDB().prepareStatement("SELECT SUM(profits) FROM bill;");
            ResultSet r3 = st3.executeQuery();
            if (r3.next()) {
                Profits.setText(r3.getDouble(1) + "$");
            }

            // Total net income
            PreparedStatement st4 = Connector.a.connectDB().prepareStatement("SELECT SUM(full_price) FROM bill;");
            ResultSet r4 = st4.executeQuery();
            if (r4.next()) {
                netIncome.setText(r4.getDouble(1) + "$");
            }

            // Payment methods
            int cash = 0;
            int insurance = 0;

            PreparedStatement st5 = Connector.a.connectDB().prepareStatement("SELECT COUNT(*) FROM bill WHERE bill_type = 'cash';");
            ResultSet r5 = st5.executeQuery();
            if (r5.next()) {
                cash = r5.getInt(1);
            }

            PreparedStatement st6 = Connector.a.connectDB().prepareStatement("SELECT COUNT(*) FROM bill WHERE bill_type = 'insurance';");
            ResultSet r6 = st6.executeQuery();
            if (r6.next()) {
                insurance = r6.getInt(1);
            }

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Insurance", insurance),
                    new PieChart.Data("Cash", cash)
            );
            paymentMethods.setData(pieChartData);
            paymentMethods.setStartAngle(90);
            paymentMethods.setLabelsVisible(true);

            // Total payments to employees
            PreparedStatement st7 = Connector.a.connectDB().prepareStatement("SELECT SUM(payment_amount) FROM employee_payments;");
            ResultSet r7 = st7.executeQuery();
            if (r7.next()) {
                employeePayments.setText("Total Payments to Employees: " + r7.getDouble(1) + "$");
            }

            Connector.a.connectDB().close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
