package activity_Chinese;

import com.jfoenix.controls.*;
import extra.Register;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Date;

public class Index extends Application{
    HBox Hpane1 = new HBox();
    HBox Hpane2 = new HBox();
    HBox Hpane3 = new HBox();
    VBox Vpane = new VBox();
    Separator separator = new Separator();
    Label title = new Label("寝室管理系统-管理员端");
    Label name = new Label("账号：");
    Label password = new Label("密码：");
    TextField nameIn = new TextField();
    PasswordField passwordIn = new PasswordField();
    JFXButton login = new JFXButton("    登录    ");
    JFXButton reset = new JFXButton("修改密码");
    JFXButton regis = new JFXButton("管理员注册");
    Image image = new Image("file:images/logo.png");
    ImageView logo = new ImageView();
    public void start(Stage stage){
        login.setStyle("-jfx-button-type: RAISED;" +
                "-fx-background-color: #FF5151;" +
                "-fx-font-size: 20;" +
                "-fx-text-fill: white;");
        reset.setStyle("-jfx-button-type: RAISED;" +
                "-fx-background-color: MediumSlateBlue;" +
                "-fx-font-size: 20;" +
                "-fx-text-fill: white;");
        regis.setStyle("-jfx-button-type: RAISED;" +
                "-fx-background-color: MediumTurquoise;" +
                "-fx-font-size: 20;" +
                "-fx-text-fill: white;");
        logo.setImage(image);
        title.setStyle("-fx-font-size:25;");
        Hpane1.getChildren().addAll(name, nameIn);
        Hpane1.setAlignment(Pos.CENTER);
        Hpane2.getChildren().addAll(password, passwordIn);
        Hpane2.setAlignment(Pos.CENTER);
        Hpane3.getChildren().addAll(reset, regis);
        Hpane3.setAlignment(Pos.CENTER);
        Hpane3.setSpacing(20);
        VBox BOX = new VBox(15);
        BOX.setAlignment(Pos.CENTER);
        BOX.getChildren().addAll(logo, title, separator, Hpane1, Hpane2, login, Hpane3);

        login.setOnAction(e->{
            try {
                String strCon = "jdbc:mysql://127.0.0.1:3306/dormitory";
                String username = nameIn.getText();
                String password = passwordIn.getText();
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(strCon, "root", "");
                Statement sta = con.createStatement();
                String sql = "SELECT * FROM admininfo WHERE name = '" + username + "' AND password = '" + password + "'";
                ResultSet rs = sta.executeQuery(sql);
                if (rs.next()) {
                    String log_string = username + " log in the system " + " ———————— " + new Date() + "\t";
                    System.out.println(log_string);
                    WriteLog(log_string);
                    Select select = new Select();
                    select.start(stage);
                }
                con.close();
                sta.close();
                rs.close();
            } catch (ClassNotFoundException cnfe) { cnfe.printStackTrace(); }
            catch (SQLException sqle) { sqle.printStackTrace(); } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        reset.setOnAction(e->{
            Reset reset = new Reset();
            reset.start(stage);
        });
        regis.setOnAction(e->{
            Register Regis = new Register();
            try {
                Regis.start(stage);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        BOX.setStyle("-fx-background-image: url('file:images/backgr.jpg');");
        Scene myScene = new Scene(BOX, 500, 400);
        stage.setScene(myScene);
        stage.setTitle("寝室管理系统");
        stage.show();
    }
    public void WriteLog(String str) throws IOException {
        File file=new File("log.txt");
        FileWriter writer = new FileWriter(file, true);
        writer.write(str + "\n");
        writer.close();
    }
    public static void main(String args[]){
        Application.launch(args);
    }
}
