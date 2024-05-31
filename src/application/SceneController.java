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
            PreparedStatement st = Connector.a.connectDB()
                    .prepareStatement("SELECT * FROM employee WHERE employee_name = ? AND emp_password = ?");
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
                if (r1.getString(1).equals(UserName.getText()) && r1.getString(4).equals(PassWord.getText())) {
                    ManegName = UserName.getText();
                    ManegPassword = PassWord.getText();
                    Manegar.mng.setName(ManegName);
                    Manegar.mng.setPassword(ManegPassword);

                    try {
                        stage = (Stage) btnLogin.getScene().getWindow();
                        stage.close();
                        root = FXMLLoader.load(getClass().getResource("Start.fxml"));
                        scene = new Scene(root, 901, 649);
                        stage.setScene(scene);
                        stage.setTitle("Chose One");
                        stage.show();
                    } catch (IOException e1) {
                        Message.displayMassage("There is no account at this username and password, Try again", "error");
                    }
                } else {
                    Message.displayMassage("There is no account at this username and password, Try again", "error");
                }
            } else {
                Message.displayMassage("There is no account at this username and password, Try again", "error");
            }

            PreparedStatement st2 = Connector.a.connectDB().prepareStatement("SELECT * FROM employee WHERE employee_name = ?");
            st2.setString(1, ManegName);
            ResultSet r2 = st2.executeQuery();
            if (r2.next()) {
                empId = r2.getInt(1);
            }
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        assert UserName != null : "fx:id=\"UserName\" was not injected: check your FXML file 'Scene.fxml'.";
        assert PassWord != null : "fx:id=\"PassWord\" was not injected: check your FXML file 'Scene.fxml'.";
    }
}