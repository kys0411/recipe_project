import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FXController implements Initializable {
	@FXML
	TextField nickname, filePath, imgCount;

	@FXML
	TextArea process;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@FXML
	private void downloadButton(ActionEvent event) {
		process.setText("Hello");
	}
}
