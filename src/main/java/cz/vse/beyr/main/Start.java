package cz.vse.beyr.main;


import cz.vse.beyr.MainController;
import cz.vse.beyr.model.Game;
import cz.vse.beyr.model.IGame;
import cz.vse.beyr.textui.TextUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sun.font.Decoration;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Hlavní třída určená pro spuštění hry. Obsahuje pouze statickou metodu
 * {@linkplain #main(String[]) main}, která vytvoří instance logiky hry
 * a uživatelského rozhraní, propojí je a zahájí hru.
 *
 * @author Jarmila Pavlíčková
 * @author Jan Říha
 * @version LS 2020
 */
public final class Start extends Application
{
    /**
     * Metoda pro spuštění celé aplikace.
     *
     * @param args parametry aplikace z příkazového řádku
     */
    public static void main(String[] args)
    {

        List<String> vstup = Arrays.asList(args);

        if (vstup.contains("text")) {
            IGame game = new Game();
            TextUI textUI = new TextUI(game);
            textUI.play();
        }else {
            launch();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Startuji");
        primaryStage.setFullScreen(true);
        primaryStage.setTitle("Vikingové");
        primaryStage.show();


        FXMLLoader loader = new FXMLLoader();
        InputStream stream = getClass().getClassLoader().getResourceAsStream("scene.fxml");
        Parent root = loader.load(stream);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        MainController controller = loader.getController();
        IGame game = new Game();

        controller.init(game);
        primaryStage.show();

    }

}
