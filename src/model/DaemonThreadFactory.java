package model;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Klasa do obslugi watkow
 * @author Ernest Paprocki
 *
 */
public class DaemonThreadFactory implements ThreadFactory {
	@Override
	public Thread newThread(Runnable r) {
		Thread t = Executors.defaultThreadFactory().newThread(r);
		t.setDaemon(true);
		return t;
	}
}