package cz.vse.beyr.model;

/**
 * Třída implementující příkaz pro pohyb mezi herními lokacemi.
 *
 * @author Jarmila Pavlíčková
 * @author Luboš Pavlíček
 * @author Jan Říha
 * @author Jakub Beyr
 * @version LS 2020
 */
public class CommandMove implements ICommand
{
    private static final String NAME = "jdi";

    private GamePlan plan;
    private Inventory inventory;

    /**
     * Konstruktor třídy.
     *
     * @param plan odkaz na herní plán s aktuálním stavem hry
     * @param inventory odkaz na inventář
     */
    public CommandMove(GamePlan plan, Inventory inventory)
    {
        this.plan = plan;
        this.inventory = inventory;
    }

    /**
     * Metoda se pokusí přesunout hráče do jiné lokace. Nejprve zkontroluje počet
     * parametrů. Pokud nebyl zadán žádný parametr <i>(tj. neznáme cíl cesty)</i>,
     * nebo bylo zadáno dva a více parametrů <i>(tj. hráč chce jít na více míst
     * současně)</i>, vrátí chybové hlášení. Pokud byl zadán právě jeden parametr,
     * zkontroluje, zda s aktuální lokací sousedí jiná lokace s daným názvem <i>(tj.
     * zda z aktuální lokace lze jít do požadovaného cíle)</i>. Pokud ne, vrátí
     * chybové hlášení. Dále se zkontroluje, pokud hráč chce jít do cílové lokace,
     * tak zda-li má v inventáři požadovaný předmět
     * Pokud všechny kontroly proběhnou v pořádku, provede přesun
     * hráče do cílové lokace a vrátí její popis.
     *
     * @param parameters parametry příkazu <i>(očekává se pole s jedním prvkem)</i>
     * @return informace pro hráče, které se vypíšou na konzoli
     */
    @Override
    public String process(String... parameters)
    {

        if (parameters.length == 0) {
            return "Nechápu, kam mám jít. Musíš mi zadat nějaký cíl.";  // Pokud hráč nezadá žádný parametr (cíl cesty)
        } else if (parameters.length > 1) {
            return "Nechápu, co po mě chceš. Neumím se 'rozdvojit' a jít na více míst současně.";  // Pokud hráč zadá více parametrů
        }

        // Název cílové lokace si uložíme do pomocné proměnné
        String exitName = parameters[0];

        // Zkusíme získat odkaz na cílovou lokaci
        Area exitArea = plan.getCurrentArea().getExitArea(exitName);

        if (exitArea == null) {
            return "Tam se ale odsud jít nedá!";  // Pokud hráč zadal název lokace, která nesousedí s aktuální
        }

        if (plan.getCurrentArea().getName().equals("pristav")) {
            if (exitArea.getName().equals("anglie") && !inventory.isInInventory("koruna")) {
                return "Momentálně nemáš přístup do této lokace";
            }
            plan.setCurrentArea(exitArea);
            return exitArea.getFullDescription();
        }

        if (plan.getCurrentArea().getName().equals("lodenice")) {
            if (exitArea.getName().equals("anglie") && !inventory.isInInventory("kotva")) {
                return "Momentálně nemáš přístup do této lokace";
            }
            plan.setCurrentArea(exitArea);
            return exitArea.getFullDescription(); 
        }
        // Změníme aktuální lokaci (přesuneme hráče) a vrátíme popis nové lokace
        plan.setCurrentArea(exitArea);
        return exitArea.getFullDescription();
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
