package org.example.gameproject.view.maps;

import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.example.gameproject.PaneController;
import org.example.gameproject.controler.databaseManage.PlayerDatabaseManage;
import org.example.gameproject.model.map.Map;
import org.example.gameproject.model.player.Player;
import org.example.gameproject.model.raiders.*;
import org.example.gameproject.model.spells.Coin;
import org.example.gameproject.model.spells.Freeze;
import org.example.gameproject.model.spells.Heal;
import org.example.gameproject.model.spells.LittleBoy;
import org.example.gameproject.model.towers.ArcherTower;
import org.example.gameproject.model.towers.StoneTower;
import org.example.gameproject.model.towers.Tower;
import org.example.gameproject.model.towers.wizardTower;

import java.net.URL;
import java.util.*;

import static org.example.gameproject.view.maps.CommonMethods.*;


public class MapLevel1Controller implements Initializable {
    @FXML
    private Label healthLabel;

    @FXML
    private Label moneyLabel;

    @FXML
    private Label waveLabel;

    @FXML
    private ImageView towerLocation1;

    @FXML
    private ImageView towerLocation2;

    @FXML
    private ImageView towerLocation3;

    @FXML
    private ImageView towerLocation4;

    @FXML
    private ImageView healImage;

    @FXML
    private Label healLabel;

    @FXML
    private ImageView freezeImage;

    @FXML
    private Label FreezeLabel;

    @FXML
    private ImageView coinImage;

    @FXML
    private Label coinLabel;

    @FXML
    private ImageView littleBoyImage;

    @FXML
    private Label littleboyLabel;

    @FXML
    private Pane startPane;

    @FXML
    private Button homButton;

    @FXML
    private AnchorPane mainPane;

    //============================================
    Player player;
    Image notBuilt = new Image("file:C:\\Users\\User\\IdeaProjects\\FinalProject\\finalproject-game-sabwinam\\GameProject\\src\\main\\resources\\org\\images\\icons\\Brown_ruins4.png");
    ArcherTower archerTower = new ArcherTower();
    wizardTower wizardTower = new wizardTower();
    StoneTower stoneTower = new StoneTower();
    Image archer = new Image(archerTower.getMainImage());
    Image stone = new Image(stoneTower.getMainImage());
    Image wizard = new Image(wizardTower.getMainImage());
    Map map1 = new Map(3, new Point2D(999.2, 124.8), 50);
    public java.util.Map<ImageView, Raider> existRaider = new HashMap<>();
    ArrayList<PathTransition> pathTransitions = new ArrayList<>();
    Path path = new Path();
    int wave = 0;
    java.util.Map<ImageView, Tower> towers = new LinkedHashMap<>();

