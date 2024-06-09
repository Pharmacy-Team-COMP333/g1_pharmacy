package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmployeeStaticController {
    @FXML
    private PieChart paymentChart;

    @FXML
    public void initialize() {
        fetchPaymentStatistics();
    }

    private void fetchPaymentStatistics() {
        try {
            Connector.a.connectDB();
            
            // Query to get the total payments
            String query = "SELECT " +
                           "(SELECT SUM(work_hours * hour_price * 24) FROM hourly_employee) AS total_hourly_payment, " +
                           "(SELECT SUM(amount_paid) FROM contract_employee) AS total_contract_payment;";
            PreparedStatement st = Connector.a.connectDB().prepareStatement(query);
            ResultSet rs = st.executeQuery();
            
            if (rs.next()) {
                double totalHourlyPayment = rs.getDouble("total_hourly_payment");
                double totalContractPayment = rs.getDouble("total_contract_payment");
                
                // Adding data to the chart
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Hourly Employees", totalHourlyPayment),
                    new PieChart.Data("Contract Employees", totalContractPayment)
                );
                paymentChart.setData(pieChartData);
            }
            
            Connector.a.connectDB().close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
