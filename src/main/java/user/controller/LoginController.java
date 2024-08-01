package user.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import user.service.LoginService;
import common.UserSession;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button joinButton;

    private LoginService loginService = new LoginService();

    @FXML
    public void loginButtonOnAction(ActionEvent actionEvent) {
        String nickname = usernameTextField.getText();
        String password = passwordPasswordField.getText();

        if (nickname.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "로그인 실패", "닉네임과 비밀번호를 모두 입력해주세요.");
            return;
        }

        try {
            loginService.login(nickname, password);

            if (UserSession.getInstance().getLoggedUser() != null) {
                showAlert(Alert.AlertType.INFORMATION, "로그인 성공", "환영합니다, " + UserSession.getInstance().getLoggedUser().getNickname().trim() + "님!");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/RecipeReview.fxml"));
                Parent mainScreen = fxmlLoader.load();

                Stage stage = (Stage) loginButton.getScene().getWindow();

                Scene scene = new Scene(mainScreen);
                stage.setScene(scene);
                stage.show();
            } else {
                showAlert(Alert.AlertType.ERROR, "로그인 실패", "닉네임 또는 비밀번호가 잘못되었습니다.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "로그인 실패", "데이터베이스 오류가 발생했습니다.");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void cancelButtonOnAction() {
        clearFields();
    }

    @FXML
    public void joinButtonOnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
            Parent registerScreen = fxmlLoader.load();

            Stage stage = (Stage) joinButton.getScene().getWindow();

            Scene scene = new Scene(registerScreen);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "화면 전환 오류", "회원 가입 화면으로 전환하는 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        usernameTextField.clear();
        passwordPasswordField.clear();
    }
}
