//@author Mukesh
//G00973428
import java.rmi.*;

public interface CommonInterface extends java.rmi.Remote
{
	void operation( String x) throws RemoteException,NotBoundException;

	public byte[] downloadFile(String fileName) throws   RemoteException;

	public byte[] uploadFile(String fileName) throws   RemoteException;
	
	public void removeFile(String fileName) throws   RemoteException;
	
	public void makeDirectory(String path) throws   RemoteException;
	
	public void removeDirectory(String path) throws   RemoteException;
	
	public String[] directory(String path) throws   RemoteException;
	
	public String commonCode(String downloadedFile, int downSize, int endLimit)throws RemoteException;
}
