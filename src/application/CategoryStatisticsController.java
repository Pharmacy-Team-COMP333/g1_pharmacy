package application;

import java.net.URL;
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

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        String SQL = "SELECT c.cat_id, c.categories_name, COUNT(*) AS item_count " +
                     "FROM categories c " +
                     "JOIN item i ON c.cat_id = i.item_name " +
                     "GROUP BY cat_id, categories_name " +
                     "ORDER BY cat_id";

        try {
            Connector.a.connectDB();
            java.sql.Statement state = Connector.a.connectDB().createStatement();
            ResultSet rs = state.executeQuery(SQL);

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
            state.close();
            Connector.a.connectDB().close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}