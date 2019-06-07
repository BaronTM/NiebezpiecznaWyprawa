package test.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import game.model.Gamer;
import game.model.Pawn;
import javafx.scene.paint.Color;

public class GamerTest {

	Gamer gamer;
	
	@Before
	public void setUp() throws Exception {
		gamer = new Gamer(1, "gamer", Color.BLUE);
	}


	@Test
	public void checksIfCreatesNewGamerTest() {
		assertTrue(gamer != null);
	}
	
	@Test
	public void checksIfCreatetes4PawnsTest() {
		int result = gamer.getPawns().size();
		assertTrue(result == 4);
	}
	
	@Test
	public void checksIfReturnsCurrentPawnTest() {
		Pawn p = gamer.getCurrentPawn();
		assertTrue(p != null);
	}

}
