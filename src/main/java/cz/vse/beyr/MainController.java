package cz.vse.beyr;


import cz.vse.beyr.model.Area;
import cz.vse.beyr.model.CommandMove;
import cz.vse.beyr.model.IGame;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.Set;
import java.util.function.Consumer;

public class MainController {



    private IGame game;

    public Label locationName;
    public Label locationDescription;

    public VBox exits;
    public VBox items;

    public void init(IGame game) {

        this.game = game;
        update();
    }

    private void update() {
        String location = getCurrentArea().getName();
        locationName.setText(location);

        String description = getCurrentArea().getDescription();
        locationDescription.setText(description);

        updateExits();
        updateItems();
    }

    private void updateItems() {

    }

    private void updateExits() {
        Set<Area> exitList = getCurrentArea().getExits();
        exits.getChildren().clear();

        for (Area area : exitList) {
            String exitName = area.getName();
            Label exitLabel = new Label(exitName);
            exits.getChildren().add(exitLabel);
            exitLabel.setCursor(Cursor.HAND);
            exitLabel.setOnMouseClicked(event ->  {
                String vysledek = game.processCommand("jdi " + exitName);
                System.out.println(vysledek);
                update();
            });

        }



    }

    private Area getCurrentArea() {

        return game.getGamePlan().getCurrentArea();
    }

}
