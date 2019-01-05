package activity_Chinese;

import com.jfoenix.controls.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Date;

public class Reset extends Application{
    HBox Hpane0 = new HBox();
    HBox Hpane1 = new HBox();
    HBox Hpane2 = new HBox();
    HBox Hpane3 = new HBox();
    VBox Vpane = new VBox();
    Label title = new Label("修改密码");
    Label username = new Label("      用户名：");
    Label prepsw = new Label("      原密码：");
    Label newpsw = new Label("      新密码：");
    Label repeat = new Label("重复新密码：");
    TextField usernameIN = new TextField();
    PasswordField prepswIN = new PasswordField();
    PasswordField newpswIN = new PasswordField();
    PasswordField repeatIN = new PasswordField();
    JFXButton CONFIRM = new JFXButton("     修改      ");
    JFXButton RETURN = new JFXButton("     返回     ");
    public void start(Stage stage){
        CONFIRM.setStyle("-jfx-button-type: RAISED;" +
                "-fx-background-color: #FF5151;" +
                "-fx-font-size: 20;" +
                "-fx-text-fill: white;");
        RETURN.setStyle("-jfx-button-type: RAISED;" +
                "-fx-background-color: MediumSlateBlue;" +
                "-fx-font-size: 20;" +
                "-fx-text-fill: white;");
        title.setStyle("-fx-font-size:25;");
        Hpane0.getChildren().addAll(username, usernameIN);
        Hpane0.setAlignment(Pos.CENTER);
        Hpane1.getChildren().addAll(prepsw, prepswIN);
        Hpane1.setAlignment(Pos.CENTER);
        Hpane2.getChildren().addAll(newpsw, newpswIN);
        Hpane2.setAlignment(Pos.CENTER);
        Hpane3.getChildren().addAll(repeat, repeatIN);
        Hpane3.setAlignment(Pos.CENTER);
        VBox BOX = new VBox(15);
        BOX.setAlignment(Pos.CENTER);
        BOX.getChildren().addAll(title, Hpane0 ,Hpane1, Hpane2, Hpane3, CONFIRM, RETURN);

        CONFIRM.setOnAction(e->{
            try {
                String strCon = "jdbc:mysql://127.0.0.1:3306/dormitory";
                String name = usernameIN.getText();
                String pre = prepswIN.getText();
                String newp = newpswIN.getText();
                String repe = repeatIN.getText();
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(strCon, "root", "");
                Statement sta = con.createStatement();
                String sql = "SELECT * FROM admininfo WHERE name = '" + name + "' AND password = '" + pre + "'";
                ResultSet rs = sta.executeQuery(sql);
                System.out.println();
                if(rs.next()){
                    if (!newp.equals(repe)){
                        Alert error = new Alert(Alert.AlertType.WARNING,"两次输入新密码不一致！");
                        error.showAndWait();
                    }
                    else {
                        sql = "UPDATE admininfo SET password = '" + newp + "' WHERE name = '" + name + "'";
                        sta.executeUpdate(sql);
                        Alert error = new Alert(Alert.AlertType.INFORMATION,"密码修改成功！");
                        String log_string = name + " change the password from \"" + pre + "\" to \"" + newp + "\" ———————— " + new Date() + "\t";
                        System.out.println(log_string);
                        WriteLog(log_string);
                        error.showAndWait();
                        Index index = new Index();
                        index.start(stage);
                    }
                }
                else {
                    Alert error = new Alert(Alert.AlertType.WARNING,"用户名或密码错误！");
                    error.showAndWait();
                }
            } catch (ClassNotFoundException cnfe) { cnfe.printStackTrace(); }
            catch (SQLException sqle) { sqle.printStackTrace(); } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        RETURN.setOnAction(e->{
            Index index = new Index();
            index.start(stage);
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
