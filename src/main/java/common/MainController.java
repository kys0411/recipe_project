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
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private Button btnMyInfo;
    @FXML
    private Button btnRecipe;
    @FXML
    private Button btnReview;
    @FXML
    private Button btnLogout;

    // 각 버튼 클릭 시 호출될 메서드
    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        Button clickedButton = (Button) event.getSource();
        String fxmlFile = "";

        if (clickedButton == btnMyInfo) {
            fxmlFile = "/fxml/myInfo.fxml";
        } else if (clickedButton == btnRecipe) {
            fxmlFile = "/fxml/recipeDetail.fxml";
        } else if (clickedButton == btnReview) {
            fxmlFile = "/fxml/recipeReview.fxml";
        } else if (clickedButton == btnLogout) {
            // 로그아웃 처리 로직
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("로그아웃");
            alert.setHeaderText("로그아웃 하시겠습니까?");
            alert.setContentText("프로그램이 종료됩니다.");
            if (alert.showAndWait().get() == ButtonType.OK) {
                System.out.println("프로그램 종료");
                ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
            }
            return;
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
}
