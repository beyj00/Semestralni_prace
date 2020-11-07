package cz.vse.beyr.model;

/**
 * Třída představuje implementaci osob (metady, parametry)  
 * 
 * @author Jakub Beyr
 * @version LS 2020
 */
public class Item
{
    private String name;
    private String description;
    private boolean moveable;
    private boolean weapon;
    /**
     * Metoda implementuje parametry 'name' (název předmětu), 'description' (stručný popis předmětu), 
     * 'moveable' (zda li je možné předmět sebrat true/false)
     * 
     * @param name název předmětu
     * @param description popis předmětu
     * @param moveable zda-li je možné předmět sebrat
     */
    public Item(String name, String description, boolean moveable, boolean weapon)
    {
        this.name = name;
        this.description = description;
        this.moveable = moveable;
        this.weapon = weapon;
    }

    /**
     * Metoda nastavuje parametr 'description'  na true, aby se nemusel pokaždě zadávat při 
     * vytváření předmětu, pokud není hodnota false.
     * 
     * @param name název předmětu
     * @param description popis předmětu
     */
    public Item(String name, String description)
    {
        this(name, description, true, false);
    } 

    /**
     * Metoda vrací jméno předmětu
     * 
     * @return název předmětu
     */
    public String getName()
    {
        return name;
    }

    /**
     * Metoda vrací popis předmětu
     * 
     * @return popis předmětu
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Metoda nastavuje popis předmětu
     * 
     * @param description popis předmětu
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Metoda vrací pohyblivost předmětu
     * 
     * @return moveable zda-li je možné předmět sebrat
     */
    public boolean isMoveable()
    {
        return moveable;
    }

    /**
     * Metoda nastavuje pohyblivost předmětu
     * 
     * @param moveable zda-li je možné předmět sebrat
     */
    public void setMoveable(boolean moveable)
    {
        this.moveable = moveable;
    }
    
    public boolean isWeapon()
    {
        return weapon;
    }
    
    public void setWeapon(boolean weapon)
    {
        this.weapon = weapon;
    }
    
    public boolean getWeapon()
    {
        return weapon;
    }

}
