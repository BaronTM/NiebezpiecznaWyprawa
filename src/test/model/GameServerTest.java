package test.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import game.controller.Main;
import game.model.GameServer;

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
