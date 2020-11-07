package cz.vse.beyr.model;
/**
 * Třída představuje implementaci osob (metady, parametry)  
 * 
 * @author Jakub Beyr
 * @version LS 2020
 */
public class Person
{
    private String name;
    private String speech;
    private boolean mortal;
    /**
     * Metoda implementuje parametry 'name' (jméno osoby), 'speech' (monolog osoby), 
     * 'mortal' (zda li je možné osobu zabít true/false)
     * @param name jméno osoby
     * @param speech prolog osoby
     * @param mortal smrtelnost osoby
     */
    public Person(String name, String speech, boolean mortal)
    {
        this.name = name;
        this.speech = speech;
        this.mortal = mortal;
    }

    /**
     * Metoda nastavuje parametr 'mortal'  na false, aby se nemusel pokaždě zadávat při 
     * vytváření každé osoby, protože většina osob nejde zabít.
     * @param name jméno osoby
     * @param speech prolog osoby
     */
    public Person(String name, String speech)
    {
        this(name, speech, false);
    }

    /**
     * Metoda vrací jméno předmětu
     * @return název osoby
     */
    public String getName()
    {
        return name;
    }

    /**
     * Metoda vrací monolog osoby
     *  @return prolog osoby
     */
    public String getSpeech()
    {
        return speech;
    }

    /**
     * Metoda nastavuje monolog osoby
     * @param speech prolog osoby
     */
    public void setSpeech(String speech)
    {
        this.speech = speech;
    }

    /**
     * Metoda vrací, zda-li lze osobu zabít
     * @return smrtelnost osoby
     */
    public boolean isMortal()
    {
        return mortal;
    }

    /**
     * Metoda nastavuje, zda-li lze osobu zabít
     * @param mortal smrtelnost osoby
     */
    public void setMortal(boolean mortal)
    {
        this.mortal = mortal;
    }

}
