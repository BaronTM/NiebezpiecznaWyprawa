package test.model;

import static org.junit.Assert.*;

import org.junit.Test;

import game.model.DaemonThreadFactory;

public class DaemonThreadFactoryTest {

	@Test
	public void checkingIfCreateNewDaemonThreadFactoryTest() {
		DaemonThreadFactory d = new DaemonThreadFactory();
		assertTrue(d != null);;
	}
	
	@Test
	public void checkingIfCreatedThreadIsDaemonTest() {
		DaemonThreadFactory d = new DaemonThreadFactory();
		Thread t = d.newThread(() -> System.out.println("Am i daemon?"));
		assertTrue(t.isDaemon());;
	}

}
