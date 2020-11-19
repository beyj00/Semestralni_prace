package cz.vse.beyr;

import cz.vse.beyr.model.Game;
import cz.vse.beyr.model.GamePlan;
import cz.vse.beyr.model.Inventory;
import cz.vse.beyr.model.Item;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída {@code CommandRemoveItemTest} slouží ke komplexnímu otestování
 * třídy {@link CommandRemoveItemTest}.
 *
 * @author  Jakub Beyr
 * @version LS 2020
 */
public class CommandRemoveItemTest
{
    GamePlan plan;
    private Game game;

    @Before
    public void setUp()
    {
        game = new Game();
        this.plan = game.getGamePlan();
    }

    @Test
    public void testCommandRemoveItem() {
        Inventory inventory = new Inventory(game.getGamePlan());

        Item sekera = new Item("sekera", "Válečná sekera.");
        Item svicen = new Item("svicen", "Zlatý svícen, určitě by se dal za něco vyměnit.");

        inventory.addToInventory(sekera.getName(), sekera);
        inventory.addToInventory(svicen.getName(), svicen);

        assertTrue(inventory.isInInventory(sekera.getName()));
        assertFalse(game.getGamePlan().getCurrentArea().containsItem(sekera.getName()));

        game.processCommand("poloz sekera");
        inventory.removeItem(sekera.getName());

        assertFalse(inventory.isInInventory(sekera.getName()));
        assertTrue(game.getGamePlan().getCurrentArea().containsItem(sekera.getName()));

        game.processCommand("poloz svicen");
        inventory.removeItem(sekera.getName());

        assertFalse(inventory.isInInventory(sekera.getName()));
        assertTrue(game.getGamePlan().getCurrentArea().containsItem(sekera.getName()));
    } 

}
