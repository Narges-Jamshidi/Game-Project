package org.example.gameproject.view.maps;

import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.gameproject.PaneController;
import org.example.gameproject.controler.databaseManage.PlayerDatabaseManage;
import org.example.gameproject.model.map.Map;
import org.example.gameproject.model.player.Player;
import org.example.gameproject.model.raiders.Raider;
import org.example.gameproject.model.spells.Coin;
import org.example.gameproject.model.spells.Freeze;
import org.example.gameproject.model.spells.Heal;
import org.example.gameproject.model.spells.LittleBoy;
import org.example.gameproject.model.towers.ArcherTower;
import org.example.gameproject.model.towers.StoneTower;
import org.example.gameproject.model.towers.Tower;
import org.example.gameproject.model.towers.wizardTower;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

import static org.example.gameproject.view.maps.CommonMethods.*;

public class MapLevel2Controller implements Initializable {
    @FXML
    AnchorPane mainPane;

    @FXML
    private Label healthLabel;

    @FXML
    private Label moneyLabel;

    @FXML
    private Label waveLabel;

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
    private ImageView towerLocation3;

    @FXML
    private ImageView towerLocation2;

    @FXML
    private ImageView towerLocation1;

    @FXML
    private ImageView towerLocation4;

    @FXML
    private ImageView towerLocation5;

    @FXML
    private Pane startPane;

    @FXML
    private Button homButton;
    //========================================
    Player player;
    Image notBuilt = new Image("file:C:\\Users\\User\\IdeaProjects\\FinalProject\\finalproject-game-sabwinam\\GameProject\\src\\main\\resources\\org\\images\\icons\\Brown_ruins4.png");
    ArcherTower archerTower = new ArcherTower();
    wizardTower wizardTower = new wizardTower();
    StoneTower stoneTower = new StoneTower();
    Image archer = new Image(archerTower.getMainImage());
    Image stone = new Image(stoneTower.getMainImage());
    Image wizard = new Image(wizardTower.getMainImage());
    Map map2 = new Map(5, new Point2D(983.2, 275.2), 80);//=========
    public java.util.Map<ImageView, Raider> existRaider = new HashMap<>();
    ArrayList<PathTransition> pathTransitions = new ArrayList<>();
    Path path = new Path();
    Path path2 = new Path();
    int wave = 0;
    java.util.Map<ImageView, Tower> towers = new LinkedHashMap<>();
    ;

    @FXML
    void backHome(MouseEvent event) {
        if (existRaider.isEmpty() && (wave == 0 || wave >= map2.getWave())) {
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
    }

    @FXML
    void dropCoin(MouseEvent event) {
        new Coin().drop(player);
        moneyLabel.setText(String.valueOf(player.getMoney()));
        coinLabel.setText(String.valueOf(player.getBackpack().getCoin()));
    }

    @FXML
    void dropFreeze(MouseEvent event) {
        new Freeze().drop(player);

        for (PathTransition transition : pathTransitions) {
            transition.pause();
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(5));
            pauseTransition.setOnFinished(actionEvent -> transition.play());
            pauseTransition.play();
        }
        FreezeLabel.setText(String.valueOf(player.getBackpack().getFreeze()));
    }

    @FXML
    void dropHeal(MouseEvent event) {
        new Heal().drop(player);
        healthLabel.setText(String.valueOf(player.getHealth()));
        healLabel.setText(String.valueOf(player.getBackpack().getHeal()));
    }

