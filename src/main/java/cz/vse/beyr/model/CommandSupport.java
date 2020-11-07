package cz.vse.beyr.model;

/**
 * Třída implementující příkaz pro zobrazení nápovědy ke hře.
 *
 * @author Jarmila Pavlíčková
 * @author Luboš Pavlíček
 * @author Jan Říha
 * @author Jakub Beyr
 * @version LS 2020
 */
public class CommandSupport implements ICommand
{
    private static final String NAME = "napoveda";

    /**
     * Metoda vrací obecnou nápovědu ke hře. Nyní vypisuje, co hráč musí udělat, 
     * aby mohl hru vyhrát. 
     * @param parameters parametry příkazu <i>(aktuálně se ignorují)</i>
     * @return nápověda, která se vypíše na konzoli
     */
    @Override
    public String process(String... parameters)
    {
        return "Tvým úkolem je plout na západ do Anglie.\n"
        + "Aby jsi mohl plout na západ, potřebuješ loď.\n"
        + "Loď získáš zabitím Jarla Haraldsona\n"
        + "nebo donesením kotvy Flokimu\n\n"
        + "pro zjištění příkazů použij příkaz 'pomoc'";
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
