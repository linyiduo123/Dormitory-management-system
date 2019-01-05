package entrance;
import activity_Chinese.*;
import activity_English.*;
import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    HBox Hpane = new HBox();
    Separator separator = new Separator();
    Label title = new Label("请选择系统语言");
    Label title_E = new Label("Please choose the language of system.");
    JFXButton CHN = new JFXButton("中文(简体)");
    JFXButton ENG = new JFXButton(" English ");
    Image image = new Image("file:images/logo.png");
    ImageView logo = new ImageView();
    VBox BOX = new VBox(15);

    @Override
    public void start(Stage stage) throws Exception {
        title.setStyle("-fx-font-size: 25");
        title_E.setStyle("-fx-font-size: 15");
        CHN.setStyle("-jfx-button-type: RAISED;" +
                "-fx-background-color: #FF5151;" +
                "-fx-font-size: 30;" +
                "-fx-text-fill: white;");
        ENG.setStyle("-jfx-button-type: RAISED;" +
                "-fx-background-color: MediumSlateBlue;" +
                "-fx-font-size: 30;" +
                "-fx-text-fill: white;");
        logo.setImage(image);
        Hpane.getChildren().addAll(CHN, ENG);
        Hpane.setAlignment(Pos.CENTER);
        Hpane.setSpacing(20);
        BOX.setAlignment(Pos.CENTER);
        BOX.getChildren().addAll(logo, separator, title, title_E, Hpane);
        BOX.setStyle("-fx-background-image: url('file:images/backgr.jpg');");
        Scene myScene = new Scene(BOX, 500, 400);
        stage.setScene(myScene);
        stage.setTitle("寝室管理系统");
        stage.show();

        /**
         * 点击按钮选择进入中文版系统或英文版系统
         */
        CHN.setOnAction(e->{
            Index index = new Index();
            index.start(stage);
        });
        ENG.setOnAction(e->{
            Index_E index_E = new Index_E();
            index_E.start(stage);
        });
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}
