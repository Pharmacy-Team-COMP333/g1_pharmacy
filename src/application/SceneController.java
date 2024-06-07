package application;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class SceneController {
    public static String ManegName;
    public static int empId;
    public static String ManegPassword;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField UserName;

    @FXML
    private PasswordField PassWord;

    @FXML
    private Button btnLogin;

    @FXML
    void btnClick(ActionEvent event) {
        try {
            // Check if the provided credentials match the entity manager
            PreparedStatement st = Connector.a.connectDB()
                    .prepareStatement("SELECT * FROM ENTITY_MANAGER WHERE name = ? AND password = ?");
            st.setString(1, UserName.getText());
            st.setString(2, PassWord.getText());
            ResultSet r1 = st.executeQuery();

            if (UserName.getText().isEmpty()) {
                Message.displayMassage("Please enter the user name", "Warning");
                return;
            }

            if (PassWord.getText().isEmpty()) {
                Message.displayMassage("Please enter the password", "Warning");
                return;
            }

            if (r1.next()) {
                // Retrieve the manager's information and the current date
                ManegName = r1.getString("name");
                ManegPassword = r1.getString("password");
                String currentDate = r1.getString("date_created");

                Manegar.mng.setName(ManegName);
                Manegar.mng.setPassword(ManegPassword);

                // Display the current date in the application
                System.out.println("Current date: " + currentDate);

                try {
                    // Proceed with opening the next scene
                    stage = (Stage) btnLogin.getScene().getWindow();
                    stage.close();
                    root = FXMLLoader.load(getClass().getResource("Start.fxml"));
                    scene = new Scene(root, 901, 649);
                    stage.setScene(scene);
                    stage.setTitle("Chose One");
                    stage.show();
                } catch (IOException e1) {
                    Message.displayMassage("Error opening the next scene", "error");
                }
            } else {
                Message.displayMassage("Invalid username or password", "error");
            }
        } catch (ClassNotFoundException | SQLException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        assert UserName != null : "fx:id=\"UserName\" was not injected: check your FXML file 'Scene.fxml'.";
        assert PassWord != null : "fx:id=\"PassWord\" was not injected: check your FXML file 'Scene.fxml'.";
    }
}