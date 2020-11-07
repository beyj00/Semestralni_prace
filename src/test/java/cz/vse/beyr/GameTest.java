package cz.vse.beyr;

import cz.vse.beyr.model.Game;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Testovací třída pro komplexní otestování herního příběhu.
 *
 * @author Jarmila Pavlíčková
 * @author Jan Říha
 * @version LS 2020
 */
public class GameTest
{
    private Game game;

    @Before
    public void setUp()
    {
        game = new Game();
    }

    @Test
    public void testPlayerQuit()
    {
        assertEquals("farma", game.getGamePlan().getCurrentArea().getName());

        game.processCommand("jdi kattegat");
        assertEquals("kattegat", game.getGamePlan().getCurrentArea().getName());
        assertFalse(game.isGameOver());

        game.processCommand("jdi trh");
        assertEquals("trh", game.getGamePlan().getCurrentArea().getName());
        assertFalse(game.isGameOver());

        game.processCommand("konec");
        assertTrue(game.isGameOver());
    }

    @Test
    public void testPlayerWinWithoutKill()
    {
        assertEquals("farma", game.getGamePlan().getCurrentArea().getName());

        game.processCommand("seber svicen");
        assertFalse(game.isGameOver());

        game.processCommand("jdi kattegat");
        assertFalse(game.isGameOver());

        game.processCommand("jdi trh");
        assertFalse(game.isGameOver());

        game.processCommand("poloz svicen");
        assertFalse(game.isGameOver());

        game.processCommand("seber kotva ");
        assertFalse(game.isGameOver());

        game.processCommand("jdi kattegat");
        assertFalse(game.isGameOver());

        game.processCommand("jdi farma");
        assertFalse(game.isGameOver());

        game.processCommand("jdi lodenice");
        assertFalse(game.isGameOver());

        game.processCommand("jdi anglie"); 
        assertTrue(game.getGamePlan().isVictorious());
        assertTrue(game.isGameOver());
    }

    @Test
    public void testPlayerWinWithKill()
    {
        assertEquals("farma", game.getGamePlan().getCurrentArea().getName());

        game.processCommand("jdi lodenice");
        assertFalse(game.isGameOver());

        game.processCommand("seber sekera");
        assertFalse(game.isGameOver());

        game.processCommand("jdi farma");
        assertFalse(game.isGameOver());

        game.processCommand("jdi kattegat");
        assertFalse(game.isGameOver());

        game.processCommand("zabij jarl_Haraldson");
        assertFalse(game.isGameOver());

        game.processCommand("seber koruna");
        assertFalse(game.isGameOver());

        game.processCommand("jdi pristav");
        assertFalse(game.isGameOver());

        game.processCommand("jdi anglie"); 
        assertTrue(game.getGamePlan().isVictorious());
        assertTrue(game.isGameOver());
    }

    @Test
    public void testPlayerLose()
    {
        assertEquals("farma", game.getGamePlan().getCurrentArea().getName());

        game.processCommand("jdi kattegat");
        assertFalse(game.isGameOver());

        game.processCommand("zabij jarl_Haraldson");
        assertFalse(game.getGamePlan().isDefeated());
        assertFalse(game.isGameOver());
    }
}
