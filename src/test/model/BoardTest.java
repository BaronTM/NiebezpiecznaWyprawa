package test.model;

import static org.junit.Assert.*;

import org.junit.Test;

import game.model.Board;

public class BoardTest {

	@Test
	public void getingLeftPlanksPosTest() {
		int firstLeftPlankXPos = Board.getLeftPlanksPos()[0][0];
		int expected = 466;
		assertTrue(expected == firstLeftPlankXPos);
	}
	
	@Test
	public void getingRightPlanksPosTest() {
		int firstRightPlankXPos = Board.getRightPlanksPos()[0][0];
		int expected = 582;
		assertTrue(expected == firstRightPlankXPos);
	}
	
	@Test
	public void getingStonesPosTest() {
		int firstStoneXPos = Board.getStonesPos()[0][0];
		int expected = 33;
		assertTrue(expected == firstStoneXPos);
	}
	
	@Test
	public void checkingIfBoardIsSingletonTest() {
		Board board = Board.getBoard();
		Board board2 = Board.getBoard();
		assertTrue(board.equals(board2));
	}

}
