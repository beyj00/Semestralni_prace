package cz.vse.beyr;
/** Třída představuje grafické ztvárnění hry. Tato třída je propojena se souborem scene.fxml, kde se řeší rozložení aplikace a tvorba kontejneru a kontroleru.
 * V této třídě se nastavuje grafické zobrazení kontejneru a kontroleru a co budou vypisovat. Nakonec se zde řeší funkčnost akci například kliknutí
 * myši a následné provedení příkazu. Trida je napojena na tridu IGame.*/

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
    public VBox topBox;
    public VBox rightBox;
    public VBox leftBox;
    public VBox bottomBox;
    private IGame game;

    public Label locationName;
    public Label locationDescription;

    public VBox exits;
    public VBox items;

    public TextArea textOutput;
    public TextField textInput;

    public ImageView background;
    public CommandShowInventory showInventory;

    /**Tato metoda slouží pro zahájení a vytvoření hry, aktualizování veškeré grafiky a logiky (nastavení do počátečních hodnot) a vepsání prologu do
     *  textArey.*/
    public void init(IGame game) {

        this.game = game;
        textOutput.appendText(game.getPrologue());
        update();
    }
    /** Tato metoda slouží jako shrnutí veškerých updateu jako například nstavení východů, předmětů, aktuální lokace atd...
     * dále se do labelů vypisuje jméno a popis aktuální lokace. */
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
        updateGameOver();
    }
    /** Tato metoda nastavuje pozadí podle aktuální lokace.*/
    private void updateArea() {
         InputStream stream = getClass().getClassLoader().getResourceAsStream(getCurrentArea().getName() + ".jpg");
         Image img = new Image(stream);
         background.setImage(img);
         background.fitHeightProperty().bind(stackpane.heightProperty());
         background.fitWidthProperty().bind(stackpane.widthProperty());

     }
     /** Tato metoda nastavuje ve Labelu vypsání osob a přiřazení obrázků. Při každém zavoláním této metody se label s postavami vyčistí a nastaví nové osoby.
      *  Dále nastavuje tooltip(nápovědu) k dané osobě.
      * Pokud lze osobu zabít, nastavuje se příkaz pro zabití jako kliknutí leveho tlačítka myši a příkaz pro oslovení jako pravé tlačítko myši.
      * Pokud osobu nelze zabít, je nastaven příkaz promluv libovolným tlačítkem myši.*/
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
                    /** Metoda představuje nastavení provedení příkazu podle toho, na jaké tlačítko myši se klikne.*/
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
    /** Tato metoda nastavuje ve Labelu vypsání předmětů a přiřazení obrázků. Při každém zavoláním této metody se label s předměty vyčistí a nastaví předměty osoby.
     *  Dále nastavuje tooltip(nápovědu) k danému předmětu.
     *  Pokud předmět lze sebrat, nastavuje se jeho sebrani na levé tlačítko myši, pro získání popisku kliknout na pravé tlačítko myši.
     *  Pokud předmět nelze sebrat, je vypsáno v tooltipu, že nelze sebrat a stisknutím libovonlého tlačítka na myši se vypíše popis předmětu.
     *  */
    private void updateItems() {
        Collection<Item> itemList = getCurrentArea().getItemList().values();
        items.getChildren().clear();
        for (Item item : itemList) {
            String itemName = item.getName();
            Label itemLabel = new Label(itemName);
            if(item.isMoveable()) {
                getLabel(itemName, itemLabel);
                itemLabel.setTooltip(new Tooltip("Pro sebrani klikni levym tlacitkem mysi.\nPro prozkoumani klikni pravym tlacitkem mysi."));

                itemLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    /** Metoda slouží k nastanení sebraní a prozkoumání pomocí tlačítek myši. Pravé pro popis a levé pro sebrání*/
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
    /** Metoda slouží k vypsání východů a přiřazení obrázků.
     * Nejprve se seznam lokací vyčistí a poté se nahrají dostupné lokace podle aktuální lokace.
     * Dále se kliknutím(libovolné tlačítko myši) na daný obrázek provede příkaz jdi a hra se posune do zvolené lokace.*/

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
    /** Metoda slouží pro zobrazení inventáře. Metoda je dosti podnobná metodě updateItems. Nejpre se inventář vyčistí.
     * Poté se pomocí cyklu zjistí, které předměty jsou vinventáří a vypíší se s obrázkem do labelu. Dále se nastaví tooltip.
     * Nakonec se nastaví kliknutí myši. Pokud klikneme na předmět levým tlačítkem myši, předmět položíme. Pokud pravým, vypíše se popis předmětu.*/

    private void updateInventory() {
        Inventory inv = game.getInventory();
        Collection<Item> inventoryItems = inv.getInventory().values();
        inventory.getChildren().clear();

        for (Item item : inventoryItems) {
            String itemName = item.getName();
            Label itemLabel = new Label(itemName);

            itemLabel.setTooltip(new Tooltip("Pro položení klikni levym tlacitkem mysi.\nPro prozkoumani klikni pravym tlacitkem mysi."));

            getLabel(itemName, itemLabel);

            itemLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                /** Metoda slouží k nastavení funkčnosti tlačítek na myši.*/
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
    /** Metoda zjišťuje, jestli je konec hry a jestli hráč prohrál. Pokud hráč prohrál, vypíše se epilog že hráč prohlál a změní se pozadí.
     * Dále se zneviditelní levá a pravá strana (Předměty, Inventář, Východy a Osoby).*/
    private void updateGameOver() {
        if (game.isGameOver()) {
            if(game.getGamePlan().isDefeated()){
                InputStream stream = getClass().getClassLoader().getResourceAsStream("prohral.jpg");
                Image img = new Image(stream);
                background.setImage(img);
                background.fitHeightProperty().bind(stackpane.heightProperty());
                background.fitWidthProperty().bind(stackpane.widthProperty());
                textOutput.appendText(game.getEpilogue());
                leftBox.setVisible(false);
                rightBox.setVisible(false);
            }
        }
    }
    /** Metoda slouži k provedení příkazu. Tato metoda je několikrát volána. Nakonec se výsledek(co se stalo) do textOutputu.*/
    private void executeCommand(String command) {
        String result = game.processCommand(command);
        textOutput.appendText("\n" + result + "\n");
        update();
    }
    /** Tato metoda se volá, pokud někde chceme přidat obrázek s předem nastavenými hodnotami(výška, šířka, nastavení obrázku a nastavení kurzoru)
     * Tato metoda je několikrát volána a používá se v jiných metodách kvůli přehlednosti.*/
    private void getLabel(String name, Label label) {
        label.setCursor(Cursor.HAND);
        InputStream stream = getClass().getClassLoader().getResourceAsStream(name + ".jpg");
        Image img = new Image(stream);
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(100);
        imageView.setFitHeight(70);
        label.setGraphic(imageView);
    }
    /** Tato metoda se několikrát volá a slouží pro přehlednost a ušetření času. Metoda vrací z herního plánu aktuální lokaci */
    private Area getCurrentArea() {

        return game.getGamePlan().getCurrentArea();
    }
    /** Metodda slouží pro psaní příkazů v textovém poli. Pokud stiskneme ENTER, provede se příkaz, který byl napsán. Poprovedení se pole vyčistí.*/
    public void onInputKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
           executeCommand(textInput.getText());
            textInput.setText("");
        }
    }
    /** Tato medota slouží pro zobrazení nápovědy. Po kliknutí na talčítko v nástrojové liště nebo v menu s nápisem Nápověda se zobrazí nápověda.
     * Metoda zde nastavuje, jaký soubor se otevře a jaké parametry bude mít okno. Okno bude mít nadpis Nápověda*/
    public void help(ActionEvent actionEvent) {
        Stage primaryStage = new Stage();
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load(getClass().getResource("/help.html").toString());
        Scene scene = new Scene(webView,600,600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Nápověda");
        primaryStage.show();
    }
    /** Metoda slouží pro ukončení hry. Po kliknutí na talčítko v nástrojové liště nebo v menu s nápisem Konec hry metoda ukončí hru*/
    public void quit(ActionEvent actionEvent) {
        Platform.exit();
    }
    /** Metoda slouží pro zapnutí nové hry. Po kliknutí na talčítko v nástrojové liště nebo v menu s nápisem Nová hra začne nová hra.
     * Metoda nastavuje a spouští novou hru. Pokud hráč prohrál a chce si zahrát znovu hru, je potřeba nastavi viditelnost levé a pravé strany stackpane*/
    public void restart(ActionEvent actionEvent) {
        game = new Game();
        leftBox.setVisible(true);
        rightBox.setVisible(true);
        init(game);
    }
}
