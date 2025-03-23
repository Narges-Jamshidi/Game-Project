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

public class MapLevel4Controller implements Initializable {
    @FXML
    private AnchorPane mainPane;

    @FXML
    private Pane startPane;

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
    private Button homButton;

    @FXML
    private ImageView towerLocation3;

    @FXML
    private ImageView towerLocation31;

    @FXML
    private ImageView towerLocation32;

    @FXML
    private ImageView towerLocation33;

    @FXML
    private ImageView towerLocation34;

    @FXML
    private ImageView towerLocation35;

    @FXML
    private ImageView towerLocation36;

    @FXML
    private ImageView towerLocation37;

    @FXML
    private ImageView towerLocation38;

    @FXML
    private ImageView towerLocation39;
    //========================================
    Player player;
    Image notBuilt = new Image("file:C:\\Users\\User\\IdeaProjects\\FinalProject\\finalproject-game-sabwinam\\GameProject\\src\\main\\resources\\org\\images\\icons\\Brown_ruins4.png");
    ArcherTower archerTower = new ArcherTower();
    org.example.gameproject.model.towers.wizardTower wizardTower = new wizardTower();
    StoneTower stoneTower = new StoneTower();
    Image archer = new Image(archerTower.getMainImage());
    Image stone = new Image(stoneTower.getMainImage());
    Image wizard = new Image(wizardTower.getMainImage());
    Map map4 = new Map(6, new Point2D(997.2, 97.6), 90);//=========
    public java.util.Map<ImageView, Raider> existRaider = new HashMap<>();
    ArrayList<PathTransition> pathTransitions = new ArrayList<>();
    Path path = new Path();
    Path path2 = new Path();
    int wave = 0;
    java.util.Map<ImageView, Tower> towers = new LinkedHashMap<>();

    @FXML
    void backHome(MouseEvent event) {
        if (existRaider.isEmpty() && (wave == 0 || wave >= map4.getWave())) {
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
        if (wave < map4.getWave() && existRaider.isEmpty() && !towers.isEmpty()) {
            wave++;
            waveLabel.setText((wave) + "/" + (map4.getWave()));
            move(mainPane, player, path, pathTransitions, existRaider, healthLabel, (wave * 3), towers, map4.getRoadBend().getFirst(), map4.getRoadBend().getLast());
            move(mainPane, player, path2, pathTransitions, existRaider, healthLabel, (wave * 2), towers, map4.getRoadBend2().getFirst(), map4.getRoadBend2().getLast());
            throwingBullets(towers, existRaider, mainPane);
        } else if (wave == 3 && existRaider.isEmpty()) {
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
                    PaneController.getPaneController().getLoginPageController().getLines().get(3).setVisible(true);
                    player.setMoney(player.getMoney() + map4.getCoin());
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
        player = PaneController.getPaneController().getLoginPageController().getPlayer();
        if (player != null) {
            waveLabel.setText(0 + " / " + map4.getWave());
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
        map4.setRoadBend(new Point2D(0, 397));//1
        map4.setRoadBend(new Point2D(339, 391));//2
        map4.setRoadBend(new Point2D(586.4, 505.6));//3
        map4.setRoadBend(new Point2D(661.6, 463.2));//4
        map4.setRoadBend(new Point2D(660, 406.4));//5
        map4.setRoadBend(new Point2D(491, 282));//6
        map4.setRoadBend(new Point2D(399, 188));//7
        map4.setRoadBend(new Point2D(440, 116));//8
        map4.setRoadBend(new Point2D(571.2, 79.2));//9
        map4.setRoadBend(new Point2D(852, 100));//10
        map4.setRoadBend(new Point2D(997.6, 97.6));//end

        path.getElements().add(new MoveTo(map4.getRoadBend().getFirst().getX(), map4.getRoadBend().getFirst().getY()));

        for (int i = 1; i < map4.getRoadBend().size(); i++) {
            path.getElements().add(new LineTo(map4.getRoadBend().get(i).getX(), map4.getRoadBend().get(i).getY()));
        }
        //==============================================
        //The Second route
        map4.setRoadBend2(new Point2D(0, 397));//1
        map4.setRoadBend2(new Point2D(339, 391));//2
        map4.setRoadBend2(new Point2D(586.4, 505.6));//3
        map4.setRoadBend2(new Point2D(661.6, 463.2));//4
        map4.setRoadBend2(new Point2D(660, 406.4));//5
        map4.setRoadBend2(new Point2D(491, 282));//6
        map4.setRoadBend2(new Point2D(572.8, 223.2));//7
        map4.setRoadBend2(new Point2D(693.8, 227.2));//8
        map4.setRoadBend2(new Point2D(826.4, 270.4));//9
        map4.setRoadBend2(new Point2D(908.8, 292));//10
        map4.setRoadBend2(new Point2D(996.6, 288));//end

        path2.getElements().add(new MoveTo(map4.getRoadBend2().getFirst().getX(), map4.getRoadBend2().getFirst().getY()));
        for (int i = 1; i < map4.getRoadBend().size(); i++) {
            path2.getElements().add(new LineTo(map4.getRoadBend2().get(i).getX(), map4.getRoadBend2().get(i).getY()));
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
            PaneController.getPaneController().setLevel4();
            AnchorPane anchorPane = PaneController.getPaneController().getLevel4();
            Scene scene = mainPane.getScene();
            scene.setRoot(anchorPane);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
