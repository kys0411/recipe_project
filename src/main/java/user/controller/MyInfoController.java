package user.controller;

import common.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import user.domain.User;

import java.io.IOException;

public class MyInfoController {

    @FXML
    private Label nicknameLabel;
    @FXML
    private Button mainpageButton;
    @FXML
    private Button memberupdateButton;

    @FXML
    public void initialize() {
        User loggedUser = UserSession.getInstance().getLoggedUser();
        if (loggedUser != null) {
            nicknameLabel.setText(loggedUser.getNickname().trim());
        }
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        Button clickedButton = (Button) event.getSource();
        String fxmlFile = "";

        if (clickedButton == mainpageButton) {
            fxmlFile = "/fxml/Main.fxml";
        } else if (clickedButton == memberupdateButton) {
            fxmlFile = "/fxml/userupdate.fxml";
        }

        switchScene(event, fxmlFile);
    }

    private void switchScene(ActionEvent event, String fxml) throws IOException {
        //메인 url 으로부터 네비게이션 하기위한 url 경로를 받는 메서드
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
