
package cz.vse.beyr.model;
/**
 * Třída představuje herní logiku zabití postavy. Nejdříve se ověří, zda-li je správně zadané jméno osoby.
 * Poté se kontroluje, zda-li je možné postavu zabít. Nastavenou smrtelnost má pouze jedna postava. 
 * Pokud se hráč pokusí tuto postavu zabít a nemá v inventáři předmět 'sekera' je zabit a hra končí.
 * Pokud hráš předmět 'sekera' má, postavu zabije, tím postava zmizí z lokace a do lokace se načte předmět 'koruna'.
 * 
 * @author Jakub Beyr
 * version LS 2020
 */
public class CommandKill implements ICommand
{
    private static final String NAME = "zabij";

    private GamePlan plan;
    private GamePlan isLose;
    private Inventory inventory;
    /**
     * Konstruktor třídy.
     *
     * @param plan odkaz na herní plán s aktuálním stavem hry
     * @param inventory odkaz na inventář s aktuálními předměty
     */
    public CommandKill(GamePlan plan, Inventory inventory)
    {
        this.plan = plan;
        this.inventory = inventory;
        this.isLose = isLose;

    }

    /**
     *  Tato metoda zjistí, jestli jdou zadány správné parametry osob. Poté, co jsou zjištěný správné parametry, metoda zjistí, jestli je možné
     *  danou postavu zabít. Pokud je zbraň 'sekera' v inventáři, bude zabita postava Jarl_Haraldson a upustí předmět 'koruna'. Pokud zbraň 'sekera' 
     *  v inventáři není. Hráč je zabit a prohrál hru. Hra se ukončí.
     *  @return zda-li lze tuto osobu zabít a jestli hráč osobu zabil či byl zabit
     */

    @Override
    public String process(String... parameters)
    {
        if (parameters.length == 0) {
            return "Nevím, koho zabít, musíš zadat název osoby.";
        }

        if (parameters.length > 1) {
            return "Tomu nerozumím, neumím zabít více osob současně.";
        }

        String personName = parameters[0];
        Area area = plan.getCurrentArea();

        if (!area.containsPerson(personName)) {
            return "Osoba '" + personName + "' tady není.";
        }

        Person person = area.getPerson(personName);

        if (!person.isMortal()) {
            return "Osobu '" + personName + "' nemůžeš zabít.";
        }

        if (inventory.isWeaponInInventory())
        {
            area.removePerson(personName);
            Item koruna = new Item("koruna", "S touto korunou se stáváš jarlem");
            plan.getCurrentArea().addItem(koruna);
            return "Po dlouhém a únavném boji zabíjíš jarla Haraldsona. Můžeš sebrat korunu a stát se jarlem Kattegatu";
        }

        if (!inventory.isWeaponInInventory())
        {
            this.isLose = isLose;
        }

        return "Byl jsi zabit při souboji!";
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

