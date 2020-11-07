package cz.vse.beyr.model;

/**
 * Třída implementující příkaz pro zobrazení dostupných příkazů ke hře.
 *
 * @author Jarmila Pavlíčková
 * @author Luboš Pavlíček
 * @author Jan Říha
 * @version LS 2020
 */
public class CommandHelp implements ICommand
{
    private static final String NAME = "pomoc";

    private ListOfCommands listOfCommands;

    /**
     * Konstruktor třídy.
     *
     * @param listOfCommands odkaz na seznam příkazů, které je možné ve hře použít
     */
    public CommandHelp(ListOfCommands listOfCommands)
    {
        this.listOfCommands = listOfCommands;
    }

    /**
     * Metoda vypisuje vcelku primitivní
     * zprávu o seznamu dostupných příkazů, které může hráč
     * používat.
     *
     * @param parameters parametry příkazu <i>(aktuálně se ignorují)</i>
     * @return nápověda, která se vypíše na konzoli
     */
    @Override
    public String process(String... parameters)
    {
        return "Ve hře můžeš používat tyto příkazy:\n"
        + listOfCommands.getNames();
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
