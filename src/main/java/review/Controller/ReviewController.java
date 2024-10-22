package review.Controller;

import common.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import review.domain.Review;
import review.impl.ReviewServiceImpl;
import review.service.ReviewService;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReviewController implements Initializable {

    @FXML
    private TableView<Review> selectReviewMember;
    @FXML
    private Button goMain;
    @FXML
    private TableColumn<Review, CheckBox> colCbDelete;
    @FXML
    private TableColumn<Review, Long> reviewId;
    @FXML
    private TableColumn<Review, String> recipeName;
    @FXML
    private TableColumn<Review, String> nickName;
    @FXML
    private TableColumn<Review, String> content;
    @FXML
    private TableColumn<Review, Long> starRating;
    @FXML
    private TableColumn<Review, Date> reviewDate;
    @FXML
    private CheckBox cbAll;
    @FXML
    private TextField tfNickname;
    @FXML
    private TextField tfRating;
    @FXML
    private TextArea taContent;
    @FXML
    private Button selectAllRecipeReview;

    /*
     *등록한 레시피 후기 전체 조회 화면 Navigation
     * @param event
     * */
    @FXML
    public void selectAllRecipeReview(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String fxmlFile = "";

        if (clickedButton == selectAllRecipeReview) {
            fxmlFile = "/fxml/RecipeReview.fxml";
        }

        try {
            switchScene(event, fxmlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        loadReviews(true);
    }
    /*
     *등록한 레시피 후기 내꺼 조회 화면 Navigation
     * @param event
     * */
    @FXML
    public void selectMyRecipeReview(ActionEvent event) {
        loadReviews(false);
    }

    private ReviewService reviewService = new ReviewServiceImpl();

    Stage stage;
    private void loadReviews(boolean loadAll) {
        try {
            List<Review> reviewList;
            if (loadAll) {
                reviewList = reviewService.selectAllRecipeReview(UserSession.getInstance().getLoggedUser().getId());
            } else {
                reviewList = reviewService.selectMyRecipeReview(UserSession.getInstance().getLoggedUser().getId());
            }

            if (reviewList != null) {
                ObservableList<Review> list = FXCollections.observableArrayList(reviewList);

                colCbDelete.setCellValueFactory(new PropertyValueFactory<>("cbDelete"));
                reviewId.setCellValueFactory(new PropertyValueFactory<>("id"));
                nickName.setCellValueFactory(new PropertyValueFactory<>("nickName"));
                recipeName.setCellValueFactory(new PropertyValueFactory<>("recipeName"));
                content.setCellValueFactory(new PropertyValueFactory<>("content"));
                starRating.setCellValueFactory(new PropertyValueFactory<>("starRating"));
                reviewDate.setCellValueFactory(new PropertyValueFactory<>("date"));

                selectReviewMember.setItems(list);
                cbAll.setSelected(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadReviews(true);

        // 데이터 초기화
        try {
            long id = 1;

            List<Review> reviewList = reviewService.selectAllRecipeReview(id);
            if (reviewList != null) {
                for (Review review : reviewList) {
                    System.out.println(review);
                }

                ObservableList<Review> list = FXCollections.observableArrayList(reviewList);

                colCbDelete.setCellValueFactory(new PropertyValueFactory<>("cbDelete"));
                reviewId.setCellValueFactory(new PropertyValueFactory<>("id"));
                nickName.setCellValueFactory(new PropertyValueFactory<>("nickName"));
                recipeName.setCellValueFactory(new PropertyValueFactory<>("recipeName"));
                content.setCellValueFactory(new PropertyValueFactory<>("content"));
                starRating.setCellValueFactory(new PropertyValueFactory<>("starRating"));
                reviewDate.setCellValueFactory(new PropertyValueFactory<>("date"));

                // TableView 에 데이터 리스트 지정
                selectReviewMember.setItems(list);

                // 테이블 갱신하고 전체 CheckBox 해제상태 설정유지
                cbAll.setSelected(false);

                // 전체 리뷰후기 선택 이벤트 cbAll(CheckBoxAll)
                cbAll.setOnMouseClicked(new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                        CheckBox checkBox = (CheckBox) event.getSource();
                        boolean checkAll = checkBox.isSelected();
                        selectReviewMember.getItems().forEach(review -> review.getCbDelete().setSelected(checkAll));
                        System.out.println("전체 체크박스");
                    }
                });

                //테이블 뷰 더블클릭 이벤트 (글번호 Id → ReviewId)
                selectReviewMember.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(mouseEvent.getClickCount() == 2 && selectReviewMember.getSelectionModel().getSelectedItem() != null){
                            System.out.println("더블클릭");

                            long memberId = selectReviewMember.getSelectionModel().getSelectedItem().getMemberId();
                            long reviewId = selectReviewMember.getSelectionModel().getSelectedItem().getId();
                            //long memberId = selectReviewMember.getSelectionModel().getSelectedItem().getId();
                            //long reviewId = selectReviewMember.getSelectionModel().getSelectedItem().getMemberId();
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource(UI.READ.getPath()));
                                Parent root = loader.load();

                                ReviewDetailController reviewDetailController = loader.getController();
                                try {
                                    reviewDetailController.selectDetailRecipeReview(reviewId,memberId);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                Stage stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 메인화면 돌아가기
     * @param event
     */
    public void goMain(ActionEvent event) {
        
        Button clickedButton = (Button) event.getSource();
        String fxmlFile = "";

        if (clickedButton == goMain) {
            fxmlFile = "/fxml/Main.fxml";
        }

        try {
            switchScene(event, fxmlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * 레시피 후기 전체, 선택 삭제하기
     * @param event
     */
    public void deleteRecipeReview(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("레시피 후기 삭제");
        alert.setHeaderText("선택한 후기를 삭제하시겠습니까?");
        alert.setContentText("이 작업은 되돌릴 수 없습니다.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            selectReviewMember.getItems().removeIf(review -> {
                CheckBox cbDelete = review.getCbDelete();
                boolean checked = cbDelete.isSelected();

                if (checked) {
                    long id = review.getId();
                    try {
                        reviewService.deleteRecipeReview(id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return checked;
            });

            initialize(null, null); // 테이블 갱신
        }
    }

    /*
     * 화면이동 (NavigationEvent)
     * @param event
     * @param fxml
     * @throws IOException
     * */
    public void switchScene(ActionEvent event, String fxml) throws IOException {
        //메인(부모) url 으로부터 네비게이션 하기위한 url 경로를 받는 메서드 구현
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /*
     * 화면이동 (지정된 root 인스턴스)
     * @param event
     * @param fxml
     * @param root
     * */
    public void switchScene(MouseEvent event, String fxml, Parent root) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /*
     * fxml 에 지정된 Controller 가져오기
     * @param fxml
     * @return
     * @throws IOException
     *
     * */
    public Object getController (String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(UI.READ.getPath()));
        Parent root = loader.load();
        ReviewDetailController reviewDetailController = loader.getController();
        return loader.getController();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /*
     *레시피후기 등록화면 Navigation
     * @param event
     * */
    public void insertRecipeReview(ActionEvent event) {
        try {
            switchScene(event, UI.INSERT.getPath());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /*
     *레시피후기 수정화면 Navigation
     * @param event
     * */
    public void updateRecipeReview(ActionEvent event) {
        try {
            switchScene(event, UI.UPDATE.getPath());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
