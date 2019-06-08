package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import controller.Main;
import model.GameServer;

/**
 * Klasa odpowiedzialna za test dzialania klasy GameServer
 * @author Ernest Paprocki
 *
 */
public class GameServerTest {

	GameServer gameServer;

	@Mock
	Main mainMock;

	@Before
	public void setUp() throws Exception {
		gameServer = new GameServer(mainMock);
	}

	@Test
	public void checkIfCreatesNewGameServer() {
		assertTrue(gameServer != null);
	}

}