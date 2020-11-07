package cz.vse.beyr.model;

import java.util.*;

/**
 * Třída představuje herní logiku pro ukázání předmětů v inventáři.
 */
public class CommandShowInventory implements ICommand
{
    private static final String NAME = "inventar";

    private Inventory inventory; 

    /**
     * Konstruktor třídy.
     * @param inventory odkaz na inventář s aktuálními předměty
     */
    public CommandShowInventory(Inventory inventory)
    {
        this.inventory = inventory;
    }

    /**
     * Tato metoda volá metodu getInventory. Pomocí této metody se pole 'inventory' projede a každý item, který je v inventáři se zapíše 
     * do proměnné itemNames která se poté vypíše do konzole.
     * 
     * @return název předmětu
     */
    @Override
    public String process(String... parameters)
    {
        String itemNames = "Předměty v inventáři";
        for (String itemName : inventory.getInventory().keySet()) {
            itemNames += " " + itemName;
        }

        return itemNames;
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