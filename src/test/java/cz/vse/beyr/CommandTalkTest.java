
package cz.vse.beyr;

import cz.vse.beyr.model.Area;
import cz.vse.beyr.model.Game;
import cz.vse.beyr.model.Person;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída {@code CommandTalkTest} slouží ke komplexnímu otestování
 * třídy {@link CommandTalkTest}.
 *
 * @author  Jakub Beyr
 * @version LS 2020
 */
public class CommandTalkTest
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
    public void testSetUp()
    {
        Area area = new Area("farma","Zde žije Ragnar se svou rodinou.");

        Person lagherta = new Person("Lagherta", "Měl by jsi zabít Haraldsona, pokud se vrátíš ze západu, budou s ním velké problémy.");

        game.processCommand("promluv Lagherta");
        assertFalse(area.containsPerson(lagherta.getName()));

        area.addPerson(lagherta);

        game.processCommand("promluv Lagherta");
        assertEquals(lagherta, area.getPerson(lagherta.getName()));
        assertTrue(area.containsPerson(lagherta.getName()));

    }

}
