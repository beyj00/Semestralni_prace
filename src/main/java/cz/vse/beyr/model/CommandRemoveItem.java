package cz.vse.beyr.model;

import java.util.*;

/**
 * Třída představuje herní logiku pro odstranění předmětu z inventáře a umístění předmětu do aktuální lokace.
 */
public class CommandRemoveItem implements ICommand
{
    private static final String NAME = "poloz";

    private Inventory inventory; 
    private GamePlan plan;

    /**
     * Konstruktor třídy.
     *
     * @param plan odkaz na herní plán s aktuálním stavem hry
     * @param inventory odkaz na inventář s aktuálními předměty
     */
    public CommandRemoveItem(Inventory inventory, GamePlan plan)
    {
        this.inventory = inventory;
        this.plan = plan;
    }

    /**
     * Tato metoda zkontroluje, zda-li byl zadaný správný parametr. Poté se parametr uloží do proměnné itemName a zjistí se, v jaké lokaci se nacházíme. 
     * Poté se zjišťuje, jestli daný "úkolový" předmět máme v inventáři a pokladame ho v požadovane lokaci. Pokud je toto splněno, předmět se vymaže a 
     * vytvoří se nový předmět a nakonec se vypíše text. 
     * Pokud s enejedná o "úkolový" předmět, provede se metoda removeItem ze třídy inventory, kde jsou zajištěny potřebné podmínky.
     * 
     * @return metoda inventory,removeItem(itemName) nebo příslušný text 
     */
    @Override
    public String process(String... parameters)
    {
        if (parameters.length == 0) {
            return "Nevím, co mám položit, musíš zadat název předmětu.";
        }

        if (parameters.length > 1) {
            return "Tomu nerozumím, neumím položit více předmětů současně.";
        }

        String itemName = parameters[0];
        Area area = plan.getCurrentArea();

        if (inventory.isInInventory("svicen") && area.containsPerson("kovar")) {
            inventory.removeItem(itemName);
            area.removeItem(itemName);
            Item kotva = new Item("kotva", "Kotva na loď");
            plan.getCurrentArea().addItem(kotva);
            return "Zaplatil jsi kováři kotvu, kterou sis u něj objednal. Nyní si ji můžeš sebrat.\n";// + inventory.removeItem(itemName);
        }

        return inventory.removeItem(itemName);
    }

    /**
     * Metoda vrací název příkazu tj.&nbsp; slovo {@value NAME}.
     *
     * @return název příkazu
     */
    @Override
    public String getName()
    {
        return NAME;
    }
}