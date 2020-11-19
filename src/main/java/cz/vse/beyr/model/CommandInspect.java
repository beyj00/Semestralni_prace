package cz.vse.beyr.model;

import java.util.*;

/**
 * Tato třída představuje herní logiku pro prozkoumání předmětu v aktuální lokaci nebo v inventáři.
 */
public class CommandInspect implements ICommand
{
    private static final String NAME = "prozkoumej";
    
    private GamePlan plan;
    private Inventory inventory;
    
    /**
     * Konstruktor třídy.
     *
     * @param plan odkaz na herní plán s aktuálním stavem hry
     * @param inventory odkaz na inventář s aktuálními předměty
     */
    public CommandInspect(GamePlan plan, Inventory inventory)
    {
        this.plan = plan;
        this.inventory = inventory;
    }
    /**
     * Metoda nejprve zjišťuje správnost parametru, poté, zda-li požadovaný předmět je v aktuální lokaci nebo v inventáři.
     * Pokud je daný předmět v inventáři nebo v aktuální lokaci, metoda vrátí popis předmětu (ggetDescription).
     * 
     * @return vrací popis zkoumaného předmětu
     */
    public String process(String... parameters)
    {
        if (parameters.length == 0) {
            return "Nevím, co mám prozkoumat, musíš zadat název předmětu.";
        }
        
        if (parameters.length > 1) {
            return "Tomu nerozumím, neumím prozkoumat více předmětů současně.";
        }
        
        String itemName = parameters[0];
        Area area = plan.getCurrentArea();
         
        if (!area.containsItem(itemName) && !inventory.isInInventory(itemName)) {
            return "Předmět '" + itemName + "' tady není.";
        }
       
        
         if (inventory.isInInventory(itemName)){
            return inventory.getItem(itemName).getDescription();
            }

        return area.getItem(itemName).getDescription();
    }

    /**
     * Metoda vrací název příkazu tj.&nbsp; slovo {@value NAME}.
     *
     * @return název příkazu
     */
    public String getName()
    {
        return NAME;
    }
}
