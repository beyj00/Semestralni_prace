package cz.vse.beyr.model;

/**
 * Třída implementující příkaz pro sebrání předmětu a vložení do inventáře.
 * 
 * @author Jakub Beyr
 * @version LS 2020
 */
public class CommandPick implements ICommand
{
    private static final String NAME = "seber";
    private Inventory inventory;    
    private GamePlan plan;

    /**
     * Konstruktor třídy.
     * @param inventory inventář
     * @param plan odkaz na herní plán s aktuálním stavem hry
     */
    public CommandPick(GamePlan plan, Inventory inventory)
    {
        this.plan = plan;
        this.inventory = inventory;
    }

    /**
     * Metoda nejdříve zjistí jestli je zadaný pouze jeden parametr. Poté se zjistí, zadaný předmět je v aktuální lokaci.
     * Pokud je zadaný předmět v aktuální lokaci, ověří se, jesstli je možné předmět sebrat. Nakonec se ověřuje, jestli se 
     * předmět přidal do inventáře, pokud se přidal do inventáře (deleteItem zůstane true), znamená to, že je v inventáři místo a předmět se odstraní
     * z aktuální lokace,pokud se do inventáře nepřidá (deleteItem se nastaví na false), znamená to, že inventář je plný.
     * 
     * @return vrací text, jestli a jaký předmět byl sebrán
     */
    @Override
    public String process(String... parameters)
    {

        if (parameters.length == 0) {
            return "Nevím, co mám sebrat, musíš zadat název předmětu.";
        }

        if (parameters.length > 1) {
            return "Tomu nerozumím, neumím sebrat více předmětů současně.";
        }

        String itemName = parameters[0];
        Area area = plan.getCurrentArea();

        if (!area.containsItem(itemName)) {
            return "Předmět '" + itemName + "' tady není.";
        }

        Item item = area.getItem(itemName);

        if (!item.isMoveable()) {
            return "Předmět '" + itemName + "' fakt neuneseš.";
        }

        boolean deleteItem = true; //vzdy se item smaze, pokud neni dano jinak
        deleteItem = inventory.addToInventory(itemName, item); //bud prida item do invetare a vrati true a tim padem se item smaze, nebo vrati false, item se nesmaze a neprida se do inventare

        if(deleteItem){//kdyz je deleteItem true, tak se item smaze
            area.removeItem(itemName);
            return "Sebral(a) jsi předmět '" + itemName + "' a uložil jsi ho do inventáře.";
        }

        return "Mas plny inventar";

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
