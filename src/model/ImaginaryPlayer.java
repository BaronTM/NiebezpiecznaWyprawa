package model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Klasa obslugujaca aktualnego gracza
 * @author Ernest Paprocki
 *
 */
public class ImaginaryPlayer {

	/**
	 * identyfikator gracza
	 */
	private int id;
	/**
	 * ilosc dostepnych pionkow gracza
	 */
	private int remainCounters;
	/**
	 * ilosc punktow
	 */
	private int score;
	/**
	 * identyfikator aktywnego pionka
	 */
	private int idOfCurrentCounter;
	/**
	 * pozycja aktywnego pionka
	 */
	private int[] currentCounterPosition;
	/**
	 * pozycja pionka na moscie
	 */
	private int currentCounterPositionStep;
	/**
	 * wspolrzedne na moscie
	 */
	private int[][] bridgeCoordinates;
	/**
	 * obiekt wyjsciowy
	 */
	private ObjectOutputStream objectOutputStream;
	/**
	 * obiekt wejsciowy
	 */
	private ObjectInputStream objectInputStream;
	/**
	 * liczba zatopionych pionkow
	 */
	private int sunkenCounters;
	/**
	 * liczba pionkow na kamieniach
	 */
	private int salvageCounters;

	/**
	 * Konstruktor aktualnego gracza
	 * @param nr identyfikator gracza
	 * @param ois obiekt wejsciowy
	 * @param oos obiekt wyjsciowy
	 */
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
		bridgeCoordinates = id == 1 ? Board.getLeftPlanksPos() : Board.getRightPlanksPos();
		currentCounterPosition = bridgeCoordinates[0];
	}

	/**
	 * @return zwraca identyfikator gracza
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id identyfikator gracza typu integer
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return zwraca ilosc dostepnych pionkow gracza
	 */
	public int getRemainCounters() {
		return remainCounters;
	}

	/**
	 * @param remainCounters liczba dostepnych pionkow gracza typu integer
	 */
	public void setRemainCounters(int remainCounters) {
		this.remainCounters = remainCounters;
	}

	/**
	 * @return zwraca liczbe punktow gracza
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score liczba punkow gracza typu integer
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @return zwraca identyfikator aktualnego pionka
	 */
	public int getIdOfCurrentCounter() {
		return idOfCurrentCounter;
	}

	/**
	 * @param idOfCurrentCounter identyfikator aktualnego pionka typ integer
	 */
	public void setIdOfCurrentCounter(int idOfCurrentCounter) {
		this.idOfCurrentCounter = idOfCurrentCounter;
	}

	/**
	 * @return zwraca pozycje aktualnego pionka
	 */
	public int[] getCurrentCounterPosition() {
		return currentCounterPosition;
	}

	/**
	 * @param currentCounterPosition pozycja aktualnego pionka typu tablica integer'ow
	 */
	public void setCurrentCounterPosition(int[] currentCounterPosition) {
		this.currentCounterPosition = currentCounterPosition;
	}

	/**
	 * @return zwraca obiekt wyjsciowy
	 */
	public ObjectOutputStream getObjectOutputStream() {
		return objectOutputStream;
	}

	/**
	 * @param objectOutputStream obiekt wyjsciowy typu ObjectOutputStream
	 */
	public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
		this.objectOutputStream = objectOutputStream;
	}

	/**
	 * @return zwraca obiekt wejsciowy
	 */
	public ObjectInputStream getObjectInputStream() {
		return objectInputStream;
	}

	/**
	 * @param objectInputStream obiekt wejsciowy typu ObjectInputStream
	 */
	public void setObjectInputStream(ObjectInputStream objectInputStream) {
		this.objectInputStream = objectInputStream;
	}

	/**
	 * @return zwraca liczbe zatopionych pionkow
	 */
	public int getSunkenCounters() {
		return sunkenCounters;
	}

	/**
	 * @param sunkenCounters liczba zatopionych pionkow typu integer
	 */
	public void setSunkenCounters(int sunkenCounters) {
		this.sunkenCounters = sunkenCounters;
	}

	/**
	 * @return zwraca liczbe pionkow na kamieniach
	 */
	public int getSalvageCounters() {
		return salvageCounters;
	}

	/**
	 * @param salvageCounters liczba pionkow na kamieniach typu integer
	 */
	public void setSalvageCounters(int salvageCounters) {
		this.salvageCounters = salvageCounters;
	}

	/**
	 * @return zwraca numer kladki dla aktualnego pionka
	 */
	public int getCurrentCounterPositionStep() {
		return currentCounterPositionStep;
	}

	/**
	 * @param currentCounterPositionStep numer kladki dla aktualnego pionka typu integer
	 */
	public void setCurrentCounterPositionStep(int currentCounterPositionStep) {
		this.currentCounterPositionStep = currentCounterPositionStep;
	}

	/**
	 * Metoda do przekazania punktacji gracza
	 * @return zwraca liczbe punktow
	 */
	public int getFinishScore() {
		int s = score + remainCounters * 5;
		return s;
	}

	/**
	 * Metoda do przekazania ciagu tekstowego do obslugi spadania pionka do wody
	 * @return zwraca ciag tekstowy typu tablice ciagow tekstowych
	 */
	public String[] counterToWater() {
		String[] s = new String[] {
				"WATER",
				"" + id,
				"" + idOfCurrentCounter
		};
		idOfCurrentCounter++;
		remainCounters--;
		currentCounterPositionStep = 0;
		currentCounterPosition = bridgeCoordinates[0];
		return s;
	}

	/**
	 * Metoda do przekazania ciagu tekstowego do obslugi przesuniecia pionka
	 * @return zwraca ciag tekstowy typu tablice ciagow tekstowych
	 */
	public String[] moveCounterCommand() {
		String[] move = new String[] {
				"MOVE",
				"" + id,
				"" + idOfCurrentCounter,
				"" + currentCounterPosition[0],
				"" + currentCounterPosition[1]};
		return move;
	}

	/**
	 * Metoda do obslugi parametrow przesuniecia po moscie i naliczenia punktow
	 * @param step ilosc krokow do przesuniecia pionka po moscie typu integer
	 */
	public void addToMove(int step) {
		score += step;
		currentCounterPositionStep += step;
		if (currentCounterPositionStep <= 9) {
			currentCounterPosition = bridgeCoordinates[currentCounterPositionStep];
		} else {
			currentCounterPosition = bridgeCoordinates[9];
		}
	}

	/**
	 * Metoda do obslugi pionka na kamieniach  i naliczenia punktow
	 */
	public void newSalvage() {
		remainCounters--;
		score += 10;
		idOfCurrentCounter++;
		salvageCounters++;
		currentCounterPositionStep = 0;
		currentCounterPosition = bridgeCoordinates[0];
	}
}