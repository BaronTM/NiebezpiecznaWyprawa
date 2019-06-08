package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import model.ImaginaryPlayer;

/**
 * Klasa odpowiedzialna za test dzialania klasy ImaginaryPlayer
 * @author Ernest Paprocki
 *
 */
public class ImaginaryPlayerTest {

	ImaginaryPlayer imaginaryPlayer;

	@Mock
	ObjectInputStream objectInputStream;

	@Mock
	ObjectOutputStream objectOutputStream;

	@Before
	public void setUp() throws Exception {
		imaginaryPlayer = new ImaginaryPlayer(1, objectInputStream, objectOutputStream);
	}

	@Test
	public void checkIfCreatesNewImaginaryPlayerTest() {
		assertTrue(imaginaryPlayer != null);
	}

	@Test
	public void checkIfSetsIdProperlyAfterCreatingNewImaginaryPlayerTest() {
		int result = imaginaryPlayer.getId();
		assertTrue(result == 1);
	}

	@Test
	public void checkIfSetsObjectInputStreamProperlyAfterCreatingNewImaginaryPlayerTest() {
		ObjectInputStream ois = Mockito.mock(ObjectInputStream.class);
		imaginaryPlayer = new ImaginaryPlayer(1, ois, objectOutputStream);
		ObjectInputStream result = imaginaryPlayer.getObjectInputStream();
		assertTrue(result.equals(ois));
	}

