package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import controller.Main;
import model.Game;


/**
 * Klasa odpowiedzialna za test dzialania klasy Game
 * @author Ernest Paprocki
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class GameTest {


	@Mock
	Main mainMock;

	@Mock
	Socket socketMock;

	Game game;

	@Before
	public void setUp() throws Exception {
		game = new Game(1, mainMock);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void creatingNewGameTest() {
		assertTrue(game != null);
	}

	@Test
	public void checkinIfProperlySetsSocketTest() throws IOException {
		Mockito.when(socketMock.getOutputStream()).thenReturn(new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				// TODO Auto-generated method stub
			}
		});
		Mockito.when(socketMock.getInputStream()).thenReturn(new InputStream() {
			@Override
			public int read() throws IOException {
				return 0;
			}
		});
		game.setSock(socketMock);
		Socket socket = game.getSock();
		assertTrue(socket.equals(socketMock));
	}

}