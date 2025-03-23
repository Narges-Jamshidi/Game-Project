package org.example.gameproject;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.example.gameproject.view.maps.*;
import org.example.gameproject.view.playertasks.ShopController;
import org.example.gameproject.view.playertasks.*;

public class PaneController {
    private static PaneController paneController;

    public static PaneController getPaneController() {
        if (paneController == null) {
            paneController = new PaneController();
        }
        return paneController;
    }


    AnchorPane loginPage;
    LoginPageController loginPageController;

    AnchorPane shop;
    ShopController shopController;

    AnchorPane level1;
    MapLevel1Controller mapLevel1Controller;

    AnchorPane level2;
    MapLevel2Controller mapLevel2Controller;

    AnchorPane level3;
    MapLevel3Controller mapLevel3Controller;

    AnchorPane level4;
    MapLevel4Controller mapLevel4Controller;

    AnchorPane level5;
    MapLevel5Controller mapLevel5Controller;

    AnchorPane menu;


    private PaneController() {
    }

    public void setPanes() {
        if (loginPage == null) {
            try {
                FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("loginPage.fxml"));
                loginPage = fxmlLoader1.load();
                loginPageController = fxmlLoader1.getController();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setShop() {

        try {
            FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("shop.fxml"));
            shop = fxmlLoader2.load();
            shopController = fxmlLoader2.getController();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public void setLevel1() {

            try {
                FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("mapLevel1.fxml"));
                level1 = fxmlLoader2.load();
                mapLevel1Controller = fxmlLoader2.getController();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

    }

    public void setLevel2() {

            try {
                FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("mapLevel2.fxml"));
                level2 = fxmlLoader2.load();
                mapLevel2Controller = fxmlLoader2.getController();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

    }

    public void setLevel3() {

            try {
                FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("mapLavel3.fxml"));
                level3 = fxmlLoader2.load();
                mapLevel3Controller = fxmlLoader2.getController();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

    }

    public void setLevel4() {

            try {
                FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("mapLevel4.fxml"));
                level4 = fxmlLoader2.load();
                mapLevel4Controller = fxmlLoader2.getController();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

    }

    public void setLevel5() {
            try {
                FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("mapLevel5.fxml"));
                level5 = fxmlLoader2.load();
                mapLevel5Controller = fxmlLoader2.getController();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }


    public AnchorPane getMenu() {
        return menu;
    }

    public AnchorPane getLevel1() {
        return level1;
    }

    public MapLevel1Controller getMapLevel1Controller() {
        return mapLevel1Controller;
    }

    public AnchorPane getLevel2() {
        return level2;
    }

    public MapLevel2Controller getMapLevel2Controller() {
        return mapLevel2Controller;
    }

    public AnchorPane getLevel3() {
        return level3;
    }

    public MapLevel3Controller getMapLevel3Controller() {
        return mapLevel3Controller;
    }

    public AnchorPane getShop() {
        return shop;
    }

    public ShopController getShopController() {
        return shopController;
    }

    public AnchorPane getLoginPage() {
        return loginPage;
    }

    public LoginPageController getLoginPageController() {
        return loginPageController;
    }

    public AnchorPane getLevel4() {
        return level4;
    }

    public MapLevel4Controller getMapLevel4Controller() {
        return mapLevel4Controller;
    }

    public AnchorPane getLevel5() {
        return level5;
    }

    public MapLevel5Controller getMapLevel5Controller() {
        return mapLevel5Controller;
    }
}
