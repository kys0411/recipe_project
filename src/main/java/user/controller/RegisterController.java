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
import user.dto.UserjoinRequestDto;
import user.service.UserService;

import java.io.IOException;
import java.sql.SQLException;

public class RegisterController {

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private PasswordField confirmpasswordPasswordField;
    @FXML
    private TextField hintTextField;
    @FXML
    private Button registerButton;
    @FXML
    private Button cancelButton;

    private UserService userService = new UserService();


    @FXML
    public void registerButtonOnAction(ActionEvent event) {
        String nickname = usernameTextField.getText();
        String password = passwordPasswordField.getText();
        String confirmPassword = confirmpasswordPasswordField.getText();
        String hint = hintTextField.getText();

        if (nickname.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "회원가입 실패", "모든 필드를 입력해주세요.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "회원가입 실패", "비밀번호가 일치하지 않습니다.");
            return;
        }

        UserjoinRequestDto joinRequest = UserjoinRequestDto.builder()
                                            .nickname(nickname)
                                            .hint(hint)
                                            .password(password)
                                            .build();

        try {
            userService.join(joinRequest);
            showAlert(Alert.AlertType.INFORMATION, "회원가입 성공", "회원가입이 완료되었습니다. 로그인해주세요.");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent mainScreen = fxmlLoader.load();

            Stage stage = (Stage) registerButton.getScene().getWindow();

            Scene scene = new Scene(mainScreen);
            stage.setScene(scene);
            stage.show();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "회원가입 실패", "데이터베이스 오류가 발생했습니다.");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void cancelButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent mainScreen = fxmlLoader.load();

        Stage stage = (Stage) cancelButton.getScene().getWindow();

        Scene scene = new Scene(mainScreen);
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void closeStage() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}