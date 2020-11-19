package cz.vse.beyr.model;

/**
 * Třída prezentuje rozhovor s postavou. Každá postava má přiřazen monolog, který ve vypíše.
 * Kontroluje se, zda byl zadaný právě jeden a správný parametr.
 * 
 * @author Jakub Beyr
 * @version LS 2020
 */
public class CommandTalk implements ICommand
{
    private static final String NAME = "promluv";
    
    private GamePlan plan;
    /**
     * Konstruktor třídy.
     *
     * @param plan odkaz na herní plán s aktuálním stavem hry
     */
    public CommandTalk(GamePlan plan)
    {
        this.plan = plan;
    }
    
    
    
    public String process(String... parameters)
    {
        if (parameters.length == 0) {
            return "Nevím, na koho mám promluvit, musíš zadat název osoby.";
        }
        
        if (parameters.length > 1) {
            return "Tomu nerozumím, neumím promluvit na více osob současně.";
        }
        
        String personName = parameters[0];
        Area area = plan.getCurrentArea();
                
        if (!area.containsPerson(personName)) {
            return "Osoba '" + personName + "' tady není.";
        }

        return area.getPerson(personName).getSpeech(); 
        
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