	@Test
	public void checkIfSetsObjectOutputStreamProperlyAfterCreatingNewImaginaryPlayerTest() throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new OutputStream() {
			@Override
			public void write(int b) throws IOException {
			}
		});
		imaginaryPlayer = new ImaginaryPlayer(1, objectInputStream, oos);
		ObjectOutputStream result = imaginaryPlayer.getObjectOutputStream();
		assertTrue(result.equals(oos));
	}

	@Test
	public void checkIfSetsIdProperlyTest() {
		imaginaryPlayer.setId(4);
		int result = imaginaryPlayer.getId();
		assertTrue(result == 4);
	}


	@Test
	public void checkIfSetsScoreProperlyTest() {
		imaginaryPlayer.setScore(40);
		int result = imaginaryPlayer.getScore();
		assertTrue(result == 40);
	}

	@Test
	public void checkIfSetsRemainCountersProperlyTest() {
		imaginaryPlayer.setRemainCounters(10);
		int result = imaginaryPlayer.getRemainCounters();
		assertTrue(result == 10);
	}

	@Test
	public void checkIfSetsIdOfCurrentCounterProperlyTest() {
		imaginaryPlayer.setIdOfCurrentCounter(50);
		int result = imaginaryPlayer.getIdOfCurrentCounter();
		assertTrue(result == 50);
	}

	@Test
	public void checkIfSetsCurrentCounterPositionProperlyTest() {
		imaginaryPlayer.setCurrentCounterPosition(new int[] {1, 2});
		int[] result = imaginaryPlayer.getCurrentCounterPosition();
		int[] expect = new int[] {1, 2};
		assertTrue(Arrays.equals(result, expect));
	}

	@Test
	public void checkIfSetsObjectOutputStreamProperlyTest() throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new OutputStream() {
			@Override
			public void write(int b) throws IOException {
			}
		});
		imaginaryPlayer.setObjectOutputStream(oos);
		ObjectOutputStream result = imaginaryPlayer.getObjectOutputStream();
		assertTrue(result.equals(oos));
	}

	@Test
	public void checkIfSetsObjectInputStreamProperlyTest() {
		ObjectInputStream ois = Mockito.mock(ObjectInputStream.class);
		imaginaryPlayer.setObjectInputStream(ois);
		ObjectInputStream result = imaginaryPlayer.getObjectInputStream();
		assertTrue(result.equals(ois));
	}

	@Test
	public void checkIfSetsSunkenCountersProperlyTest() {
		imaginaryPlayer.setSunkenCounters(3);
		int result = imaginaryPlayer.getSunkenCounters();
		assertTrue(result == 3);
	}

	@Test
	public void checkIfSetsSalvageCountersProperlyTest() {
		imaginaryPlayer.setSalvageCounters(33);
		int result = imaginaryPlayer.getSalvageCounters();
		assertTrue(result == 33);
	}

	@Test
	public void checkIfSetsCurrentCounterPositionStepProperlyTest() {
		imaginaryPlayer.setCurrentCounterPositionStep(44);
		int result = imaginaryPlayer.getCurrentCounterPositionStep();
		assertTrue(result == 44);
	}

	@Test
	public void checkIfSetsBridgeCoordinatesForFirstPlayerProperlyTest() {
		int[] result = imaginaryPlayer.getCurrentCounterPosition();
		assertTrue(Arrays.equals(result, new int[] {466, 613} ));
	}

	@Test
	public void checkIfSetsBridgeCoordinatesForSecondPlayerProperlyTest() {
		imaginaryPlayer = new ImaginaryPlayer(2, objectInputStream, objectOutputStream);
		int[] result = imaginaryPlayer.getCurrentCounterPosition();
		assertTrue(Arrays.equals(result, new int[] {582, 577} ));
	}

	@Test
	public void checkIfReturnsFinishScoresForNotMovedPawnsProperlyTest() {
		int result = imaginaryPlayer.getFinishScore();
		System.out.println(result);
		assertTrue(result == 20);
	}

	@Test
	public void checkIfReturnsProperlyCommandToSetPawnIntoWaterTest() {
		String[] result = imaginaryPlayer.counterToWater();
		String[] expected = {
				"WATER",
				"" + 1,
				"" + 0
		};
		assertTrue(Arrays.equals(result, expected));
	}

	@Test
	public void checkIfIncrementCurrentCounterIdAfterPushingIntoWaterTest() {
		imaginaryPlayer.counterToWater();
		int result = imaginaryPlayer.getIdOfCurrentCounter();
		assertTrue(result == 1);
	}

	@Test
	public void checkIfDecrementsRemainsCountersAfterPushingIntoWaterTest() {
		imaginaryPlayer.counterToWater();
		int result = imaginaryPlayer.getRemainCounters();
		assertTrue(result == 3);
	}

	@Test
	public void checkIfSetsCurrentCounterPositionStepDefaultAfterPushingIntoWaterTest() {
		imaginaryPlayer.setCurrentCounterPositionStep(4);
		imaginaryPlayer.counterToWater();
		int result = imaginaryPlayer.getCurrentCounterPositionStep();
		assertTrue(result == 0);
	}

	@Test
	public void checkIfSetsCurrentCounterPositionDefaultAfterPushingIntoWaterTest() {
		imaginaryPlayer.setCurrentCounterPosition(new int[] {44, 44});
		imaginaryPlayer.counterToWater();
		int result[] = imaginaryPlayer.getCurrentCounterPosition();
		assertTrue(Arrays.equals(new int[] {466, 613}, result));
	}

	@Test
	public void checkIfReturnsProperlyCommandToMovePawnTest() {
		String[] result = imaginaryPlayer.moveCounterCommand();
		String[] expected = new String[] {
				"MOVE",
				"" + 1,
				"" + 0,
				"" + 466,
				"" + 613};
		assertTrue(Arrays.equals(result, expected));
	}

	@Test
	public void checkIfAddsStepsToScoreAfterAddToMoveTest() {
		imaginaryPlayer.addToMove(2);
		imaginaryPlayer.addToMove(6);
		int result = imaginaryPlayer.getScore();
		assertTrue(result == 8);
	}

	@Test
	public void checkIfAddsStepsToCurrentCounterPositionStepAfterAddToMoveTest() {
		imaginaryPlayer.addToMove(3);
		imaginaryPlayer.addToMove(2);
		int result = imaginaryPlayer.getCurrentCounterPositionStep();
		assertTrue(result == 5);
	}

	@Test
	public void checkIfSetsBridgeCoordinatesProperlyAfterAddToMoveTest() {
		imaginaryPlayer.addToMove(2);
		imaginaryPlayer.addToMove(2);
		int[] result = imaginaryPlayer.getCurrentCounterPosition();
		int[] expected = new int[] {386, 392};
		assertTrue(Arrays.equals(result, expected));
	}

	@Test
	public void checkIfSetsFinishBridgeCoordinatesProperlyAfterAddToMoveMoreThan9Test() {
		imaginaryPlayer.addToMove(5);
		imaginaryPlayer.addToMove(7);
		int[] result = imaginaryPlayer.getCurrentCounterPosition();
		int[] expected = new int[] {407, 152};
		assertTrue(Arrays.equals(result, expected));
	}

	@Test
	public void checkIfDecrementsRemainCountersAfterNewSalvageTest() {
		imaginaryPlayer.newSalvage();
		int result = imaginaryPlayer.getRemainCounters();
		assertTrue(result == 3);
	}

	@Test
	public void checkIfAdds10PointsAfterNewSalvageTest() {
		imaginaryPlayer.newSalvage();
		int result = imaginaryPlayer.getScore();
		assertTrue(result == 10);
	}

	@Test
	public void checkIfIncrementIdOfCurrentCounterAfterNewSalvageTest() {
		imaginaryPlayer.newSalvage();
		int result = imaginaryPlayer.getIdOfCurrentCounter();
		assertTrue(result == 1);
	}

	@Test
	public void checkIfIncrementsSalvageCountersAfterNewSalvageTest() {
		imaginaryPlayer.newSalvage();
		int result = imaginaryPlayer.getSalvageCounters();
		assertTrue(result == 1);
	}

	@Test
	public void checkIfSetsDefaultCurrentCounterPositionStepAfterNewSalvageTest() {
		imaginaryPlayer.newSalvage();
		int result = imaginaryPlayer.getCurrentCounterPositionStep();
		assertTrue(result == 0);
	}

	@Test
	public void checkIfSetsDefaultCurrentCounterPositionAfterNewSalvageTest() {
		imaginaryPlayer.newSalvage();
		int[] result = imaginaryPlayer.getCurrentCounterPosition();
		int[] expected = new int[] {466, 613};
		assertTrue(Arrays.equals(result, expected));
	}
}