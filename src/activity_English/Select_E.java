package activity_English;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class Select_E extends Application{
    BorderPane Root = new BorderPane();
    private Button CHECK_IN, CHECK_OUT, QUERY;
    private GridPane GRID = new GridPane();
    Scene scene2 = new Scene(Root, 900, 350);
    JFXButton[][] num = new JFXButton[50][50];
    JFXTextField choosen_num = new JFXTextField();
    Statement sta = null;
    Connection con = null;
    ResultSet rs = null;
    public Select_E(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String strCon = "jdbc:mysql://127.0.0.1:3306/dormitory?useUnicode=true&characterEncoding=UTF-8";
            con = DriverManager.getConnection(strCon, "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void start(Stage stage) throws SQLException {
        String number;
        sta = con.createStatement();
//        Connection();
        QUERY = new JFXButton("QUERY");
        CHECK_IN = new JFXButton("Check In");
        CHECK_OUT = new JFXButton("Check Out");
        QUERY.setStyle("-jfx-button-type: RAISED;" +
                " -fx-background-color: MediumSpringGreen;" +
                "-fx-font-size:15;");
        CHECK_IN.setStyle("-jfx-button-type: RAISED;" +
                " -fx-background-color: white;" +
                "-fx-font-size:15;");
        CHECK_OUT.setStyle("-jfx-button-type: RAISED;" +
                " -fx-background-color: white;" +
                "-fx-font-size:15;");
        Label choosen = new Label("Choosen room：");
        choosen.setStyle("-fx-font-size:15;");

        choosen_num.setEditable(false);
        Button temp1 = new JFXButton("   ");
        temp1.setStyle("-jfx-button-type: RAISED;" +
                " -fx-background-color: red;" +
                "-fx-text-fill: white;");
        JFXButton temp2 = new JFXButton("   ");
        temp2.setStyle("-jfx-button-type: RAISED;" +
                " -fx-background-color: CornflowerBlue;" +
                "-fx-text-fill: white;");
        Label ym = new Label("Filled");
        Label wm = new Label("Unfilled");
        HBox hPane1 = new HBox(15);
        GRID.setHgap(10);
        GRID.setVgap(10);
        GRID.setPadding(new Insets(50));

        SetAction();

//        for (int i=0;i<3;i++){
//            for (int j=0;j<10;j++){
//                GRID.add(num[i][j], j, i);
//            }
//        }

        hPane1.setPadding(new Insets(5,5,5,5));
        hPane1.setAlignment(Pos.TOP_CENTER);
        hPane1.getChildren().addAll(temp1, ym, temp2, wm);

        HBox hPane2 = new HBox(15);
        hPane2.setPadding(new Insets(5,5,5,5));
        hPane2.setAlignment(Pos.BOTTOM_CENTER);
        hPane2.getChildren().addAll(choosen, choosen_num, CHECK_IN, CHECK_OUT, QUERY);

        GRID.setAlignment(Pos.TOP_CENTER);

        VBox vPane = new VBox(15);
        vPane.setPadding(new Insets(5,5,5,5));
        vPane.setAlignment(Pos.BOTTOM_CENTER);
        vPane.getChildren().addAll(hPane1, hPane2);

        BorderPane bp = new BorderPane();
        bp.setCenter(GRID);
        bp.setBottom(vPane);
        bp.setStyle("-fx-background-image: url('file:images/backgr.jpg');");
        Root.setCenter(bp);
        stage.setScene(scene2);
        stage.setTitle("Select");
        stage.show();

        CHECK_IN.setOnAction(e->
        {
            if (choosen_num.getText().length()==3){
                CheckIn_E checkInE = new CheckIn_E(choosen_num.getText());
                try {
                    checkInE.start(stage);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            else {
                Alert error = new Alert(Alert.AlertType.WARNING,"Please choose a room above all！");
                error.showAndWait();
            }
        });
        CHECK_OUT.setOnAction(e->
        {
            if (choosen_num.getText().length()==3){
                CheckOut_E checkIn = new CheckOut_E(choosen_num.getText());
                try {
                    checkIn.start(stage);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            else {
                Alert error = new Alert(Alert.AlertType.WARNING,"Please choose a room above all！");
                error.showAndWait();
            }
        });
        QUERY.setOnAction(e->
        {
            Query_E queryE = new Query_E();
            try {
                queryE.start(stage);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

    }

    public void SetAction() throws SQLException {
        for(int i=0;i<3;++i){
            for(int j=0;j<10;++j){
                JFXButton temp = new JFXButton(String.valueOf((i+1)*100+j+1));
                num[i][j] = temp;
                GridPane.setHalignment(num[i][j], HPos.CENTER);
                temp.setStyle("-jfx-button-type: RAISED;" +
                        " -fx-background-color: CornflowerBlue;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size:20;");
                String number = String.valueOf((i+1)*100+j+1);
                rs = sta.executeQuery("SELECT * FROM roominfo WHERE roomId = '" + number + "'");
                while (rs.next()) {
                    if (rs.getString("bed1").length()==7&&
                            rs.getString("bed2").length()==7&&
                            rs.getString("bed3").length()==7&&
                            rs.getString("bed4").length()==7) {
                        num[i][j].setStyle("-jfx-button-type: RAISED;" +
                                " -fx-background-color: red;" +
                                "-fx-text-fill: white;" +
                                "-fx-font-size:20;");
                    }
                }
                GRID.add(num[i][j], j, i);
                num[i][j].setOnMousePressed(e->{
                    Button btn = (Button) e.getSource();
                    for(int x=0; x<3; x++) {
                        for (int y = 0; y < 10; y++) {
                            num[x][y].setStyle("-jfx-button-type: RAISED;" +
                                    " -fx-background-color: CornflowerBlue;" +
                                    "-fx-text-fill: white;" +
                                    "-fx-font-size:20;");
                            try {
                                String number1 = String.valueOf((x+1)*100+y+1);
                                rs = sta.executeQuery("SELECT * FROM roominfo WHERE roomId = '" + number1 + "'");
                                while (rs.next()) {
                                    if (rs.getString("bed1").length()==7&&
                                            rs.getString("bed2").length()==7&&
                                            rs.getString("bed3").length()==7&&
                                            rs.getString("bed4").length()==7) {
                                        num[x][y].setStyle("-jfx-button-type: RAISED;" +
                                                " -fx-background-color: red;" +
                                                "-fx-text-fill: white;" +
                                                "-fx-font-size:20;");
                                    }
                                }
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                    btn.setStyle("-jfx-button-type: RAISED;" +
                            " -fx-background-color: green;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-size:20;");
                    choosen_num.setText(btn.getText());
                });
            }
        }
    }
    public static void main(String args[]){
        Application.launch(args);
    }
}
