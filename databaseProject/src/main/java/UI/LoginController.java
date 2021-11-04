package UI;

import Database.DBInit;
import Database.DBRequests;
import Database.UserRole;
import Database.user.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
     TextField userLoginField;
    @FXML
     TextField userPasswordField;
    @FXML
     ComboBox<UserRole> roleBox;
    @FXML
     Button startWorkButton;
    @FXML
     Button addUserButton;
    @FXML
     TextField userIdField;
    @FXML
    TextArea infoArea;
    private final DBRequests dbRequests = DBRequests.getInstance();

    public void initController(){
        roleBox.getItems().addAll(UserRole.АДМИНИСТРАТОР,UserRole.ПОЛЬЗОВАТЕЛЬ);
    }

    @FXML
    public void addUser(){

    }
    @FXML
    public void startWork() throws IOException, SQLException {
//        DBInit dbInit = DBInit.getInstance();
//        dbInit.init();
        User user=dbRequests.getUserByName(userLoginField.getText());
        if(user!=null){
            switch (user.getUserRole()){
                case ПОЛЬЗОВАТЕЛЬ: openUserMenu(user);break;
                case АДМИНИСТРАТОР:openAdminMenu();
            }
        }
        else {
            infoArea.setText("Пользователь с таким логином и паролем не найден.\n" +
                    "Попробуйте другой пароль или зарегистрируйтесь");
        }

    }

    public void openUserMenu(User user) throws IOException {
        Parent root;
        try {
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/test-menu.fxml"));
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/libraryMenu.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/userMenu.fxml"));

            root = loader.load();
            UserController controller=loader.getController();
            controller.initController(user);
            Stage stage = new Stage();
            stage.setTitle("Меню пользователя");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openAdminMenu() throws IOException {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/adminMenu.fxml"));

            root = loader.load();
            AdminController controller=loader.getController();
            controller.initController();
            Stage stage = new Stage();
            stage.setTitle("Меню администратора");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
