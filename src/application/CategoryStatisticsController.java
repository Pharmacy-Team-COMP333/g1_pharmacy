package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;

public class CategoryStatisticsController implements Initializable {

    @FXML
    private StackedBarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private Connection connection;

    @Override
    	public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            connection = Connector.a.connectDB();

            String SQL = "SELECT c.cat_id, c.categories_name, COUNT(i.par_code) AS item_count " +
                         "FROM categories c " +
                         "LEFT JOIN item i ON c.cat_id = i.cat_id " +
                         "GROUP BY c.cat_id, c.categories_name " +
                         "ORDER BY c.cat_id";

            PreparedStatement statement = connection.prepareStatement(SQL);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int categoryId = rs.getInt("cat_id");
                String categoryName = rs.getString("categories_name");
                int itemCount = rs.getInt("item_count");

                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName("Category ID: " + categoryId);
                series.getData().add(new XYChart.Data<>(categoryName, itemCount));

                barChart.getData().add(series);
            }

            rs.close();
            statement.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
