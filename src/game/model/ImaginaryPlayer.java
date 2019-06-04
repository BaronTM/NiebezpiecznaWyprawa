package game.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ImaginaryPlayer {
	
	private int id;
	private int remainCounters;
	private int score;
	private int idOfCurrentCounter;
	private int[] currentCounterPosition;
	private int currentCounterPositionStep;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;
	private int sunkenCounters;
	private int salvageCounters;
	
	public ImaginaryPlayer(int nr, ObjectInputStream ois, ObjectOutputStream oos) {
		id = nr;
		objectInputStream = ois;
		objectOutputStream = oos;
		remainCounters = 4;
		score = 0;
		idOfCurrentCounter = 0;
		sunkenCounters = 0;
		salvageCounters = 0;
		currentCounterPositionStep = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRemainCounters() {
		return remainCounters;
	}

	public void setRemainCounters(int remainCounters) {
		this.remainCounters = remainCounters;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getIdOfCurrentCounter() {
		return idOfCurrentCounter;
	}

	public void setIdOfCurrentCounter(int idOfCurrentCounter) {
		this.idOfCurrentCounter = idOfCurrentCounter;
	}

	public int[] getCurrentCounterPosition() {
		return currentCounterPosition;
	}

	public void setCurrentCounterPosition(int[] currentCounterPosition) {
		this.currentCounterPosition = currentCounterPosition;
	}

	public ObjectOutputStream getObjectOutputStream() {
		return objectOutputStream;
	}

	public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
		this.objectOutputStream = objectOutputStream;
	}

	public ObjectInputStream getObjectInputStream() {
		return objectInputStream;
	}

	public void setObjectInputStream(ObjectInputStream objectInputStream) {
		this.objectInputStream = objectInputStream;
	}

	public int getSunkenCounters() {
		return sunkenCounters;
	}

	public void setSunkenCounters(int sunkenCounters) {
		this.sunkenCounters = sunkenCounters;
	}

	public int getSalvageCounters() {
		return salvageCounters;
	}

	public void setSalvageCounters(int salvageCounters) {
		this.salvageCounters = salvageCounters;
	}	
	
	public int getCurrentCounterPositionStep() {
		return currentCounterPositionStep;
	}

	public void setCurrentCounterPositionStep(int currentCounterPositionStep) {
		this.currentCounterPositionStep = currentCounterPositionStep;
	}

	public String[] counterToWater() {
		String[] s = new String[] {
				"WATER",
				"" + id,
				"" + idOfCurrentCounter				
		};
		idOfCurrentCounter++;
		return s;
	}
	
	public String[] moveCounter() {
		String[] move = new String[] {
				"MOVE", 
				"" + id, 
				"" + idOfCurrentCounter, 
				"" + currentCounterPosition[0],
				"" + currentCounterPosition[1]};
		return move;
	}

}
