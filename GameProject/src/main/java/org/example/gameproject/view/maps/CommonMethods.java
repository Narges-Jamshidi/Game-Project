package org.example.gameproject.view.maps;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.example.gameproject.PaneController;
import org.example.gameproject.controler.databaseManage.PlayerDatabaseManage;
import org.example.gameproject.model.player.Player;
import org.example.gameproject.model.raiders.*;
import org.example.gameproject.model.towers.StoneTower;
import org.example.gameproject.model.towers.Tower;
import org.example.gameproject.model.towers.wizardTower;

import java.util.*;

import static javafx.scene.paint.Color.rgb;

public class CommonMethods {

    static ArrayList<Point2D> attackPoints = new ArrayList<>();
    private static Map<ImageView, Timeline> towerTimelines = new HashMap<>();


    public static VBox creatMenu() {
        VBox vBox = new VBox(10);
        vBox.setBackground(new Background(new BackgroundFill(rgb(54, 36, 0), new CornerRadii(15), Insets.EMPTY)));
        vBox.setPrefSize(150, 100);

        Text text1 = new Text(" Archer Tower");
        Text text2 = new Text(" Stone Tower");
        Text text3 = new Text(" Wizard Tower");
        Text text4 = new Text(" Update");
        Text text5 = new Text(" Destroy");

        text1.setFont(Font.font("Gabriola", 20));
        text1.setFill(Color.WHITE);
        text2.setFont(Font.font("Gabriola", 20));
        text2.setFill(Color.WHITE);
        text3.setFont(Font.font("Gabriola", 20));
        text3.setFill(Color.WHITE);
        text4.setFont(Font.font("Gabriola", 20));
        text4.setFill(Color.WHITE);
        text5.setFont(Font.font("Gabriola", 20));
        text5.setFill(Color.WHITE);

        Label label1 = new Label();
        label1.setGraphic(new TextFlow(text1));
        label1.setPrefSize(150, 20);
        Label label2 = new Label();
        label2.setGraphic(new TextFlow(text2));
        label2.setPrefSize(150, 20);
        Label label3 = new Label();
        label3.setGraphic(new TextFlow(text3));
        label3.setPrefSize(150, 20);
        Label label4 = new Label();
        label4.setGraphic(new TextFlow(text4));
        label4.setPrefSize(150, 20);
        Label label5 = new Label();
        label5.setGraphic(new TextFlow(text5));
        label5.setPrefSize(150, 20);

        Border border = new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(20), BorderWidths.DEFAULT));
        label1.setBorder(border);
        label2.setBorder(border);
        label3.setBorder(border);
        label4.setBorder(border);
        label5.setBorder(border);

        vBox.getChildren().addAll(label1, label2, label3, label4, label5);
        return vBox;
    }

    public static void showWarning(String message) {
        Notifications notification = Notifications.create().title("Warning").text(message).position(Pos.CENTER);
        notification.showInformation();
    }

    static int i = 0;

   /* public static Timeline animation(ImageView walkerImage, Raider walker) {
        i += 20;
        System.out.println(walker.getSpeed());
        Timeline animation = new Timeline();
        animation.getKeyFrames().addAll(new KeyFrame(Duration.millis(i), event -> {
            walkerImage.setImage(new Image(walker.getAnimationImage().getFirst()));
        }), new KeyFrame(Duration.millis(i + 100), event -> {
            walkerImage.setImage(new Image(walker.getAnimationImage().get(3)));
        }), new KeyFrame(Duration.millis(i + 200), event -> {
            walkerImage.setImage(new Image(walker.getAnimationImage().get(6)));
        }), new KeyFrame(Duration.millis(i + 400), event -> {
            walkerImage.setImage(new Image(walker.getAnimationImage().getLast()));
        }));
        animation.setCycleCount(-1);

        return animation;
    }*/

    public static PathTransition Transition(Path path, ImageView raiderImage, Raider raider) {
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(raider.getSpeed()));
        pathTransition.setPath(path);
        pathTransition.setNode(raiderImage);
        pathTransition.setCycleCount(1);
        return pathTransition;
    }

    public static void throwingBullets(Map<ImageView, Tower> towers, Map<ImageView, Raider> existRaider, Pane mainPane) {
        for (Map.Entry<ImageView, Tower> entry : towers.entrySet()) {
            ImageView towerImage = entry.getKey();
            Tower tower = entry.getValue();
            Timeline shootingTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
                if (tower instanceof StoneTower) {
                    howitzer(towerImage, tower, existRaider, mainPane);
                } else {
                    for (Map.Entry<ImageView, Raider> raiderEntry : existRaider.entrySet()) {
                        ImageView raiderImage = raiderEntry.getKey();
                        Raider raider = raiderEntry.getValue();
                        if (isInRange(towerImage, raiderImage, tower.getRadius())) {
                            shootBullet(tower, towerImage, raiderImage, raider, mainPane, existRaider);
                            break;
                        }
                    }
                }
            }));
            shootingTimeline.setCycleCount(Timeline.INDEFINITE);
            shootingTimeline.play();
            towerTimelines.put(towerImage, shootingTimeline);
        }
    }

    public static void stopTowerTimeline(ImageView towerImage) {
        Timeline timeline = towerTimelines.get(towerImage);
        if (timeline != null) {
            timeline.stop();
            towerTimelines.remove(towerImage);
        }
    }

    private static boolean isInRange(ImageView towerImage, ImageView raider, int radius) {
        Bounds towerBounds = towerImage.getBoundsInParent();
        Bounds raiderBounds = raider.getBoundsInParent();

        double towerCenterX = towerBounds.getMinX() + towerBounds.getWidth() / 2;
        double towerCenterY = towerBounds.getMinY() + towerBounds.getHeight() / 2;
        double raiderCenterX = raiderBounds.getMinX() + raiderBounds.getWidth() / 2;
        double raiderCenterY = raiderBounds.getMinY() + raiderBounds.getHeight() / 2;

        double distance = Math.sqrt(Math.pow(towerCenterX - raiderCenterX, 2) + Math.pow(towerCenterY - raiderCenterY, 2));
        return distance <= radius;
    }

    //خمپاره انداز
    private static void howitzer(ImageView towerImage, Tower tower, Map<ImageView, Raider> existRaider, Pane mainPane) {
        Bounds towerBounds = towerImage.getBoundsInParent();

        double towerCenterX = (towerBounds.getMinX() + towerBounds.getWidth() / 2);
        double towerCenterY = (towerBounds.getMinY() + towerBounds.getHeight() / 2);


        for (Map.Entry<ImageView, Raider> raiderEntry : existRaider.entrySet()) {
            ImageView raiderImage = raiderEntry.getKey();
            Raider raider = raiderEntry.getValue();
            if (!(raider instanceof Monster)) {
                Bounds raiderBounds = raiderImage.getBoundsInParent();
                double raiderCenterX = raiderBounds.getMinX() + raiderBounds.getWidth() / 2;
                double raiderCenterY = raiderBounds.getMinY() + raiderBounds.getHeight() / 2;
                if (isInRange(towerImage, raiderImage, tower.getRadius())) {
                    Circle bullet = new Circle(5);
                    bullet.setLayoutX(towerCenterX);
                    bullet.setLayoutY(towerCenterY);
                    mainPane.getChildren().add(bullet);

                    Path path = new Path();
                    path.getElements().add(new MoveTo(towerCenterX + 10, towerCenterY + 200));
                    path.getElements().add(new LineTo(raiderCenterX + 10, raiderCenterY + 200));

                    PathTransition pathTransition = new PathTransition();
                    pathTransition.setDuration(Duration.seconds(1.5));
                    pathTransition.setPath(path);
                    pathTransition.setNode(bullet);
                    pathTransition.setOnFinished(event -> {
                        raider.setHealth(raider.getHealth() - tower.getPower());
                        System.out.println("Raider Health: " + raider.getHealth());
                        mainPane.getChildren().remove(bullet);

                        if (raider.getHealth() <= 0) {
                            mainPane.getChildren().remove(raiderImage);
                            existRaider.remove(raiderImage);
                        }
                    });
                    pathTransition.play();
                }
            }
        }
    }

    private static void shootBullet(Tower tower, ImageView towerImage, ImageView raiderImage, Raider raider, Pane mainPane, Map<ImageView, Raider> existRaider) {
        //پرتاب گلوله
        Bounds towerBounds = towerImage.getBoundsInParent();
        Bounds raiderBounds = raiderImage.getBoundsInParent();

        double towerCenterX = towerBounds.getMinX() + towerBounds.getWidth() / 2;
        double towerCenterY = towerBounds.getMinY() + towerBounds.getHeight() / 2;
        double raiderCenterX = raiderBounds.getMinX() + raiderBounds.getWidth() / 2;
        double raiderCenterY = raiderBounds.getMinY() + raiderBounds.getHeight() / 2;

        Circle circle = new Circle();
        circle.setRadius(5);
        circle.setCenterY(towerCenterY);
        circle.setCenterX(towerCenterX);
        mainPane.getChildren().add(circle);

        Path path = new Path();
        path.getElements().add(new MoveTo(towerCenterX, towerCenterY));

        ArcTo arcTo = new ArcTo();
        arcTo.setX(raiderCenterX);
        arcTo.setY(raiderCenterY);
        arcTo.setRadiusX(Math.abs(raiderCenterX - towerCenterX) / 2);
        arcTo.setRadiusY(Math.abs(raiderCenterY - towerCenterY) / 2);
        arcTo.setSweepFlag(false); //خلاف جهت عقربه های ساعت
        path.getElements().add(arcTo);

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(1)); // مدت زمان حرکت گلوله
        pathTransition.setPath(path);
        pathTransition.setNode(circle);
        pathTransition.setInterpolator(Interpolator.LINEAR);// حرکت یکنواخت
        pathTransition.play();

        if (raider instanceof Troll && tower instanceof wizardTower) {
            ((Troll) raider).setShield(false);
        }
        pathTransition.setOnFinished(event -> {
            if (raider instanceof Troll && ((Troll) raider).isShield()) {
                raider.setHealth(raider.getHealth() - (tower.getPower() / 2));
            } else {
                raider.setHealth(raider.getHealth() - tower.getPower());
            }
            System.out.println("Raider Health: " + raider.getHealth());
            mainPane.getChildren().remove(circle);
            if (raider.getHealth() <= 0) {
                mainPane.getChildren().remove(raiderImage);
                existRaider.remove(raiderImage);

            }
        });
    }

    static int num = 0;

    private static void addPoints(Point2D firstPoint, Map<ImageView, Tower> towers, Point2D lastPoint) {
        attackPoints.add(firstPoint);

        for (ImageView towerImage : towers.keySet()) {
            double towerCenterX = towerImage.getBoundsInParent().getMinX() + towerImage.getBoundsInParent().getWidth() / 2;
            double towerCenterY = towerImage.getBoundsInParent().getMinY() + towerImage.getBoundsInParent().getHeight() / 2;
            attackPoints.add(new Point2D(towerCenterX, towerCenterY));
        }
        attackPoints.add(lastPoint);
    }

    public static PathTransition transitionToTower(Point2D firstPoint, Point2D secondPoint, Raider raider, ImageView raiderImage) {
        PathTransition pathTransition = new PathTransition();
        Path path = new Path();
        path.getElements().add(new MoveTo(firstPoint.getX(), firstPoint.getY()));
        path.getElements().add(new LineTo(secondPoint.getX(), secondPoint.getY()));
        pathTransition.setDuration(Duration.seconds(raider.getSpeed()));
        pathTransition.setPath(path);
        pathTransition.setNode(raiderImage);
        pathTransition.setAutoReverse(false);
        pathTransition.setCycleCount(1);
        return pathTransition;
    }

    private static void attack(ImageView raiderImage, Raider raider, Map<ImageView, Tower> towers, AnchorPane mainPane, Player player, Map<ImageView, Raider> existRaider, Label healthLabel) {
        raiderImage.setImage(new Image(((Attacker) raider).getKicking()));
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(6));
        pauseTransition.setOnFinished(event -> {
            if (existRaider.containsKey(raiderImage)) {
                removeFirstElement(towers, mainPane);
            }
            if (!attackPoints.isEmpty()) {
                attackPoints.removeFirst();
            }
            System.out.println("attack point +++++++++" + attackPoints.getFirst().getX());
            if (attackPoints.size() > 2) {
                System.out.println("2222222222222222222222");
                PathTransition goToTower = transitionToTower(attackPoints.getFirst(), attackPoints.get(1), raider, raiderImage);
                raiderImage.setImage(new Image(raider.getWalkingImage()));
                goToTower.setOnFinished(event2 -> {
                    goToTower.stop();
                    attack(raiderImage, raider, towers, mainPane, player, existRaider, healthLabel);
                });
                goToTower.play();
            } else {
                PathTransition goEndPoint = transitionToTower(attackPoints.getFirst(), attackPoints.get(1), raider, raiderImage);
                goEndPoint.setOnFinished(event2 -> {
                    attackPoints.clear();
                    System.out.println("clear");
                    goEndPoint.stop();
                    mainPane.getChildren().remove(raiderImage);
                    existRaider.remove(raiderImage);
                    player.setHealth(player.getHealth() - 1);
                    PlayerDatabaseManage.getPlayerDatabaseManage().updateHealth(player.getHealth(), player.getId());
                });
                goEndPoint.play();
            }
        });
        pauseTransition.play();
    }

    public static <K, V> void removeFirstElement(Map<K, V> map, AnchorPane mainPane) {
        Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
        if (iterator.hasNext()) {
            Map.Entry<K, V> image = iterator.next();
            mainPane.getChildren().remove(image.getKey());
            stopTowerTimeline((ImageView) image.getKey());
            iterator.remove();
        }
    }

    static boolean endWave = false;

    synchronized public static void move(AnchorPane mainPane, Player player, Path path, ArrayList<PathTransition> pathTransitions, Map<ImageView,
            Raider> existRaider, Label healthLabel, int wave, Map<ImageView, Tower> towers, Point2D firstPoint, Point2D lastPoint) {

        if (attackPoints.isEmpty()) {
            addPoints(firstPoint, towers, lastPoint);
        }
        if (endWave) {
            attackPoints.clear();
        }

        if (num > wave) {
            endWave = true;
            num = 0;
            return;
        }
        endWave = false;
        num++;
        Raider raider = new Golem();

        Random random = new Random();
        switch (random.nextInt(5) + 1) {
            case 4:
                raider = new Reaper();
                break;
            case 1:
                raider = new Troll();
                break;
            case 2:
                raider = new Monster();
                break;
            case 3:
                raider = new Golem();
                break;
            case 5:
                raider = new Attacker();
        }

        ImageView imageView = new ImageView();
        imageView.setImage(new Image(raider.getWalkingImage()));
        imageView.setFitHeight(40);
        imageView.setFitWidth(30);
        existRaider.put(imageView, raider);
        mainPane.getChildren().add(imageView);

        //=================================================
        if (raider instanceof Attacker) {
            Raider finalRaider = raider;
            new Thread(() -> {
                Platform.runLater(() -> {
                    PathTransition goToTower = transitionToTower(attackPoints.getFirst(), attackPoints.get(1), finalRaider, imageView);
                    goToTower.setOnFinished(event -> {
                        attack(imageView, finalRaider, towers, mainPane, player, existRaider, healthLabel);
                    });
                    goToTower.play();
                });
            }).start();
        }
        //=================================================
        else {
            PathTransition walking = Transition(path, imageView, raider);
            pathTransitions.add(walking);
            /*timeline.play();*/
            walking.play();
            walking.setOnFinished(event -> {
                mainPane.getChildren().remove(imageView);
                existRaider.remove(imageView);
                pathTransitions.remove(walking);
                walking.stop();
                /*timeline.stop();*/
                PlayerDatabaseManage.getPlayerDatabaseManage().updateHealth(player.getHealth() - 1, player.getId());
                player.setHealth(player.getHealth() - 1);
                healthLabel.setText(String.valueOf(player.getHealth()));

            });
        }
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));

        pauseTransition.setOnFinished(event -> {
            move(mainPane, player, path, pathTransitions, existRaider, healthLabel, wave, towers, firstPoint, lastPoint);
        });
        pauseTransition.play();
    }


    public static void goHome(MouseEvent event) {
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

   /* private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void move(AnchorPane mainPane, Path path, ArrayList <PathTransition> pathTransitions, ArrayList <ImageView> existRaider) {
       num++;
       if(num>8){
       return;
       }
       Raider raider = new Golem();
       Random random = new Random();
       switch (random.nextInt(4) + 1) {
           case 4: raider = new Reaper(); break;
           case 1: raider = new Troll(); break;
           case 2: raider = new Monster(); break;
           case 3: raider = new Golem(); break;
       }

       ImageView imageView = new ImageView();
       imageView.setFitHeight(40);
       imageView.setFitWidth(30);
       existRaider.add(imageView);
        mainPane.getChildren().add(imageView);

       PathTransition walking = Transition(path, imageView, raider);
       Timeline timeline = animation(imageView, raider);
       pathTransitions.add(walking);
       timeline.play();
       walking.play();

           Platform.runLater(() -> {
       walking.setOnFinished(event -> {
               mainPane.getChildren().remove(imageView);
               existRaider.remove(imageView);
               pathTransitions.remove(walking);
           });
       if (num < 9) {
           scheduler.schedule(() -> move(mainPane, path, pathTransitions, existRaider), 1, TimeUnit.SECONDS);
       }
       });

   }
*/
}
