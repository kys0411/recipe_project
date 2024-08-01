package common;
/*
* 작성일 2024-08-01
* 작성자 황석현
* */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import user.domain.User;
import user.service.LoginService;

import java.io.IOException;

public class MainController {

    @FXML
    private Label memberLabel;
    @FXML
    private Button btnMyInfo;
    @FXML
    private Button btnRecipe;
    @FXML
    private Button btnCreateRecipe;
    @FXML
    private Button btnReview;
    @FXML
    private Button btnLogout;

    private LoginService loginService = new LoginService();

    @FXML
    public void initialize() {
        User loggedUser = UserSession.getInstance().getLoggedUser();
        if (loggedUser != null) {
            memberLabel.setText("환영합니다, " + loggedUser.getNickname().trim() + "님!");
        }
    }

    // 각 버튼 클릭 시 호출될 메서드
    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        Button clickedButton = (Button) event.getSource();
        String fxmlFile = "";

        if (clickedButton == btnMyInfo) {
            fxmlFile = "/fxml/myInfo.fxml";
        } else if (clickedButton == btnRecipe) {
            fxmlFile = "/fxml/recipeDetail.fxml";
        } else if (clickedButton == btnCreateRecipe) {
            fxmlFile = "/fxml/createRecipe.fxml";
        } else if (clickedButton == btnReview) {
            fxmlFile = "/fxml/recipeReview.fxml";
        } else if (clickedButton == btnLogout) {
           loginService.logout();
            showAlert(Alert.AlertType.INFORMATION, "로그아웃", "로그아웃 되었습니다.");
           fxmlFile = "/fxml/login.fxml";
        }

        switchScene(event, fxmlFile);
    }

    /*
     * 화면이동 (NavigationEvent)
     * @param event
     * @param fxml
     * @throws IOException
     * */
    private void switchScene(ActionEvent event, String fxml) throws IOException {
        //메인 url 으로부터 네비게이션 하기위한 url 경로를 받는 메서드
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
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
}
