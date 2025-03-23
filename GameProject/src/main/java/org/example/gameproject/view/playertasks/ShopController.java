package org.example.gameproject.view.playertasks;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import org.example.gameproject.PaneController;
import org.example.gameproject.controler.playerOperation.Shopping;
import org.example.gameproject.model.player.Player;
import org.example.gameproject.model.spells.Coin;
import org.example.gameproject.model.spells.Freeze;
import org.example.gameproject.model.spells.Heal;
import org.example.gameproject.model.spells.LittleBoy;

import java.net.URL;
import java.util.ResourceBundle;

public class ShopController implements Initializable {

    @FXML
    private VBox healBox;

    @FXML
    private VBox freezeBox;

    @FXML
    private VBox coinBox;

    @FXML
    private VBox boyBox;

    @FXML
    private Label healLabel;

    @FXML
    private Label FreezeLabel;

    @FXML
    private Label coinLabel;

    @FXML
    private Label littleboyLabel;

    @FXML
    private Button homButton;

    @FXML
    private Label moneyLabel;

    @FXML
    private Label jewelLabel;

    @FXML
    private VBox jewelBox;

    private Player player;

    public Player getPlayer() {
        return player;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        player = PaneController.getPaneController().getLoginPageController().getPlayer();
        System.out.println("player set");
        if (player != null) {
            moneyLabel.setText(String.valueOf(player.getMoney()));
            jewelLabel.setText(String.valueOf(player.getJewel()));
            coinLabel.setText(String.valueOf(player.getBackpack().getCoin()));
            littleboyLabel.setText(String.valueOf(player.getBackpack().getLittleBoy()));
            healLabel.setText(String.valueOf(player.getBackpack().getHeal()));
            FreezeLabel.setText(String.valueOf(player.getBackpack().getFreeze()));
        } else {
            System.out.println("player is null");
        }
    }

    @FXML
    void backHome(ActionEvent event) {
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
    }

    @FXML
    void buyCoin(MouseEvent event) {
        System.out.println("event");
        if (player.getJewel() >= new Coin().getPrice()) {
            Shopping.getShopping().buyCoin(player);
            coinLabel.setText(String.valueOf(player.getBackpack().getCoin()));
            jewelLabel.setText(String.valueOf(player.getJewel()));
            moneyLabel.setText(String.valueOf(player.getMoney()));
        } else {
            showWarning();
        }

    }

    @FXML
    void buyFraaze(MouseEvent event) {
        if (player.getJewel() >= new Freeze().getPrice()) {
            Shopping.getShopping().buyFreeze(player);
            FreezeLabel.setText(String.valueOf(player.getBackpack().getFreeze()));
            jewelLabel.setText(String.valueOf(player.getJewel()));
            moneyLabel.setText(String.valueOf(player.getMoney()));
        } else {
            showWarning();
        }
    }

    @FXML
    void buyHeal(MouseEvent event) {
        if (player.getJewel() >= new Heal().getPrice()) {
            Shopping.getShopping().buyHeal(player);
            healLabel.setText(String.valueOf(player.getBackpack().getHeal()));
            jewelLabel.setText(String.valueOf(player.getJewel()));
            moneyLabel.setText(String.valueOf(player.getMoney()));
        } else {
            showWarning();
        }
    }

    @FXML
    void buyJewel(MouseEvent event) {
        if (player.getMoney() >= 20) {
            Shopping.getShopping().buyJewel(player);
            jewelLabel.setText(String.valueOf(player.getJewel()));
            moneyLabel.setText(String.valueOf(player.getMoney()));
        } else {
            showWarning();
        }
    }

    @FXML
    void buyLittleBoy(MouseEvent event) {
        if (player.getJewel() >= new LittleBoy().getPrice()) {
            Shopping.getShopping().buyBoy(player);
            littleboyLabel.setText(String.valueOf(player.getBackpack().getLittleBoy()));
            jewelLabel.setText(String.valueOf(player.getJewel()));
            moneyLabel.setText(String.valueOf(player.getMoney()));
        } else {
            showWarning();
        }
    }

    private void showWarning() {
        Notifications notification = Notifications.create()
                .title("Warning")
                .text("you cant buy this item")
                .position(Pos.CENTER);
        notification.showInformation();
    }
}
