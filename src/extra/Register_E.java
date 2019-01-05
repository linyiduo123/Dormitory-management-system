package extra;

import activity_Chinese.Index;
import activity_English.Index_E;
import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

public class Register_E extends Application {
    public String captcha = "";
    public String userMail = "837756566@qq.com";
    Statement sta = null;
    Connection con = null;
    ResultSet rs = null;

    public Register_E(){
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

    HBox Hpane0 = new HBox();
    HBox Hpane1 = new HBox();
    HBox Hpane2 = new HBox();
    HBox Hpane3 = new HBox();
    VBox Vpane = new VBox();
    Label title = new Label("Admin Register");
    Label username = new Label("  Username：");
    Label psw =   new Label("    Password：");
    Label mailadd =   new Label("                                E-Mail：");
    Label captcha_confirm =   new Label("Captcha：");

    TextField usernameIN = new TextField();
    PasswordField pswIN = new PasswordField();
    TextField mainaddIN = new TextField();
    TextField captcha_confirmIN = new TextField();
    JFXButton SEND = new JFXButton("Send Captcha");
    JFXButton CONFIRM = new JFXButton("     Register      ");
    JFXButton RETURN = new JFXButton("     Return     ");
    public void start(Stage stage) throws SQLException{
        CONFIRM.setStyle("-jfx-button-type: RAISED;" +
                "-fx-background-color: #FF5151;" +
                "-fx-font-size: 20;" +
                "-fx-text-fill: white;");
        RETURN.setStyle("-jfx-button-type: RAISED;" +
                "-fx-background-color: MediumSlateBlue;" +
                "-fx-font-size: 20;" +
                "-fx-text-fill: white;");
        SEND.setStyle("-jfx-button-type: RAISED;");
        title.setStyle("-fx-font-size:25;");
        Hpane0.getChildren().addAll(username, usernameIN);
        Hpane0.setAlignment(Pos.CENTER);
        Hpane1.getChildren().addAll(psw, pswIN);
        Hpane1.setAlignment(Pos.CENTER);
        Hpane2.getChildren().addAll(mailadd, mainaddIN, SEND);
        Hpane2.setAlignment(Pos.CENTER);
        Hpane2.setSpacing(5);
        Hpane3.getChildren().addAll(captcha_confirm, captcha_confirmIN);
        Hpane3.setAlignment(Pos.CENTER);
        VBox BOX = new VBox(15);
        BOX.setAlignment(Pos.CENTER);
        BOX.getChildren().addAll(title, Hpane0 ,Hpane1, Hpane2, Hpane3, CONFIRM, RETURN);
        SEND.setOnAction(e->{
            captcha = "";
            int _captcha = (int)((Math.random()*9+1)*1000);
            captcha += _captcha;

            try {
                SendMessage();
                Alert error = new Alert(Alert.AlertType.INFORMATION,"Send success!Check if you receive.(May be in SPAM folder)");
                error.showAndWait();
            } catch (MessagingException e1) {
                e1.printStackTrace();
            }
        });

        CONFIRM.setOnAction(e->{
            if (captcha_confirmIN.getText().equals(captcha)){
                try {
                    String sql = "INSERT INTO admininfo (name, password) values(?,?)";
                    PreparedStatement pstmt = null;
                    pstmt = (PreparedStatement) con.prepareStatement(sql);
                    pstmt.setString(1, usernameIN.getText());
                    pstmt.setString(2, pswIN.getText());
                    int i = pstmt.executeUpdate();
                    pstmt.close();
                    con.close();
                    Alert error = new Alert(Alert.AlertType.INFORMATION,"Register success！");
                    error.showAndWait();
                    String log_string = "A new account \"" + usernameIN.getText() + "\" is registered" + " ———————— " + new Date() + "\t";
                    System.out.println(log_string);
                    WriteLog(log_string);
                    Index index = new Index();
                    index.start(stage);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            else {
                Alert error = new Alert(Alert.AlertType.WARNING,"Captcha matching error！");
                error.showAndWait();
            }
        });

        RETURN.setOnAction(e->{
            Index_E index_e = new Index_E();
            index_e.start(stage);
        });
        BOX.setStyle("-fx-background-image: url('file:images/backgr.jpg');");
        Scene myScene = new Scene(BOX, 500, 400);
        stage.setScene(myScene);
        stage.setTitle("Register");
        stage.show();
    }

    public static void main(String[] args)  {
        Application.launch(args);
    }
    public void SendMessage() throws MessagingException{

        Properties prop=new Properties();
        prop.put("mail.host","smtp.163.com" );
        prop.put("mail.transport.protocol", "smtp");
        prop.put("mail.smtp.auth", true);
        Session session = Session.getInstance(prop);
        session.setDebug(true);

        Transport ts=session.getTransport();
        ts.connect("hznulin@163.com", "linyiduo123123");

        Message msg = createSimpleMail(session);

        ts.sendMessage(msg, msg.getAllRecipients());
    }
    public void WriteLog(String str) throws IOException {
        File file=new File("log.txt");
        FileWriter writer = new FileWriter(file, true);
        writer.write(str + "\n");
        writer.close();
    }
    public MimeMessage createSimpleMail(Session session) throws AddressException, MessagingException{
        MimeMessage mm=new MimeMessage(session);
        mm.setFrom(new InternetAddress("hznulin@163.com"));
        mm.setRecipient(Message.RecipientType.TO, new InternetAddress(mainaddIN.getText()));
        mm.setRecipient(Message.RecipientType.CC, new InternetAddress(mainaddIN.getText()));

        mm.setSubject("Dormitory system register captcha");
        mm.setContent("Captcha: " + captcha, "text/html;charset=utf-8");

        return mm;
    }
}
