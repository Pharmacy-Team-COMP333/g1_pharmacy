package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;

public class ItemStatisticsController {

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private StackedBarChart<String, Number> barChart;

    @FXML
    public void initialize() {
        showTopSales();
    }

    private void showTopSales() {
        try (Connection conn = Connector.a.connectDB();

             PreparedStatement statement = conn.prepareStatement("SELECT item_name, sale_price FROM item ORDER BY sale_price DESC LIMIT 5");
             ResultSet resultSet = statement.executeQuery()) {

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            while (resultSet.next()) {
                String itemName = resultSet.getString("item_name");
                double salePrice = resultSet.getDouble("sale_price");
                series.getData().add(new XYChart.Data<>(itemName, salePrice));
            }
            barChart.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
}
