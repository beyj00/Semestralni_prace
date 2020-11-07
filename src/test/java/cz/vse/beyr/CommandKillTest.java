package cz.vse.beyr;

import cz.vse.beyr.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída {@code CommandKillTest} slouží ke komplexnímu otestování
 * třídy {@link CommandKillTest}.
 *
 * @author  author name
 * @version 0.00.0000 — 20yy-mm-dd
 */
public class CommandKillTest
{
    private Game game;
    private Inventory inventory;
    /***************************************************************************
     * Inicializace předcházející spuštění každého testu a připravující tzv.
     * přípravek (fixture), což je sada objektů, s nimiž budou testy pracovat.
     */
    @Before
    public void setUp()
    {
        game = new Game();
        this.inventory = inventory;
    }

    /***************************************************************************
     * Test of the {@link #setUp()} method preparing the test fixture.
     */
    @Test
    public void testCommandKill()
    {

        Area lodenice = new Area("loděnice","Zde Floki vyrábí lodě.");

        Person floki = new Person("Floki", "Zdravím tě Ragnare, loď už je skoro hotová. Až mi přineseš kotvu, můžeme hned vyplout na západ.");
        Person jarl_haraldson = new Person("jarl_Haraldson", "Ragnar Lothbruk, vím že máš něco v plánu. Dávej si na mě pozor.", true);

        lodenice.addPerson(floki);
        lodenice.addPerson(jarl_haraldson);

        game.processCommand("kill floki");
        assertTrue(lodenice.containsPerson(floki.getName()));

        game.processCommand("kill jarl_haraldson");

        assertTrue(lodenice.containsPerson(jarl_haraldson.getName()));

        Inventory inventory = new Inventory(game.getGamePlan());

        Item sekera = new Item("sekera", "Válečná sekera.");

        inventory.addToInventory(sekera.getName(), sekera);

        game.processCommand("kill jarl_haraldson");
        lodenice.removePerson(jarl_haraldson.getName());

        assertFalse(lodenice.containsPerson(jarl_haraldson.getName()));

    }
}
