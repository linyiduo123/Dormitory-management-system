package activity_Chinese;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.sql.*;
import java.util.Date;

public class CheckIn extends Application {
    String id;
    Statement sta = null;
    Connection con = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    File f;
    FileWriter writer;
    CheckIn(String id){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String strCon = "jdbc:mysql://127.0.0.1:3306/dormitory?useUnicode=true&characterEncoding=UTF-8";
            con = DriverManager.getConnection(strCon, "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.id = id;
    }
    CheckIn(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String strCon = "jdbc:mysql://127.0.0.1:3306/dormitory";
            con = DriverManager.getConnection(strCon, "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    Label stuIDL = new Label("学号：");
    Label nameL = new Label("姓名：");
    Label bedL = new Label("床号：");
    TextField stuID = new TextField();
    TextField name = new TextField();
    JFXTextField bed = new JFXTextField("点击右侧床位来选择");
    JFXButton RETURN = new JFXButton("返回");
    JFXButton SUBMIT = new JFXButton("提交");

    HBox hp1 = new HBox();
    HBox hp2 = new HBox();
    HBox hp3 = new HBox();
    HBox hp4 = new HBox();
    HBox hp5 = new HBox();
    BorderPane bp = new BorderPane();
    BorderPane Root = new BorderPane();

    HBox Bal = new HBox();
    HBox BED1 = new HBox();
    HBox BED2 = new HBox();
    VBox bedindicator = new VBox();

    Label ym = new Label("已选");
    Label wm = new Label("未选");
    Label balcony = new Label("  阳台  ");
    Label blank1 = new Label("        ");
    Label blank2 = new Label("        ");

    Button temp1 = new JFXButton("   ");
    JFXButton temp2 = new JFXButton("   ");
    JFXButton bed1 = new JFXButton("一\n号\n床");
    JFXButton bed2 = new JFXButton("二\n号\n床");
    JFXButton bed3 = new JFXButton("三\n号\n床");
    JFXButton bed4 = new JFXButton("四\n号\n床");

    public void start(Stage stage) throws SQLException, IOException {
        sta = con.createStatement();
        Label title = new Label(id + "寝室\n" + "入住登记");
        title.setStyle("-fx-font-size:35;");
        System.out.println(id);
        bed.setEditable(false);
        hp1.getChildren().addAll(title);
        hp1.setAlignment(Pos.CENTER);
        hp2.getChildren().addAll(bedL, bed);
        hp2.setAlignment(Pos.CENTER);
        hp3.getChildren().addAll(stuIDL, stuID);
        hp3.setAlignment(Pos.CENTER);
        hp4.getChildren().addAll(nameL, name);
        hp4.setAlignment(Pos.CENTER);
        hp5.getChildren().addAll(SUBMIT, RETURN);
        hp5.setSpacing(20);
        hp5.setAlignment(Pos.CENTER);

        bed1.setOnAction(e->bed.setText("1"));
        bed2.setOnAction(e->bed.setText("2"));
        bed3.setOnAction(e->bed.setText("3"));
        bed4.setOnAction(e->bed.setText("4"));

        setStyle();
        checkBed();

        HBox indicator = new HBox(15);
        indicator.setAlignment(Pos.TOP_CENTER);
        indicator.getChildren().addAll(temp1, ym, temp2, wm);

        Bal.getChildren().addAll(balcony);
        Bal.setAlignment(Pos.CENTER);
        BED1.getChildren().addAll(bed1, blank1, bed2);
        BED2.getChildren().addAll(bed3, blank2, bed4);
        bedindicator.getChildren().addAll(Bal, BED1, BED2, indicator);
        bedindicator.setAlignment(Pos.CENTER);
        bedindicator.setSpacing(20);

        RETURN.setOnAction(e->{
            Select select = new Select();
            try {
                select.start(stage);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        SUBMIT.setOnAction(e->{

            String ID = stuID.getText();
            String NAME = name.getText();
            String BED = "bed" + bed.getText();
            System.out.println(BED);
            try{

                String sql = "SELECT * FROM studentinfo WHERE stuId = '" + ID + "'";
                String sql2 = "SELECT * FROM roominfo WHERE roomId = '" + id + "'";
                rs2 = sta.executeQuery(sql2);
                /**
                 * 各条件串行判断
                 *
                 * 判断顺序： 床位是否有人 > 学号是否存在 > 姓名学号是否匹配 > 是否已办理过入住
                 *
                 */
                if (rs2.next()){
                    if (rs2.getString(BED).length()==7){
                        Alert error = new Alert(Alert.AlertType.WARNING,"该床位已住人，请重新选择！");
                        error.showAndWait();
                        clearInput();
                    }
                    else {
                        rs = sta.executeQuery(sql);
                        if (rs.next()) {
                            if (!rs.getString("stuName").equals(NAME)){
                                Alert error = new Alert(Alert.AlertType.WARNING,"姓名错误！");
                                error.showAndWait();
                                clearInput();
                            }
                            else {
                                if (rs.getString("isCheckIn").length()!=3){
                                    sta.executeUpdate("UPDATE studentinfo SET isCheckIn = " + id + " WHERE stuId = '" + ID + "'");
                                    sta.executeUpdate("UPDATE roominfo SET " + BED + " = '" + ID + "' WHERE roomId = '" + id + "'");
                                    String log_string = ID + " check in the " + BED + " of room " + id + " ———————— " + new Date() + "\t";
                                    System.out.println(log_string);
                                    WriteLog(log_string);
                                    Alert error = new Alert(Alert.AlertType.INFORMATION,"办理成功！");
                                    error.showAndWait();
                                    checkBed();
                                    clearInput();
                                }
                                else {
                                    Alert error = new Alert(Alert.AlertType.WARNING,"您已办理过入住！\n若要入住其他寝室，需先办理退房！");
                                    error.showAndWait();
                                    clearInput();
                                }
                            }
                        }
                        else {
                            Alert error = new Alert(Alert.AlertType.WARNING,"学号不存在！");
                            error.showAndWait();
                            clearInput();
                        }
                    }
                }
            }catch (SQLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            ;
        });

        VBox BOX = new VBox(15);
        BOX.setAlignment(Pos.CENTER);
        BOX.getChildren().addAll(hp1, hp2, hp3, hp4, hp5);
        HBox realBOX = new HBox();
        realBOX.getChildren().addAll(BOX, bedindicator);
        realBOX.setStyle("-fx-background-image: url('file:images/backgr.jpg');");
        realBOX.setAlignment(Pos.CENTER);
        realBOX.setSpacing(75);
        bp.setCenter(realBOX);
        Root.setCenter(bp);

        Scene scene = new Scene(Root, 800, 450);
        stage.setScene(scene);
        stage.setTitle("办理入住");
        stage.show();


    }/**
     * 更新床位入住信息
     */
    public void checkBed() throws SQLException{
        rs = sta.executeQuery("SELECT * FROM roominfo WHERE roomId = '" + id + "'");
        while (rs.next()) {
            if (rs.getString("bed1").length()==7){
                bed1.setStyle(" -fx-background-color: red;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size:20;");
            }
            if (rs.getString("bed2").length()==7){
                bed2.setStyle(" -fx-background-color: red;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size:20;");
            }
            if (rs.getString("bed3").length()==7){
                bed3.setStyle(" -fx-background-color: red;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size:20;");
            }
            if (rs.getString("bed4").length()==7){
                bed4.setStyle(" -fx-background-color: red;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size:20;");
            }
        }
    }

    /**
     * 设置组件样式
     */
    public void setStyle() throws SQLException{
        RETURN.setStyle(" -fx-background-color: MediumSlateBlue;" +
                "-fx-text-fill: white;" +
                "-fx-font-size:20;");
        SUBMIT.setStyle(" -fx-background-color: #FF5151;" +
                "-fx-text-fill: white;" +
                "-fx-font-size:20;");
        balcony.setStyle("-fx-background-color: CornflowerBlue;" +
                " -fx-font-size:23;" +
                " -fx-text-fill:white;" +
                " -fx-padding:15");
        bed1.setStyle(" -fx-background-color: CornflowerBlue;" +
                "-fx-text-fill: white;" +
                "-fx-font-size:20;");
        bed2.setStyle(" -fx-background-color: CornflowerBlue;" +
                "-fx-text-fill: white;" +
                "-fx-font-size:20;");
        bed3.setStyle(" -fx-background-color: CornflowerBlue;" +
                "-fx-text-fill: white;" +
                "-fx-font-size:20;");
        bed4.setStyle(" -fx-background-color: CornflowerBlue;" +
                "-fx-text-fill: white;" +
                "-fx-font-size:20;");
        temp1.setStyle("-jfx-button-type: RAISED;" +
                " -fx-background-color: red;" +
                "-fx-text-fill: white;");
        temp2.setStyle("-jfx-button-type: RAISED;" +
                " -fx-background-color: CornflowerBlue;" +
                "-fx-text-fill: white;");
    }

    /**
     * 清空表单
     */
    public void clearInput(){
        stuID.setText("");
        name.setText("");
        bed.setText("");
    }

    /**
     * 将日志保存到本地
     */
    public void WriteLog(String str) throws IOException{
        File file=new File("log.txt");
        FileWriter writer = new FileWriter(file, true);
        writer.write(str + "\n");
        writer.close();
    }

    public static void main(String args[]){
        Application.launch(args);
    }
}