    @FXML
    void backHome(MouseEvent event) {
        if (existRaider.isEmpty() && (wave == 0 || wave >= map1.getWave())) {
            try {
                Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
                AnchorPane anchorPane = PaneController.getPaneController().getLoginPage();
                Scene scene = anchorPane.getScene();
                scene.setRoot(anchorPane);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            CommonMethods.showWarning("The soldiers are fighting '\n' You cannot leave the battlefield");
        }

    }

    @FXML
    void buildTower(MouseEvent event) {
        VBox vBox = CommonMethods.creatMenu();
        vBox.setLayoutX(event.getSceneX());
        vBox.setLayoutY(event.getSceneY());
        mainPane.getChildren().add(vBox);
        goBuilding(vBox, event);
    }

    @FXML
    void dropBoy(MouseEvent event) {
        if (player.getBackpack().getLittleBoy() > 0) {
            new LittleBoy().drop(player);
            for (PathTransition p : pathTransitions) {
                p.stop();
            }
            for (java.util.Map.Entry<ImageView, Raider> raiderEntry : existRaider.entrySet()) {
                mainPane.getChildren().remove(raiderEntry.getKey());
            }
            existRaider.clear();
            pathTransitions.clear();
            littleboyLabel.setText(String.valueOf(player.getBackpack().getLittleBoy()));
        } else showWarning(" you cannot drop this spell");

    }

    @FXML
    void dropCoin(MouseEvent event) {
        if (player.getBackpack().getCoin() > 0) {
            new Coin().drop(player);
            moneyLabel.setText(String.valueOf(player.getMoney()));
            coinLabel.setText(String.valueOf(player.getBackpack().getCoin()));
        } else
            showWarning(" you cannot drop this spell");

    }

    @FXML
    void dropFreeze(MouseEvent event) {
        if (player.getBackpack().getFreeze() > 0) {
            new Freeze().drop(player);

            for (PathTransition transition : pathTransitions) {
                transition.pause();
                PauseTransition pauseTransition = new PauseTransition(Duration.seconds(5));
                pauseTransition.setOnFinished(actionEvent -> transition.play());
                pauseTransition.play();
            }
            FreezeLabel.setText(String.valueOf(player.getBackpack().getFreeze()));
        } else
            showWarning(" you cannot drop this spell");
    }

    @FXML
    void dropHeal(MouseEvent event) {
        if (player.getBackpack().getHeal() > 0) {
            new Heal().drop(player);
            healthLabel.setText(String.valueOf(player.getHealth()));
            healLabel.setText(String.valueOf(player.getBackpack().getHeal()));
        } else
            showWarning(" you cannot drop this spell");

    }

    boolean end = false;

    @FXML
    void startWave(MouseEvent event) {
        System.out.println("Wave started");
        if (wave < map1.getWave() && existRaider.isEmpty() && !towers.isEmpty()) {
            wave++;
            waveLabel.setText((wave) + "/" + (map1.getWave()));
            move(mainPane, player, path, pathTransitions, existRaider, healthLabel, (3), towers, map1.getRoadBend().getFirst(), map1.getRoadBend().getLast());
            throwingBullets(towers, existRaider, mainPane);
        } else if (wave == map1.getWave()) {
            if (player.getHealth() < 1) {
                CommonMethods.showWarning(" Game Over !!!");
                PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));
                pauseTransition.setOnFinished(event1 -> {
                    goHome(event);
                });
                pauseTransition.play();
            } else {
                CommonMethods.showWarning(" Winner !!!!!!!!");
                PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));
                pauseTransition.setOnFinished(event1 -> {
                    PaneController.getPaneController().getLoginPageController().getLines().get(0).setVisible(true);
                    player.setMoney(player.getMoney() + map1.getCoin());
                    player.setLevel(player.getLevel() + 1);
                    PlayerDatabaseManage.getPlayerDatabaseManage().updateLevel(player.getLevel(), player.getId());
                    PlayerDatabaseManage.getPlayerDatabaseManage().updateMoney(player.getMoney(), player.getId());
                    moneyLabel.setText(String.valueOf(player.getMoney()));
                    goHome(event);
                });
                pauseTransition.play();
            }
        } else
            CommonMethods.showWarning("you cant start war");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainPane.setOnMouseClicked(event -> {
            System.out.println("X:  " + event.getSceneX());
            System.out.println("y:  " + event.getSceneY());
        });
        player = PaneController.getPaneController().getLoginPageController().getPlayer();
        towerLocation1.setImage(notBuilt);
        towerLocation2.setImage(notBuilt);
        towerLocation3.setImage(notBuilt);
        towerLocation4.setImage(notBuilt);
        if (player != null) {
            waveLabel.setText(0 + " / " + map1.getWave());
            moneyLabel.setText(String.valueOf(player.getMoney()));
            coinLabel.setText(String.valueOf(player.getBackpack().getCoin()));
            littleboyLabel.setText(String.valueOf(player.getBackpack().getLittleBoy()));
            healLabel.setText(String.valueOf(player.getBackpack().getHeal()));
            FreezeLabel.setText(String.valueOf(player.getBackpack().getFreeze()));
            healthLabel.setText(String.valueOf(player.getHealth()));
        } else {
            System.out.println("player is null");
        }
        //=====================================================
        map1.setRoadBend(new Point2D(14, 247));
        map1.setRoadBend(new Point2D(214.4, 204.0));
        map1.setRoadBend(new Point2D(397.6, 291.2));
        map1.setRoadBend(new Point2D(644, 270.4));
        map1.setRoadBend(new Point2D(804.8, 151.2));
        map1.setRoadBend(map1.getEndPoint());

        path.getElements().add(new MoveTo(map1.getRoadBend().getFirst().getX(), map1.getRoadBend().getFirst().getY()));

        for (int i = 1; i < map1.getRoadBend().size(); i++) {
            path.getElements().add(new LineTo(map1.getRoadBend().get(i).getX(), map1.getRoadBend().get(i).getY()));
        }


    }

    private void goBuilding(VBox vBox, MouseEvent mouseEvent) {
        Label archer = (Label) vBox.getChildren().get(0);
        Label stone = (Label) vBox.getChildren().get(1);
        Label wizard = (Label) vBox.getChildren().get(2);
        Label update = (Label) vBox.getChildren().get(3);
        Label destroy = (Label) vBox.getChildren().get(4);

        archer.setOnMouseClicked(event -> {
            if (player.getMoney() >= archerTower.getCost()) {
                ImageView archerImage = ((ImageView) mouseEvent.getSource());
                towers.put(archerImage, new ArcherTower());
                archerImage.setImage(this.archer);
                player.setMoney(player.getMoney() - archerTower.getCost());
                PlayerDatabaseManage.getPlayerDatabaseManage().updateMoney(player.getMoney(), player.getId());
                moneyLabel.setText(String.valueOf(player.getMoney()));
            } else {
                CommonMethods.showWarning(" you cant buy this item.");
            }
            mainPane.getChildren().remove(vBox);
        });
        stone.setOnMouseClicked(event -> {
            if (player.getMoney() >= stoneTower.getCost()) {
                ImageView stoneImage = (ImageView) mouseEvent.getSource();
                (stoneImage).setImage(this.stone);
                towers.put(stoneImage, new StoneTower());
                player.setMoney(player.getMoney() - stoneTower.getCost());
                PlayerDatabaseManage.getPlayerDatabaseManage().updateMoney(player.getMoney(), player.getId());
                moneyLabel.setText(String.valueOf(player.getMoney()));

            } else {
                CommonMethods.showWarning(" you cant buy this item.");
            }
            mainPane.getChildren().remove(vBox);
        });
        wizard.setOnMouseClicked(event -> {
            if (player.getMoney() >= stoneTower.getCost()) {
                ImageView wizardImage = (ImageView) mouseEvent.getSource();
                (wizardImage).setImage(this.wizard);
                towers.put(wizardImage, new wizardTower());
                player.setMoney(player.getMoney() - wizardTower.getCost());
                PlayerDatabaseManage.getPlayerDatabaseManage().updateMoney(player.getMoney(), player.getId());
                moneyLabel.setText(String.valueOf(player.getMoney()));
            } else {
                CommonMethods.showWarning(" you cant buy this item.");
            }
            mainPane.getChildren().remove(vBox);
        });
        update.setOnMouseClicked(event -> {
            ImageView image = (ImageView) mouseEvent.getSource();
            Tower selectedTower = towers.get(image);
            if (selectedTower.getLevel() < player.getLevel()) {
                selectedTower.setLevel(selectedTower.getLevel() + 1);
                selectedTower.setRadius(selectedTower.getRadius() + 50);
                selectedTower.setPower(selectedTower.getPower() + 5);
                player.setMoney((player.getMoney()) - 10);
                selectedTower.setCost(10);
                PlayerDatabaseManage.getPlayerDatabaseManage().updateMoney(player.getMoney(), player.getId());
                moneyLabel.setText(String.valueOf(player.getMoney()));
                towers.put(image, selectedTower);
            } else {
                CommonMethods.showWarning("cant level up");
            }
            mainPane.getChildren().remove(vBox);
        });
        destroy.setOnMouseClicked(event -> {
            if (!towers.isEmpty()) {
                Tower selectedTower = towers.get(((ImageView) mouseEvent.getSource()));
                player.setMoney((player.getMoney()) + selectedTower.getCost() / 2);
                PlayerDatabaseManage.getPlayerDatabaseManage().updateMoney(player.getMoney(), player.getId());
                towers.remove(((ImageView) mouseEvent.getSource()));
                moneyLabel.setText(String.valueOf(player.getMoney()));
            }
            mainPane.getChildren().remove(vBox);
            ((ImageView) mouseEvent.getSource()).setImage(notBuilt);
        });
    }

    @FXML
    void replay(MouseEvent event) {
        try {
            Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
            PaneController.getPaneController().setLevel1();
            AnchorPane anchorPane = PaneController.getPaneController().getLevel1();
            Scene scene = mainPane.getScene();
            scene.setRoot(anchorPane);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
