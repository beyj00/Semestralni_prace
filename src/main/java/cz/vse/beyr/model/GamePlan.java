package cz.vse.beyr.model;

/**
 * Třída představující aktuální stav hry. Veškeré informace o stavu hry
 * <i>(mapa lokací, inventář, vlastnosti hlavní postavy, informace o plnění
 * úkolů apod.)</i> by měly být uložené zde v podobě datových atributů.
 * <p>
 * Třída existuje především pro usnadnění potenciální implementace ukládání
 * a načítání hry. Pro uložení rozehrané hry do souboru by mělo stačit uložit
 * údaje z objektu této třídy <i>(např. pomocí serializace objektu)</i>. Pro
 * načtení uložené hry ze souboru by mělo stačit vytvořit objekt této třídy
 * a vhodným způsobem ho předat instanci třídy {@link Game}.
 *
 * @author Michael Kölling
 * @author Luboš Pavlíček
 * @author Jarmila Pavlíčková
 * @author Jan Říha
 * @author Jakub Beyr
 * @version LS 2020
 *
 * @see <a href="https://java.vse.cz/4it101/AdvSoubory">Postup pro implementaci ukládání a načítání hry na předmětové wiki</a>
 * @see java.io.Serializable
 */
public class GamePlan
{
    private static final String FINAL_LOCATION_NAME = "anglie";
    private boolean isLose;
    
    private Area currentArea;
    
    /**
     * Konstruktor třídy. Pomocí metody {@link #prepareWorldMap() prepareWorldMap}
     * vytvoří jednotlivé lokace a propojí je pomocí východů.
     */
    public GamePlan()
    {
        prepareWorldMap();
    }

    /**
     * Metoda vytváří jednotlivé lokace a propojuje je pomocí východů. Jako
     * výchozí aktuální lokaci následně nastaví farma, kde žije Ragnar.
     * Dále přidáme objekty a postavy do lokací.
     */
    private void prepareWorldMap()
    {
        // Vytvoříme jednotlivé lokace
        Area farma = new Area("farma","Zde žije Ragnar se svou rodinou.");
        Area anglie = new Area(FINAL_LOCATION_NAME, "Úspěšně jsi doplul do Anglie a zahajuješ nájezdy do přilehlých osad.");
        Area lodenice = new Area("lodenice","Zde Floki vyrábí lodě.");
        Area flokihoDum = new Area("flokiho_dum","Ocitl ses uprostřed lesů, kde žije Floki se svou družkou Helgou.");
        Area kattegat = new Area("kattegat","Ocitl jsi se na náměstí Kattegatu.");
        Area trh = new Area("trh", "Všude okolo je spoustu lidí a stánků.");
        Area pristav = new Area("pristav", "Jsi na molu, kde městské lodě hlídají stráže.");

        // Nastavíme průchody mezi lokacemi (sousední lokace)
        farma.addExit(lodenice);
        farma.addExit(flokihoDum);
        farma.addExit(kattegat);
        lodenice.addExit(farma);
        flokihoDum.addExit(farma);
        kattegat.addExit(farma);

        kattegat.addExit(flokihoDum);
        kattegat.addExit(trh);
        kattegat.addExit(pristav);
        trh.addExit(kattegat);
        flokihoDum.addExit(kattegat);
        pristav.addExit(kattegat);
        
       
        flokihoDum.addExit(lodenice);
        lodenice.addExit(anglie);
        lodenice.addExit(flokihoDum);

        pristav.addExit(anglie);
        
        
        // vytvoříme a nastavíme předměty
        Item povoz = new Item("povoz", "Dřevěný povoz pro převoz sena.", false, false);
        Item lopata = new Item("lopata", "Železná lopata s naprasklou násadou.");
        Item sekera = new Item("sekera", "Válečná sekera.", true, true);
        Item lod = new Item("lod", "Nová loď kterou ses s Flokim rozhodl postavit. Pro její dokončení je potřeba kotva.", false, false);
        Item chleb = new Item("chleb", "Čerstvě upečený chléb.");
        Item pivo = new Item("pivo", "Tmavé a velmi silné pivo.");
        Item stul = new Item("stul", "masivní stůl z dubového dřeva.", false, false);
        Item stanek = new Item("stanek", "malý stánek s pečivem.", false, false);
        Item flotila = new Item("flotila", "deset lodí připravených na drancovální a nájezdy.");
        Item svicen = new Item("svicen", "Zlatý svícen, určitě by se dal za něco vyměnit.");
        Item sin = new Item("sin", "Příbytek jarla Kattegatu. Konají se zde veškerá zasedání a oslavy.", false, false);
        Item mec = new Item("mec", "Starý železný meč.", true, true);
        
        
        
        // Přidáme předměty do lokací
        farma.addItem(povoz);
        farma.addItem(lopata);
        flokihoDum.addItem(pivo);
        flokihoDum.addItem(stul);
        lodenice.addItem(sekera);
        lodenice.addItem(lod);
        trh.addItem(chleb);
        trh.addItem(stanek);
        pristav.addItem(flotila);
        farma.addItem(svicen);
        kattegat.addItem(sin);
        farma.addItem(mec);
        
        
        //vytvoříme a nastavíme postavy
        Person floki = new Person("Floki", "Zdravím tě Ragnare, loď už je skoro hotová. Až mi přineseš kotvu, můžeme hned vyplout na západ.");
        Person lagherta = new Person("Lagherta", "Měl by jsi zabít Haraldsona, pokud se vrátíš ze západu, budou s ním velké problémy.");
        Person jarl_haraldson = new Person("jarl_Haraldson", "Ragnar Lothbruk, vím že máš něco v plánu. Dávej si na mě pozor.", true);
        Person helga = new Person("Helga", "Ahoj Ragnare, pokud hledáš Flokiho, tak je v loděnici.");
        Person kovar = new Person("kovar", "Mám pro tebe tu kotvu. Polož mi sem nějaké zlato a můžeš si ji vzít.");
        
        //Přidáme postavy do lokací
        flokihoDum.addPerson(helga);
        farma.addPerson(lagherta);
        lodenice.addPerson(floki);
        kattegat.addPerson(jarl_haraldson);
        trh.addPerson(kovar);
        
        

        // Hru začneme na farmě
        currentArea = farma;
    }

    /**
     * Metoda vrací odkaz na aktuální lokaci, ve které se hráč právě nachází.
     *
     * @return aktuální lokace
     */
    public Area getCurrentArea()
    {
        return currentArea;
    }

    /**
     * Metoda nastaví aktuální lokaci, používá ji příkaz {@link CommandMove}
     * při přechodu mezi lokacemi.
     *
     * @param area lokace, která bude nastavena jako aktuální
     */
    public void setCurrentArea(Area area)
    {
        currentArea = area;
    }
    
    /**
     * Metoda slouži pro zjištění, jestli hráč vyhrál či nikoli.
     * 
     * @return jestli se hráč nachází ve finální lokaci
     */
    public boolean isVictorious()
    {
        return FINAL_LOCATION_NAME.equals(currentArea.getName());
    }
    
    /**
     * Metoda slouži pro zjištění, jestli hráč prohrál či nikoli.
     * 
     * @return jestli hráč prohrál
     */
    public boolean isDefeated()
    {
        return isLose;
    }
    
    public void setLose(boolean isLose)
    {
        this.isLose = isLose;
    }
    
    public boolean getLose()
    {
        return isLose;
    }
    
    
    
}
