package activity_English;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import queryClass.*;

import java.sql.*;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Query_E extends Application{

    Statement sta = null;
    Connection con = null;
    ResultSet rs = null;

    Query_E(){
        try{
            String strCon = "jdbc:mysql://127.0.0.1:3306/dormitory?useUnicode=true&characterEncoding=UTF-8";
            con = DriverManager.getConnection(strCon, "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    final ToggleGroup group = new ToggleGroup();
    HBox Hpane0 = new HBox();
    HBox Hpane1 = new HBox();
    HBox Hpane2 = new HBox();
    HBox Hpane3 = new HBox();
    HBox Hpane4 = new HBox();
    HBox pane = new HBox();
    VBox Vpane = new VBox();
    Label grade = new Label("Query by grade");
    Label Class = new Label("Query by class");
    Label id = new Label("Query by ID");
    Label name = new Label("Query by name");
    Label room = new Label("Query by room");
    Label label = new Label("input specific query conditions here");
    JFXToggleButton BYgrade = new JFXToggleButton();
    JFXToggleButton BYclass = new JFXToggleButton();
    JFXToggleButton BYid = new JFXToggleButton();
    JFXToggleButton BYname = new JFXToggleButton();
    JFXToggleButton BYroom = new JFXToggleButton();

    private final ObservableList obsList = FXCollections.observableArrayList();
    private final TableView table = new TableView();

    JFXTextField query = new JFXTextField();
    JFXButton QUERY = new JFXButton("QUERY");
    JFXButton RETURN = new JFXButton("RETURN");

    public void start(Stage stage) throws SQLException{
        sta = con.createStatement();
        label.setStyle("-fx-font-size:15;");
        QUERY.setStyle("-jfx-button-type: RAISED;" +
                " -fx-background-color: #FF5151;" +
                "-fx-text-fill: white;" +
                "-fx-font-size:20;");
        RETURN.setStyle("-jfx-button-type: RAISED;" +
                " -fx-background-color: MediumSlateBlue;" +
                "-fx-text-fill: white;" +
                "-fx-font-size:20;");
        BYgrade.setToggleGroup(group);
        BYclass.setToggleGroup(group);
        BYid.setToggleGroup(group);
        BYname.setToggleGroup(group);
        BYroom.setToggleGroup(group);
        Hpane0.getChildren().addAll(grade, BYgrade);
        Hpane0.setAlignment(Pos.CENTER);
        Hpane1.getChildren().addAll(Class, BYclass);
        Hpane1.setAlignment(Pos.CENTER);
        Hpane2.getChildren().addAll(id, BYid);
        Hpane2.setAlignment(Pos.CENTER);
        Hpane3.getChildren().addAll(name, BYname);
        Hpane3.setAlignment(Pos.CENTER);
        Hpane4.getChildren().addAll(room, BYroom);
        Hpane4.setAlignment(Pos.CENTER);
        VBox BOX = new VBox(10);
        BOX.setAlignment(Pos.CENTER);
        BOX.getChildren().addAll(Hpane0 ,Hpane1, Hpane2, Hpane3, Hpane4, label, query, QUERY, RETURN);

        pane.setSpacing(15);
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(BOX, table);
        pane.setStyle("-fx-background-image: url('file:images/backgr.jpg');");
        pane.setPadding(new Insets(10, 0, 10, 10));
        Scene myScene = new Scene(pane, 700, 600);
        stage.setScene(myScene);
        stage.setTitle("Query");
        stage.show();

        RETURN.setOnAction(e->{
            Select_E sselect = new Select_E();
            try {
                sselect.start(stage);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        QUERY.setOnAction(e->{
            if (BYgrade.isSelected()){
                try {
                    QueryByGrade();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            else if (BYclass.isSelected()) {
                try {
                    QueryByClass();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            else if (BYid.isSelected()) {
                try {
                    QueryById();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            else if (BYname.isSelected()) {
                try {
                    QueryByName();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            else if (BYroom.isSelected()) {
                try {
                    QueryByRoom();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
    public void QueryByClass() throws SQLException{
        obsList.clear();
        table.getColumns().setAll();
        table.setItems(obsList);
        TableColumn stuName = new TableColumn("NAME");
        TableColumn stuId = new TableColumn("ID");
        TableColumn stuRoom = new TableColumn("ROOM");
        stuName.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        stuId.setCellValueFactory(
                new PropertyValueFactory<>("id"));
        stuRoom.setCellValueFactory(
                new PropertyValueFactory<>("room"));
        table.getColumns().addAll(stuName, stuId, stuRoom);
        String sql;
        if (query.getText().length()!=0){
            sql = "SELECT * FROM studentInfo WHERE class = '" + query.getText() + "'";
        }
        else {
            sql = "SELECT * FROM studentInfo";
        }
        rs = sta.executeQuery(sql);
        while (rs.next()) {
            String tempId = rs.getString("stuId");
            String tempName = rs.getString("stuName");
            String tempRoom = rs.getString("isCheckIn");
            obsList.add(new ByClass(tempName, tempId, tempRoom));
        }
    }
    public void QueryById() throws SQLException{
        obsList.clear();
        table.getColumns().setAll();
        table.setItems(obsList);
        TableColumn stuName = new TableColumn("NAME");
        TableColumn colleget = new TableColumn("INSTITUTE");
        TableColumn stuClass = new TableColumn("CLASS");
        TableColumn stuRoom = new TableColumn("ROOM");
        stuName.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        colleget.setCellValueFactory(
                new PropertyValueFactory<>("institude"));
        stuClass.setCellValueFactory(
                new PropertyValueFactory<>("_class"));
        stuRoom.setCellValueFactory(
                new PropertyValueFactory<>("room"));
        table.getColumns().addAll(stuName, colleget, stuClass, stuRoom);
        String sql;
        if (query.getText().length()!=0){
            sql = "SELECT * FROM studentInfo WHERE stuId = '" + query.getText() + "'";
        }
        else {
            sql = "SELECT * FROM studentInfo";
        }
        rs = sta.executeQuery(sql);
        while (rs.next()) {
            String tempName = rs.getString("stuName");
            String tempInst = rs.getString("colleget");
            String tempClass = rs.getString("class");
            String tempRoom = rs.getString("isCheckIn");
            obsList.add(new ById(tempName, tempInst, tempClass, tempRoom));
        }
    }
    public void QueryByGrade() throws SQLException{
        obsList.clear();
        table.getColumns().setAll();
        table.setItems(obsList);
        TableColumn stuName = new TableColumn("NAME");
        TableColumn stuId = new TableColumn("ID");
        TableColumn colleget = new TableColumn("INSTITUTE");
        TableColumn stuClass = new TableColumn("CLASS");
        TableColumn stuRoom = new TableColumn("ROOM");
        stuName.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        stuId.setCellValueFactory(
                new PropertyValueFactory<>("id"));
        colleget.setCellValueFactory(
                new PropertyValueFactory<>("institude"));
        stuClass.setCellValueFactory(
                new PropertyValueFactory<>("_class"));
        stuRoom.setCellValueFactory(
                new PropertyValueFactory<>("room"));
        table.getColumns().addAll(stuName, stuId, colleget, stuClass, stuRoom);
        String sql;
        if (query.getText().length()!=0){
            sql = "SELECT * FROM studentInfo WHERE department = '" + query.getText() + "'";
        }
        else {
            sql = "SELECT * FROM studentInfo";
        }
        rs = sta.executeQuery(sql);
        while (rs.next()) {
            String tempName = rs.getString("stuName");
            String tempId = rs.getString("stuId");
            String tempInst = rs.getString("colleget");
            String tempClass = rs.getString("class");
            String tempRoom = rs.getString("isCheckIn");
            obsList.add(new ByGrade(tempName, tempId, tempInst, tempClass,tempRoom));
        }
    }
    public void QueryByName() throws SQLException{
        obsList.clear();
        table.getColumns().setAll();
        table.setItems(obsList);
        TableColumn stuId = new TableColumn("ID");
        TableColumn colleget = new TableColumn("INSTITUTE");
        TableColumn stuClass = new TableColumn("CLASS");
        TableColumn stuRoom = new TableColumn("ROOM");
        stuId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colleget.setCellValueFactory(new PropertyValueFactory<>("institude"));
        stuClass.setCellValueFactory(new PropertyValueFactory<>("_class"));
        stuRoom.setCellValueFactory(new PropertyValueFactory<>("room"));
        table.getColumns().addAll(stuId, colleget, stuClass, stuRoom);
        String sql;
        if (query.getText().length()!=0){
            sql = "SELECT * FROM studentInfo WHERE stuName = '" + query.getText() + "'";
        }
        else {
            sql = "SELECT * FROM studentInfo";
        }
        rs = sta.executeQuery(sql);
        while (rs.next()) {
            String tempId = rs.getString("stuId");
            String tempInst = rs.getString("colleget");
            String tempClass = rs.getString("class");
            String tempRoom = rs.getString("isCheckIn");
            obsList.add(new ByName(tempId, tempInst, tempClass, tempRoom));
        }
    }
    public void QueryByRoom() throws SQLException{
        obsList.clear();
        table.getColumns().setAll();
        table.setItems(obsList);
        TableColumn stuName = new TableColumn("NAME");
        TableColumn stuId = new TableColumn("ID");
        TableColumn institude = new TableColumn("INSTITUTE");
        TableColumn stuClass = new TableColumn("CLASS");
        TableColumn stuBed = new TableColumn("BED");
        stuName.setCellValueFactory(new PropertyValueFactory<>("name"));
        stuId.setCellValueFactory(new PropertyValueFactory<>("id"));
        institude.setCellValueFactory(new PropertyValueFactory<>("institude"));
        stuClass.setCellValueFactory(new PropertyValueFactory<>("_class"));
        stuBed.setCellValueFactory(new PropertyValueFactory<>("bed"));
        table.getColumns().addAll(stuName, stuId, institude, stuClass, stuBed);
        String sql;
        sql = "SELECT * FROM roomInfo WHERE roomId = '" + query.getText() + "'";
        rs = sta.executeQuery(sql);
        Queue<String> q = new ArrayBlockingQueue<>(4);
        while (rs.next()) {
            String bed1 = rs.getString("bed1");
            q.offer(bed1);
            String bed2 = rs.getString("bed2");
            q.offer(bed2);
            String bed3 = rs.getString("bed3");
            q.offer(bed3);
            String bed4 = rs.getString("bed4");
            q.offer(bed4);
        }
        String tempName = "";
        String tempId = "";
        String tempInst = "";
        String tempClass = "";
        ResultSet rs2 = null;
        int cnt = 1;
        while (q.peek() != null){
            if (q.peek().length()==7){
                String bed = "";
                bed += cnt;
                sql = "SELECT * FROM studentinfo WHERE stuId = '" + q.peek() + "'";
                rs2 = sta.executeQuery(sql);
                while (rs2.next()){
                    tempName = rs2.getString("stuName");
                    tempId = rs2.getString("stuId");
                    tempInst = rs2.getString("colleget");
                    tempClass = rs2.getString("class");
                }
                obsList.add(new ByRoom(tempName, tempId, tempInst, tempClass, bed));
            }
            q.poll();
            cnt += 1;
        }

    }

    public static void main(String args[]){
        Application.launch(args);
    }
}
