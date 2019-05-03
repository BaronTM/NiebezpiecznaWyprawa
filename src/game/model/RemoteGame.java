package game.model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteGame extends Remote {
	
	public String getDiceResult() throws RemoteException;
	public boolean getDecision() throws RemoteException;
	public boolean moveCounter() throws RemoteException;
	public boolean showScore() throws RemoteException;
	public boolean updateData() throws RemoteException;	
	
}
