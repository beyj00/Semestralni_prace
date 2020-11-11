package cz.vse.beyr;


import cz.vse.beyr.model.Area;
import cz.vse.beyr.model.IGame;
import javafx.scene.control.Label;

public class MainController {


    private IGame game;

    public Label locationName;
    public Label locationDescription;

    public void init(IGame game) {

        this.game = game;
        update();
    }

    private void update() {
        String location = getCurrentArea().getName();
        locationName.setText(location);

        String description = getCurrentArea().getDescription();
        locationDescription.setText(description);
    }

    private Area getCurrentArea() {
        return game.getGamePlan().getCurrentArea();
    }

}
