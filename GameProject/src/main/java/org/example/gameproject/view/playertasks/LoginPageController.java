
package org.example.gameproject.view.playertasks;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.cell.MediaImageCell;
import org.example.gameproject.PaneController;
import org.example.gameproject.controler.Regex;
import org.example.gameproject.controler.databaseManage.PlayerDatabaseManage;
import org.example.gameproject.controler.playerOperation.CreateAccount;
import org.example.gameproject.model.player.Player;
import org.example.gameproject.view.maps.CommonMethods;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    @FXML
    private ImageView mainMap;

    @FXML
    private VBox loginPane;

    @FXML
    private TextField loginUsername;

    @FXML
    private TextField loginPassword;

    @FXML
    private Label forgotPasswordLabel;

    @FXML
    private Label errorLoginLabel;

    @FXML
    private Button loginButton;

    @FXML
    private VBox signupPane;

    @FXML
    private TextField signupUsername;

    @FXML
    private TextField sinupPassword;

    @FXML
    private TextField signupEmail;

    @FXML
    private Button signin;

    @FXML
    private Label signupErrorLabel;

    @FXML
    private VBox changePasswordPane;

    @FXML
    private TextField username;

    @FXML
    private TextField newPassword;

    @FXML
    private Button applyChanges;

    @FXML
    private Label changeErrorLabel;

    @FXML
    private ImageView firstMap;

    @FXML
    private ImageView secondMAp;

    @FXML
    private ImageView thirdMap;

    @FXML
    private ImageView shop;

    @FXML
    private ImageView setting;

    @FXML
    private Pane settingPane;

    @FXML
    private ImageView closeSetting;

    @FXML
    private TextField usernameSetting;

    @FXML
    private TextField passwordSetting;

    @FXML
    private Button applySetting;

    @FXML
    private Slider volumeSlider;

    @FXML
    private ImageView mute;

    @FXML
    private ImageView playPauseImage;
    @FXML
    private ImageView map4;

    @FXML
    private ImageView map5;

    @FXML
    private Line map12Line;

    @FXML
    private Line map23Line;

    @FXML
    private Line map34Line;

    @FXML
    private Line map45Line;

    //=========================================
    File file = new File("C:\\Users\\User\\IdeaProjects\\FinalProject\\finalproject-game-sabwinam\\GameProject\\src\\main\\resources\\org\\musics");
    File[] list;
    public ArrayList<File> musics;
    Media media;
    MediaPlayer mediaPlayer;
    //=========================================
    @FXML
    void applyAndCloseSetting(ActionEvent event) {
        firstMap.setVisible(true);
        secondMAp.setVisible(true);
        thirdMap.setVisible(true);
        shop.setVisible(true);
        setting.setVisible(true);
        map5.setVisible(true);
        map4.setVisible(true);
        for (int i = 0; i < player.getLevel() - 1; i++) {
            lines.get(i).setVisible(true);
        }

        String newUsername = usernameSetting.getText();
        String newPassword = passwordSetting.getText();
        if (!Objects.equals(newUsername, "")) {
            PlayerDatabaseManage.getPlayerDatabaseManage().updateUsername(newUsername, player.getId());
        }
        if (!Objects.equals(newPassword, "")) {
            PlayerDatabaseManage.getPlayerDatabaseManage().updatePassword(newPassword, player.getId());
        }
        close();
    }

    @FXML
    void muteMusic(MouseEvent event) {

        mediaPlayer.setMute(false);
        mute.setVisible(false);
        playPauseImage.setVisible(true);
    }

    @FXML
    void unMuteMusic(MouseEvent event) {
        mediaPlayer.setMute(true);

        mute.setVisible(true);
        playPauseImage.setVisible(false);
    }


    @FXML
    void closeSetting(MouseEvent event) {
        firstMap.setVisible(true);
        secondMAp.setVisible(true);
        thirdMap.setVisible(true);
        shop.setVisible(true);
        setting.setVisible(true);
        map5.setVisible(true);
        map4.setVisible(true);
        for (int i = 0; i < player.getLevel() - 1; i++) {
            lines.get(i).setVisible(true);
        }
        close();
    }

    String userName;
    String password;
    String email;
    ArrayList<Line> lines = new ArrayList<>();
    public Player player;


    @FXML
    void goForgotPasswordPane(MouseEvent event) {
        //فید کردن و برگرداندن پین لاگین ======================================================
        FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(1), loginPane);
        TranslateTransition transition1 = new TranslateTransition(Duration.seconds(1), loginPane);

        fadeTransition1.setFromValue(1.0);
        fadeTransition1.setToValue(0.2);
        transition1.setByY(600);
        transition1.play();
        fadeTransition1.play();

        //ویزیبل کردن و اجرای تنزیشن روی پین تغییر پسورد======================================================
        changePasswordPane.setVisible(true);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), changePasswordPane);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), changePasswordPane);

        fadeTransition.setFromValue(0.2);
        fadeTransition.setToValue(1.0);

        transition.setByY(-600);
        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(12);
        transition.play();
        fadeTransition.play();
        mainMap.setEffect(blur);
    }

    @FXML
    void goHome(ActionEvent event) {
        if (password == null || userName == null) {
            signupErrorLabel.setText("some fields are empty!");
        } else {
            if ((((Button) event.getSource()).getId()).equals(signin.getId())) {
                try {
                    if (Regex.checkEmail(email) && Regex.checkPassword(password)) {
                        CreateAccount.getCreateAccount().signup(userName, password, email);
                        player = CreateAccount.getCreateAccount().login(password, userName);
                        FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(1), signupPane);
                        TranslateTransition transition1 = new TranslateTransition(Duration.seconds(1), signupPane);

                        fadeTransition1.setFromValue(1.0);
                        fadeTransition1.setToValue(0.2);
                        transition1.setByY(600);
                        mainMap.setEffect(null);
                        transition1.play();
                        fadeTransition1.play();
                        firstMap.setVisible(true);
                        secondMAp.setVisible(true);
                        thirdMap.setVisible(true);
                        shop.setVisible(true);
                        setting.setVisible(true);
                        map5.setVisible(true);
                        map4.setVisible(true);
                        for (int i = 0; i < player.getLevel() - 1; i++) {
                            lines.get(i).setVisible(true);
                        }
                        email = null;
                        password = null;
                        userName = null;
                        signupErrorLabel.setText("");
                    }
                } catch (Exception e) {
                    signupErrorLabel.setText(e.getMessage());
                    throw new RuntimeException(e);
                }

            } else {
                try {
                    if (Regex.checkPassword(password)) {
                        player = CreateAccount.getCreateAccount().login(password, userName);
                        System.out.println(player.getId());
                        FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), loginPane);
                        TranslateTransition transition1 = new TranslateTransition(Duration.seconds(0.5), loginPane);

                        fadeTransition1.setFromValue(1.0);
                        fadeTransition1.setToValue(0.2);
                        transition1.setByY(600);
                        mainMap.setEffect(null);
                        transition1.play();
                        fadeTransition1.play();
                        firstMap.setVisible(true);
                        secondMAp.setVisible(true);
                        thirdMap.setVisible(true);
                        shop.setVisible(true);
                        map5.setVisible(true);
                        map4.setVisible(true);
                        setting.setVisible(true);
                        for (int i = 0; i < player.getLevel() - 1; i++) {
                            lines.get(i).setVisible(true);
                        }
                        email = null;
                        password = null;
                        userName = null;
                    }
                } catch (Exception e) {
                    signupErrorLabel.setText(e.getMessage());
                    throw new RuntimeException(e);
                }
            }

        }
    }

    @FXML
    void applyChange(ActionEvent event) {
        try {
            if (Regex.checkPassword(password)) {
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), changePasswordPane);
                TranslateTransition transition = new TranslateTransition(Duration.seconds(1), changePasswordPane);

                fadeTransition.setFromValue(1);
                fadeTransition.setToValue(0.2);
                transition.setByY(600);
                transition.play();
                fadeTransition.play();
                mainMap.setEffect(null);
                password = null;
            }
        } catch (Exception e) {
            changeErrorLabel.setText(e.getMessage());
        }
    }

    @FXML
    void goSigninPane(MouseEvent event) {
        //فید کردن و برگرداندن پین لاگین ======================================================
        FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(1), loginPane);
        TranslateTransition transition1 = new TranslateTransition(Duration.seconds(1), loginPane);

        fadeTransition1.setFromValue(1.0);
        fadeTransition1.setToValue(0.2);
        transition1.setByY(600);
        transition1.play();
        fadeTransition1.play();

        //ویزیبل کردن و اجرای تنزیشن روی پین ساین آپ======================================================
        signupPane.setVisible(true);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), signupPane);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), signupPane);

        fadeTransition.setFromValue(0.2);
        fadeTransition.setToValue(1.0);

        transition.setByY(-600);
        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(12);
        transition.play();
        fadeTransition.play();
        mainMap.setEffect(blur);
    }

    private int currentIndex;


    private void playTrack(int index) {
        if (index >= 0 && index < musics.size()) {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.dispose();
            }

            media = new Media(musics.get(index).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();

            mediaPlayer.setOnEndOfMedia(() -> {
                currentIndex = (currentIndex + 1) % musics.size();
                playTrack(currentIndex);
            });
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lines.add(map12Line);
        lines.add(map23Line);
        lines.add(map34Line);
        lines.add(map45Line);

        //============================================
        musics = new ArrayList<>();
        list = file.listFiles();
        if (list != null) {
            for (File f : list) {
                musics.add(f);
                System.out.println(f);
            }
        }
        media = new Media(musics.getFirst().toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        currentIndex = 0;
        playTrack(currentIndex);
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
            }
        });
        //======================================================
        firstMap.setVisible(false);
        secondMAp.setVisible(false);
        thirdMap.setVisible(false);
        shop.setVisible(false);
        setting.setVisible(false);
        map4.setVisible(false);
        map5.setVisible(false);
        for (Line line : lines) {
            line.setVisible(false);
        }

        //====================================================== set Prompt text for all fields
        username.setPromptText("username");
        loginUsername.setPromptText("username");
        loginPassword.setPromptText("Password");
        newPassword.setPromptText("password");
        signupUsername.setPromptText("username");
        sinupPassword.setPromptText("Password");
        signupEmail.setPromptText("E-Mail");

        //====================================================== transition for login pane
        loginPane.setVisible(true);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), loginPane);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(2), loginPane);

        fadeTransition.setFromValue(0.2);
        fadeTransition.setToValue(1.0);

        transition.setByY(-600);
        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(12);
        transition.play();
        fadeTransition.play();
        mainMap.setEffect(blur);

        //====================================================== sign up textFields
        sinupPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            password = newValue;
        });
        signupUsername.textProperty().addListener((observable, oldValue, newValue) -> {
            userName = newValue;
        });
        signupEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            email = newValue;
        });
        //====================================================== login textFields
        loginPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            password = newValue;
        });
        loginUsername.textProperty().addListener((observable, oldValue, newValue) -> {
            userName = newValue;
        });
        //====================================================== change password text fields
        newPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            password = newValue;
        });
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            userName = newValue;
        });
    }

    @FXML
    void goFirstMap(MouseEvent event) {
        if (player.getLevel() >= 1) {
            PaneController.getPaneController().setLevel1();
            AnchorPane anchorPane = PaneController.getPaneController().getLevel1();
            Scene scene;
            Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
            if (anchorPane.getScene() != null) {
                scene = anchorPane.getScene();
            } else {
                scene = new Scene(anchorPane, 1000, 630);
            }
            stage.setScene(scene);
            stage.show();
        } else {
            CommonMethods.showWarning(" this map is not available for you :(");
        }
    }

    @FXML
    void goSecondMap(MouseEvent event) {
        if (player.getLevel() >= 2) {
            PaneController.getPaneController().setLevel2();
            AnchorPane anchorPane = PaneController.getPaneController().getLevel2();
            Scene scene;
            Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
            if (anchorPane.getScene() != null) {
                scene = anchorPane.getScene();
            } else {
                scene = new Scene(anchorPane, 1000, 630);
            }
            stage.setScene(scene);
            stage.show();
        } else {
            CommonMethods.showWarning(" this map is not available for you :(");

        }

    }

    @FXML
    void goThirdMAp(MouseEvent event) {
        if (player.getLevel() >= 3) {
            PaneController.getPaneController().setLevel3();
            AnchorPane anchorPane = PaneController.getPaneController().getLevel3();
            Scene scene;
            Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
            if (anchorPane.getScene() != null) {
                scene = anchorPane.getScene();
            } else {
                scene = new Scene(anchorPane, 1000, 630);
            }
            stage.setScene(scene);
            stage.show();
        } else {
            CommonMethods.showWarning(" this map is not available for you :(");
        }
    }

    @FXML
    void goShopping(MouseEvent event) {
        try {
            PaneController.getPaneController().setShop();
            Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
            AnchorPane anchorPane = PaneController.getPaneController().getShop();
            Scene scene;
            if (anchorPane.getScene() != null) {
                scene = anchorPane.getScene();
            } else {
                scene = new Scene(anchorPane, 1000, 630);
            }
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void goSetting(MouseEvent event) {
        firstMap.setVisible(false);
        secondMAp.setVisible(false);
        thirdMap.setVisible(false);
        shop.setVisible(false);
        setting.setVisible(false);
        map4.setVisible(false);
        map5.setVisible(false);
        for (Line line : lines) {
            line.setVisible(false);
        }
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), settingPane);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(2), settingPane);

        fadeTransition.setFromValue(0.2);
        fadeTransition.setToValue(1.0);

        transition.setByY(-600);
        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(12);
        transition.play();
        fadeTransition.play();
        mainMap.setEffect(blur);
    }


    @FXML
    void goMap4(MouseEvent event) {
        if (player.getLevel() >= 4) {
            PaneController.getPaneController().setLevel4();
            AnchorPane anchorPane = PaneController.getPaneController().getLevel4();
            Scene scene;
            Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
            if (anchorPane.getScene() != null) {
                scene = anchorPane.getScene();
            } else {
                scene = new Scene(anchorPane, 1000, 630);
            }
            stage.setScene(scene);
            stage.show();
        } else {
            CommonMethods.showWarning(" this map is not available for you :(");
        }

    }

    @FXML
    void goMap5(MouseEvent event) {
        if (player.getLevel() >= 5) {
            PaneController.getPaneController().setLevel5();
            AnchorPane anchorPane = PaneController.getPaneController().getLevel5();
            Scene scene;
            Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
            if (anchorPane.getScene() != null) {
                scene = anchorPane.getScene();
            } else {
                scene = new Scene(anchorPane, 1000, 630);
            }
            stage.setScene(scene);
            stage.show();
        }else {
            CommonMethods.showWarning(" this map is not available for you :(");
        }
    }

    private void close() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), settingPane);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(2), settingPane);

        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0.2);

        transition.setByY(600);

        transition.play();
        fadeTransition.play();
        mainMap.setEffect(null);
    }

    public Player getPlayer() {
        return player;
    }
    public ArrayList<Line> getLines(){
        return lines;
    }
}
