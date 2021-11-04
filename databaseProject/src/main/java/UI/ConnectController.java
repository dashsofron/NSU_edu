package UI;

import Database.DBInit;
import Database.DBRequests;
import Database.UserRole;
import Database.user.User;
import UI.readers.AddReaderController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public class ConnectController {
    final static String someUrl = "jdbc:oracle:thin:@";
    final static String port = ":1521";
    final static String courseServerIp = "84.237.50.81";
    final static String courseUsername = "18208_Sofronova";
    final static String coursePassword = "nnhWHc9";
    final static String libraryAdmin = "libraryAdmin";
    final static String libraryAdminPassword = "admin";
    @FXML
    public AnchorPane connectPane;

    @FXML
    Button myServerButton;
    @FXML
    private TextField ipField;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passField;
    @FXML
    private TextArea infoArea;

    @FXML
    public void connectToCourseServer() throws SQLException, IOException {
        infoArea.clear();

        DBInit dbInit = DBInit.getInstance();
        String username = libraryAdmin;
        String password = libraryAdminPassword;
        if (!loginField.getText().equals(""))
            username = loginField.getText();
        if (!passField.getText().equals(""))
            password = passField.getText();
        try {
            dbInit.startConnection(someUrl + courseServerIp + port, username, password);

        } catch (Exception e) {
            infoArea.setText("не получилось подключиться");
        }
        //dbInit.init();
        openMenu(username);
    }

    @FXML
    public void connectToMyServer() throws SQLException, IOException {
        DBInit dbInit = DBInit.getInstance();
        infoArea.clear();

        try {


            if (ipField.getText() != null && !loginField.getText().equals("") && !passField.getText().equals("")) {
                dbInit.startConnection(someUrl + ipField.getText() + port, loginField.getText(), passField.getText());
                openMenu(loginField.getText());
            }
        } catch (Exception e) {
            infoArea.setText("не получилось подключиться");
        }
    }


    @FXML
    public void openMenu(String username) throws IOException {
        Parent root;
        DBRequests dbRequests = DBRequests.getInstance();
        User user = dbRequests.getUserByName(username);
        if (username.equals("libraryAdmin") && passField.getText().equals("admin"))
            openAdminMenu();
        else if (user.getUserRole().equals(UserRole.ПОЛЬЗОВАТЕЛЬ))
            openUserMenu(user);

    }


    public void openUserMenu(User user) throws IOException {
        if (user.getReaderId() == null) {
            Parent root;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/readers/addReaderWindow.fxml"));
                root = loader.load();
                AddReaderController c = loader.getController();
                Stage stage = new Stage();

                stage.setTitle("Добавление читателя");
                stage.setScene(new Scene(root));
                c.initController(user, stage);

                stage.initModality(Modality.APPLICATION_MODAL);

                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Parent root;
            try {
                //FXMLLoader loader = new FXMLLoader(getClass().getResource("/test-menu.fxml"));
                //FXMLLoader loader = new FXMLLoader(getClass().getResource("/libraryMenu.fxml"));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/userMenu.fxml"));

                root = loader.load();
                UserController controller = loader.getController();
                controller.initController(user);
                Stage stage = new Stage();
                stage.setTitle("Меню пользователя");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void openAdminMenu() throws IOException {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/adminMenu.fxml"));

            root = loader.load();
            AdminController controller = loader.getController();
            controller.initController();
            Stage stage = new Stage();
            stage.setTitle("Меню администратора");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void registrate() throws SQLException, IOException {

        DBInit dbInit = DBInit.getInstance();
        String username = libraryAdmin;
        String password = libraryAdminPassword;
        dbInit.startConnection(someUrl + courseServerIp + port, username, password);
        DBRequests dbRequests = DBRequests.getInstance();
        //dbInit.init();
        User user = new User();
        user.setUsername(loginField.getText());
        user.setPassword(passField.getText());
        user.setUserRole(UserRole.ПОЛЬЗОВАТЕЛЬ);
        dbRequests.createUser(user);
        dbInit.startConnection(someUrl + courseServerIp + port, user.getUsername(), user.getPassword());
        openUserMenu(user);


        //dbInit.init();
    }

    @FXML
    public void initUsers() throws SQLException, UnsupportedEncodingException {
        DBInit dbInit = DBInit.getInstance();
        String username = libraryAdmin;
        String password = libraryAdminPassword;
        if (!loginField.getText().equals(""))
            username = loginField.getText();
        if (!passField.getText().equals(""))
            password = passField.getText();

        dbInit.startConnection(someUrl + courseServerIp + port, username, password);
        dbInit.initAll();
        //dbInit.init();
    }
}
