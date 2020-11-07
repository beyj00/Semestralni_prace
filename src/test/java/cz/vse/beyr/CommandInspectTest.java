
package cz.vse.beyr;




import cz.vse.beyr.model.Area;
import cz.vse.beyr.model.Game;
import cz.vse.beyr.model.Inventory;
import cz.vse.beyr.model.Item;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;



/*******************************************************************************
 * Testovací třída {@code CommandInspectTest} slouží ke komplexnímu otestování
 * třídy {@link CommandInspectTest}.
 *
 * @author  Jakub Beyr
 * @version LS 2020
 */
public class CommandInspectTest
{
    private Game game;
    
    /***************************************************************************
     * Inicializace předcházející spuštění každého testu a připravující tzv.
     * přípravek (fixture), což je sada objektů, s nimiž budou testy pracovat.
     */
    @Before
    public void setUp()
    {
        game = new Game();
    }

    /***************************************************************************
     * Test of the {@link #setUp()} method preparing the test fixture.
     */
    @Test
    public void testCommandInspect()
    {
        Area farma = new Area("farma","Zde žije Ragnar se svou rodinou.");
        Inventory inventory = new Inventory(game.getGamePlan());
        
        Item povoz = new Item("povoz", "Dřevěný povoz pro převoz sena.", false, false);
        Item lopata = new Item("lopata", "Železná lopata s naprasklou násadou.");
        Item sekera = new Item("sekera", "Válečná sekera.");
        
        farma.addItem(povoz);
        inventory.addToInventory(lopata.getName(), lopata);
        
        game.processCommand("prozkoumej povoz");
        assertTrue(farma.containsItem(povoz.getName()));
        assertFalse(inventory.isInInventory(povoz.getName()));
        
        game.processCommand("prozkoumej lopata"); 
        assertFalse(farma.containsItem(lopata.getName()));
        assertTrue(inventory.isInInventory(lopata.getName()));
        
        game.processCommand("prozkoumej sekera"); 
        assertFalse(farma.containsItem(sekera.getName()));
        assertFalse(inventory.isInInventory(sekera.getName()));
    }

}
