package cz.vse.beyr.model;
import java.util.*;

/**
 * Tato třída má na starost herní logiku prohledání a vypsání předmětů v aktuální lokaci.
 */
public class CommandSearch implements ICommand
{
    private static final String NAME = "prohledej";

    private GamePlan plan;

    /**
     * Konstruktor třídy.
     * @param plan odkaz na herní plán s aktuálním stavem hry
     * 
     */
    public CommandSearch(GamePlan plan)
    {
        this.plan = plan;
    }

    /**
     * Metoda provadí cyklus, ve kterém se zjišťuje přítomnost předmětu v aktuální lokaci.
     * Nejprve se vytvoří textový řetězec itemNames s hodnotou "Předměty"
     * Poté se provádí cyklus, který projíždí předměty v aktuální lokaci, název předmětu se přepíše
     * do Stringu itemName spolešně s mezerou (" ") a tento řetězec se přidá do itemNames.
     * Nakonec, když už v lokaci nejsou žádné další předměty, cyklus se ukončí a vypíše se obsah itemNames.
     * 
     * @return vrací předměty v aktuální lokaci
     */
    public String process(String... parameters)
    {
        String itemNames = "Předměty:";
        for (String itemName : plan.getCurrentArea().getItems().keySet()) {
            itemNames += " " + itemName;
        }
        return itemNames;
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