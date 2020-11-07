package cz.vse.beyr.model;

/**
 * Hlavní třída logiky aplikace. Třída vytváří instanci třídy {@link GamePlan},
 * která inicializuje lokace hry, a vytváří seznam platných příkazů a instance
 * tříd provádějících jednotlivé příkazy.
 *
 * Během hry třída vypisuje uvítací a ukončovací texty a vyhodnocuje jednotlivé
 * příkazy zadané uživatelem.
 *
 * @author Michael Kölling
 * @author Luboš Pavlíček
 * @author Jarmila Pavlíčková
 * @author Jan Říha
 * @author Jakub Beyr
 * @version LS 2020
 */
public class Game implements IGame
{
    private ListOfCommands listOfCommands;
    private GamePlan gamePlan;
    private boolean gameOver;
    public Inventory inventory;

    /**
     * Konstruktor třídy. Vytvoří hru, inicializuje herní plán udržující
     * aktuální stav hry a seznam platných příkazů.
     */
    public Game()
    {
        
        gameOver = false;
        gamePlan = new GamePlan();
        listOfCommands = new ListOfCommands();
        this.inventory = new Inventory(gamePlan);
        
        listOfCommands.addCommand(new CommandHelp(listOfCommands));
        listOfCommands.addCommand(new CommandTerminate(this));
        listOfCommands.addCommand(new CommandMove(gamePlan, this.inventory));
        listOfCommands.addCommand(new CommandPick(gamePlan, this.inventory));
        listOfCommands.addCommand(new CommandInspect(gamePlan, this.inventory));
        listOfCommands.addCommand(new CommandTalk(gamePlan));
        listOfCommands.addCommand(new CommandKill(gamePlan, this.inventory));
        listOfCommands.addCommand(new CommandSupport());
        listOfCommands.addCommand(new CommandSearch(gamePlan));
        listOfCommands.addCommand(new CommandShowInventory(this.inventory));
        listOfCommands.addCommand(new CommandRemoveItem(this.inventory, gamePlan));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPrologue()
    {
        return "Vítejte!\n"
                + "Toto je příběh o vyplutí Ragnara na západ a objevení Anglie.\n"
                + "Napište 'napoveda', pokud si nevíte rady, jak hrát dál.\n"
                + "\n"
                + gamePlan.getCurrentArea().getFullDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEpilogue()
    {
        String epilogue = "Díky, že sis zahrál(a).";

        if (gamePlan.isVictorious()) {
            epilogue = "ZVÍTĚZIL(A) JSI !\n\n" + epilogue;
        }
        
        if (gamePlan.isDefeated()) {
            epilogue = "PROHRÁL(A) JSI !\n\n" + epilogue;
        }
        return epilogue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGameOver()
    {
        return gameOver;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String processCommand(String line)
    {
        String[] words = line.split("[ \t]+");

        String cmdName = words[0];
        String[] cmdParameters = new String[words.length - 1];

        for (int i = 0; i < cmdParameters.length; i++) {
            cmdParameters[i] = words[i + 1];
        }

        String result = null;
        if (listOfCommands.checkCommand(cmdName)) {
            ICommand command = listOfCommands.getCommand(cmdName);
            result = command.process(cmdParameters);
        } else {
            result = "Nechápu, co po mně chceš. Tento příkaz neznám.";
        }

        if (gamePlan.isVictorious()) {
            gameOver = true;
        }
        
        if (gamePlan.isDefeated()) {
            gameOver = true;
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GamePlan getGamePlan()
    {
        return gamePlan;
    }

    /**
     * Metoda nastaví příznak indikující, že nastal konec hry. Metodu
     * využívá třída {@link CommandTerminate}, mohou ji ale použít
     * i další implementace rozhraní {@link ICommand}.
     *
     * @param gameOver příznak indikující, zda hra již skončila
     */
    void setGameOver(boolean gameOver)
    {
        this.gameOver = gameOver;
    }

}
