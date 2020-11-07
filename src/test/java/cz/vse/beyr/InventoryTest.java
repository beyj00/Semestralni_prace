
package cz.vse.beyr;

import cz.vse.beyr.model.Game;
import cz.vse.beyr.model.Inventory;
import cz.vse.beyr.model.Item;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída {@code InventoryTest} slouží ke komplexnímu otestování
 * třídy {@link InventoryTest}.
 *
 * @author  Jakub Beyr
 * @version LS2020
 */
public class InventoryTest
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

    @Test
    public void testAddToInventory() 
    {
        Inventory inventory = new Inventory(game.getGamePlan());

        Item pivo = new Item("pivo", "Tmavé a velmi silné pivo.");
        Item lopata = new Item("lopata", "Železná lopata s naprasklou násadou.");
        Item sekera = new Item("sekera", "Válečná sekera.");

        assertTrue(inventory.addToInventory(pivo.getName(), pivo));
        assertTrue(inventory.addToInventory(lopata.getName(), lopata));
        assertFalse(inventory.addToInventory(sekera.getName(), sekera));

        assertTrue(inventory.isInInventory(pivo.getName()));
        assertTrue(inventory.isInInventory(lopata.getName()));
        assertFalse(inventory.isInInventory(sekera.getName()));
    }

    @Test
    public void testRemoveItem()
    {
        Inventory inventory = new Inventory(game.getGamePlan());

        Item pivo = new Item("pivo", "Tmavé a velmi silné pivo.");

        inventory.addToInventory(pivo.getName(), pivo);

        assertTrue(inventory.isInInventory(pivo.getName()));
        assertFalse(game.getGamePlan().getCurrentArea().containsItem(pivo.getName()));

        inventory.removeItem(pivo.getName());

        assertFalse(inventory.isInInventory(pivo.getName()));
        assertTrue(game.getGamePlan().getCurrentArea().containsItem(pivo.getName()));

    }

    @Test
    public void testIsInInventory() 
    {
        Inventory inventory = new Inventory(game.getGamePlan());

        Item pivo = new Item("pivo", "Tmavé a velmi silné pivo.");
        Item sekera = new Item("sekera", "Válečná sekera.");

        inventory.addToInventory(pivo.getName(), pivo);

        assertTrue(inventory.isInInventory(pivo.getName()));
        assertFalse(inventory.isInInventory(sekera.getName()));
    }

}
