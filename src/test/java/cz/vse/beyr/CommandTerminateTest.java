
package cz.vse.beyr;

import cz.vse.beyr.model.Game;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída {@code CommandTerminateTest} slouží ke komplexnímu otestování
 * třídy {@link CommandTerminateTest}.
 *
 * @author  author name
 * @version 0.00.0000 — 20yy-mm-dd
 */
public class CommandTerminateTest
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
        game.processCommand("seber svicen");
        assertFalse(game.isGameOver());

        game.processCommand("jdi kattegat");
        assertFalse(game.isGameOver());

        game.processCommand("konec");
        assertTrue(game.isGameOver());
    }

}