    @FXML
    void startWave(MouseEvent event) {
        if (wave < map2.getWave() && existRaider.isEmpty() && !towers.isEmpty()) {
            wave++;
            waveLabel.setText((wave) + "/" + (map2.getWave()));
            move(mainPane, player, path, pathTransitions, existRaider, healthLabel, (wave * 2), towers, map2.getRoadBend().getFirst(), map2.getRoadBend().getLast());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            move(mainPane, player, path2, pathTransitions, existRaider, healthLabel, (wave * 3), towers, map2.getRoadBend().getFirst(), map2.getRoadBend().getLast());
            throwingBullets(towers, existRaider, mainPane);
        } else if (wave == map2.getWave() && existRaider.isEmpty()) {
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
                    PaneController.getPaneController().getLoginPageController().getLines().get(2).setVisible(true);

                    player.setMoney(player.getMoney() + map2.getCoin());
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
        //==============================================

        towerLocation1.setImage(notBuilt);
        towerLocation2.setImage(notBuilt);
        towerLocation3.setImage(notBuilt);
        towerLocation4.setImage(notBuilt);

        //==============================================
        player = PaneController.getPaneController().getLoginPageController().getPlayer();
        if (player != null) {
            waveLabel.setText(0 + " / " + map2.getWave());
            moneyLabel.setText(String.valueOf(player.getMoney()));
            coinLabel.setText(String.valueOf(player.getBackpack().getCoin()));
            littleboyLabel.setText(String.valueOf(player.getBackpack().getLittleBoy()));
            healLabel.setText(String.valueOf(player.getBackpack().getHeal()));
            FreezeLabel.setText(String.valueOf(player.getBackpack().getFreeze()));
            healthLabel.setText(String.valueOf(player.getHealth()));
        } else {
            System.out.println("player is null");
        }
        //==============================================
        //The first route
        map2.setRoadBend(new Point2D(1.6, 254.4));//1
        map2.setRoadBend(new Point2D(164.8, 230.4));//2
        map2.setRoadBend(new Point2D(328.8, 301.5));//3
        map2.setRoadBend(new Point2D(414.4, 343.2));//4
        map2.setRoadBend(new Point2D(580.8, 405.6));//5
        map2.setRoadBend(new Point2D(684.8, 464));//6
        map2.setRoadBend(new Point2D(776, 444));//7
        map2.setRoadBend(new Point2D(789.6, 360.8));//8
        map2.setRoadBend(map2.getEndPoint());// end

        path.getElements().add(new MoveTo(map2.getRoadBend().getFirst().getX(), map2.getRoadBend().getFirst().getY()));

        for (int i = 1; i < map2.getRoadBend().size(); i++) {
            path.getElements().add(new LineTo(map2.getRoadBend().get(i).getX(), map2.getRoadBend().get(i).getY()));
        }
        //==============================================
        //The Second route
        map2.setRoadBend2(new Point2D(0, 426.4));//1
        map2.setRoadBend2(new Point2D(135.2, 363.2));//2
        map2.setRoadBend2(new Point2D(256.8, 370));//3
        map2.setRoadBend2(new Point2D(356.4, 335.2));//4
        map2.setRoadBend2(new Point2D(528.2, 366.4));//5
        map2.setRoadBend2(new Point2D(609.8, 460));//6
        map2.setRoadBend2(new Point2D(776, 444));//7
        map2.setRoadBend2(new Point2D(789.6, 360.8));//8
        map2.setRoadBend2(map2.getEndPoint());// end

        path2.getElements().add(new MoveTo(map2.getRoadBend2().getFirst().getX(), map2.getRoadBend2().getFirst().getY()));
        for (int i = 1; i < map2.getRoadBend().size(); i++) {
            path2.getElements().add(new LineTo(map2.getRoadBend2().get(i).getX(), map2.getRoadBend2().get(i).getY()));
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
                ((ImageView) mouseEvent.getSource()).setImage(this.archer);
                towers.put((ImageView) mouseEvent.getSource(), new ArcherTower());
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
                ((ImageView) mouseEvent.getSource()).setImage(this.stone);
                towers.put((ImageView) mouseEvent.getSource(), new StoneTower());
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
                ((ImageView) mouseEvent.getSource()).setImage(this.wizard);
                towers.put((ImageView) mouseEvent.getSource(), new wizardTower());
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
            PaneController.getPaneController().setLevel2();
            AnchorPane anchorPane = PaneController.getPaneController().getLevel2();
            Scene scene = mainPane.getScene();
            scene.setRoot(anchorPane);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
