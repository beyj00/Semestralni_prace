
package cz.vse.beyr;

import cz.vse.beyr.model.Game;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída {@code CommandMoveTest} slouží ke komplexnímu otestování
 * třídy {@link CommandMoveTest}.
 *
 * @author  Jakub Beyr
 * @version LS 2020
 */
public class CommandMoveTest
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
        assertEquals("farma", game.getGamePlan().getCurrentArea().getName());
        game.processCommand("jdi farma");
        assertEquals("farma", game.getGamePlan().getCurrentArea().getName());

        game.processCommand("jdi kattegat");
        assertEquals("kattegat", game.getGamePlan().getCurrentArea().getName());

        game.processCommand("jdi pristav");
        assertEquals("pristav", game.getGamePlan().getCurrentArea().getName());

        game.processCommand("jdi farma");
        assertNull(game.getGamePlan().getCurrentArea().getExitArea("farma"));
        assertEquals("pristav", game.getGamePlan().getCurrentArea().getName());

        game.processCommand("jdi anglie");
        assertEquals("pristav", game.getGamePlan().getCurrentArea().getName());

    }

}
