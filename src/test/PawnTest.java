package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import javafx.scene.paint.Color;
import model.Pawn;

/**
 * Klasa odpowiedzialna za test dzialania klasy Pawn
 * @author Ernest Paprocki
 *
 */
public class PawnTest {

	Pawn pawn;

	@Before
	public void setUp() throws Exception {
		pawn = new Pawn(Color.BLACK, 1);
	}

	@Test
	public void checkIfCreatesNewPawnWithArgsConstructorTest() {
		assertTrue(pawn != null);
	}

	@Test
	public void checkIfCreatesNewPawnWithNoArgsConstructorTest() {
		pawn = new Pawn();
		assertTrue(pawn != null);
	}

	@Test
	public void checkIfGetsStageXProperlyTest() {
		int result = pawn.getStageX();
		assertTrue(result == 20);
	}

	@Test
	public void checkIfGetsStageYProperlyTest() {
		int result = pawn.getStageY();
		assertTrue(result == 80);
	}

	@Test
	public void checkIfSetsCounterPositionProperlyTest() {
		pawn.setCounterPosition(200, 200);
		double resultX = pawn.getLayoutX();
		double resultY = pawn.getLayoutY();
		double expectedX = 180;
		double expectedY = 120;
		assertTrue(resultX == expectedX && resultY == expectedY);
	}
}