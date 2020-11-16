package cz.vse.beyr;


import cz.vse.beyr.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;


public class MainController {
    @FXML
    public StackPane stackpane;
    public VBox persons;
    public VBox inventory;
    public MenuItem quit;
    public MenuItem reset;
    private IGame game;

    public Label locationName;
    public Label locationDescription;

    public VBox exits;
    public VBox items;

    public TextArea textOutput;
    public TextField textInput;

    public ImageView background;
    public CommandShowInventory showInventory;

    public void init(IGame game) {

        this.game = game;
        textOutput.appendText(game.getPrologue());
        update();
    }
    private void update() {
        String location = getCurrentArea().getName();
        locationName.setText(location);

        String description = getCurrentArea().getDescription();
        locationDescription.setText(description);

        updateExits();
        updateItems();
        updateArea();
        updatePerson();
        updateInventory();
    }

    private void updateArea() {
         InputStream stream = getClass().getClassLoader().getResourceAsStream(getCurrentArea().getName() + ".jpg");
         Image img = new Image(stream);
         background.setImage(img);
         background.fitHeightProperty().bind(stackpane.heightProperty());
         background.fitWidthProperty().bind(stackpane.widthProperty());

     }
    private void updatePerson() {
        Collection<Person> personList = getCurrentArea().getListPerson().values();
        persons.getChildren().clear();

        for (Person person : personList) {
            String personName = person.getName();
            Label personLabel = new Label(personName);
            if(person.isMortal()) {
                getLabel(personName, personLabel);
                personLabel.setTooltip(new Tooltip("Pro osloveni klikni pravym tlacitkem mysi\n Pro zabiti klikni levym mysi."));

                personLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                            executeCommand("zabij " +personName);
                        }else if(mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                            executeCommand("promluv "+personName);
                        }
                    }
                });

            } else {
                personLabel.setTooltip(new Tooltip("Tuto osobu nemuzes zabit.\nPro osloveni klikni tlacitkem mysi"));
                getLabel(personName, personLabel);
                personLabel.setOnMouseClicked(event -> {
                    executeCommand("promluv "+personName);
                });
            }

            persons.getChildren().add(personLabel);

        }

    }


    private void updateItems() {
        Collection<Item> itemList = getCurrentArea().getItemList().values();
        items.getChildren().clear();
        inventory.getChildren().clear();
        for (Item item : itemList) {
            String itemName = item.getName();
            Label itemLabel = new Label(itemName);
            if(item.isMoveable()) {
                getLabel(itemName, itemLabel);
                itemLabel.setTooltip(new Tooltip("Pro sebrani klikni levym tlacitkem mysi.\nPro prozkoumani klikni pravym tlacitkem mysi."));

                itemLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                            executeCommand("seber " +itemName);
                        }else if(mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                            executeCommand("prozkoumej "+itemName);
                        }
                    }
                });

            } else {
                itemLabel.setTooltip(new Tooltip("Tato vec neni prenositelna.\nPro prozkoumani klikni tlacitkem mysi"));
                getLabel(itemName, itemLabel);
                itemLabel.setOnMouseClicked(event -> {
                    executeCommand("prozkoumej "+itemName);
                });
            }

            items.getChildren().add(itemLabel);
        }
    }
    private void updateExits() {
        Set<Area> exitList = getCurrentArea().getExits();
        exits.getChildren().clear();

        for (Area area : exitList) {
            String exitName = area.getName();
            Label exitLabel = new Label(exitName);
            exitLabel.setTooltip(new Tooltip(area.getDescription()));
            getLabel(exitName, exitLabel);
            exitLabel.setOnMouseClicked(event ->  {
                executeCommand("jdi "+exitName);
            });

            exits.getChildren().add(exitLabel);
        }

    }


    private void updateInventory() {
        Inventory inv = game.getInventory();
        Collection<Item> inventoryItems = inv.getInventory().values();
        inventory.getChildren().clear();

        for (Item item : inventoryItems) {
            String itemName = item.getName();
            Label itemLabel = new Label(itemName);

            itemLabel.setTooltip(new Tooltip(item.getDescription()));

            getLabel(itemName, itemLabel);

            itemLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                        executeCommand("poloz " +itemName);
                    }else if(mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                        executeCommand("prozkoumej "+itemName);
                    }
                }
            });

            inventory.getChildren().add(itemLabel);
        }
    }


    private void executeCommand(String command) {
        String result = game.processCommand(command);
        textOutput.appendText(result + "\n\n");
        update();
    }
    private void getLabel(String name, Label label) {
        label.setCursor(Cursor.HAND);
        InputStream stream = getClass().getClassLoader().getResourceAsStream(name + ".jpg");
        Image img = new Image(stream);
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(100);
        imageView.setFitHeight(70);
        label.setGraphic(imageView);
    }

    private Area getCurrentArea() {

        return game.getGamePlan().getCurrentArea();
    }

    public void onInputKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
           executeCommand(textInput.getText());
            textInput.setText("");
        }
    }

    public void help(ActionEvent actionEvent) {
        Stage primaryStage = new Stage();
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load(getClass().getResource("/help.html").toString());
        Scene scene = new Scene(webView,600,600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Help");
        primaryStage.show();
    }

    public void quit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void restart(ActionEvent actionEvent) {
        game = new Game();
        textOutput.clear();
        textInput.clear();
        items.getChildren().clear();
        inventory.getChildren().clear();
        init(game);
    }
}
