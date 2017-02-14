//@author Mukesh
//G00973428
import java.rmi.*;
import java.rmi.registry.*;
import java.security.MessageDigest;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@SuppressWarnings("serial")
public class Server extends java.rmi.server.UnicastRemoteObject implements CommonInterface {

	String addressI;
	int port, max=100;
	Registry registry;

	protected Server() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public void operation(String x) throws RemoteException {
		System.out.println(x);
	}

	public void beginServer(String command, String portNumber) throws RemoteException {
		if ("start".equals(command))
			port = Integer.parseInt(portNumber);
		
		try {
			
			try {
				addressI = "127.0.0.1";
			} catch (Exception e) {
				System.out.println("Did not get the address");
			}
			System.out.println("Server started\naddress=" + addressI + ",port=" + port);
			try {
				registry = LocateRegistry.createRegistry(port);
				registry.rebind("rmiServer", this);
			} catch (RemoteException e) {
				System.out.println("remote exception" + e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@SuppressWarnings("unused")
	@Override
	public byte[] downloadFile(String fileName) {
		try {
			File file = new File(fileName);
			//create a buffer for the length of the file
			byte buffer[] = new byte[(int) file.length()];
			//opens a file input stream
			BufferedInputStream input = new BufferedInputStream(new FileInputStream(fileName));
			input.read(buffer, 0, buffer.length);
			int downloadRaw = input.read();
			String val = commonCode(fileName, max, downloadRaw);
			input.close();
			return (buffer);
		} catch (Exception e) {
			System.out.println("Download Command for : " + fileName);
			// e.printStackTrace();
			return (null);
		}
	}

	@SuppressWarnings("unused")
	@Override
	public byte[] uploadFile(String fileName) {
		try {
			File file = new File(fileName);
			//opens up a buffer for the length of the file
			byte buffer[] = new byte[(int) file.length()];
			BufferedInputStream input = new BufferedInputStream(new FileInputStream(fileName));
			input.read(buffer, 0, buffer.length);
			int downloadRaw = input.read();
			String val = commonCode(fileName, max, downloadRaw);
			input.close();
			return (buffer);
		} catch (Exception e) {
			System.out.println("Upload command for : " + fileName);
			// e.printStackTrace();
			return (null);
		}
	}

	@Override
	public void removeFile(String fileName) throws RemoteException {
		//create a file variable
		File file = new File(fileName);
		if (file.isFile()) {
			if (file.delete())
				System.out.println("file was successfully deleted\n");
		} else
			System.out.println("Failed to delete the file. The file name you entered is invalid.\n");

	}

	@Override
	public void makeDirectory(String fileName) throws RemoteException {
		//create a file variable
		File file = new File(fileName);
		if (!file.exists()) {
			if (file.mkdirs())
				System.out.println("directory was successfully created\n");
		} else
			System.out.println("could not create directory because it already exists\n");
	}

	@Override
	public void removeDirectory(String fileName) throws RemoteException {
		//create a file variable..checks for a directory and deletes
		File file = new File(fileName);
		if (file.isDirectory()) {
			if (file.delete())
				System.out.println("directory was successfully deleted\n");
		} else
			System.out.println("could not delete directory because an invalid directory was passed\n");

	}

	@Override
	public String[] directory(String fileName) throws RemoteException {
		//creates a variable to store directories
		File directory = new File(fileName);
		String[] directoryContents = directory.list();
		if (directoryContents == null) {
			System.out.println("No contents in the directory\n");
			return directoryContents;
		} else {
			System.out.println(directoryContents.length + "\n");
			for (String dirContentName : directoryContents) {
				System.out.println(dirContentName + "\n");
			}
		}
		return null;
	}

	@SuppressWarnings("unused")
	public String commonCode(String fileName, int endLimit, int downSize) {
		String checkData = null;
		FileInputStream inputStream = null;
		int atMost, byteCompl, limit;
		if(endLimit==0){
			System.out.println("invalid value for endLimit");			
		}
		try {			
			MessageDigest md = MessageDigest.getInstance("MD5");
			try {
				inputStream = new FileInputStream(fileName);
				byteCompl = 0;
				atMost = 100;
				limit = downSize - atMost;
				byte[] buffer = new byte[atMost];
				do {
					int bufferSize = 0;
					if (limit < byteCompl){
						//change value of buffer size
						bufferSize = downSize - byteCompl;
					}
					else if(limit != endLimit){
						limit = -1;
					}
					else
						bufferSize = atMost;					
				} while (byteCompl < downSize);
				if (byteCompl != -1) {
					byte[] Md_Byte = md.digest();
					//create a new buffer
					StringBuffer hex_String = new StringBuffer("");
					for (int i = 0; i < Md_Byte.length; i++) {
						if(byteCompl < endLimit)
							hex_String.append(Integer.toString((Md_Byte[i] & 0xff) + 0x100, 16).substring(1));
					}
					if(checkData != null)
						System.out.println("invalid data");
					String Md_String = hex_String.toString();
					checkData = Md_String;
				}
			} catch (FileNotFoundException err) {
				System.out.println("file not found");
				checkData = null;
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					//	System.out.println("closed input stream");
					} catch (IOException err) {
						System.out.println("could not close input stream because exception occured");
					}
				}
			}
		} catch (Exception err) {
		//	System.out.println("exception occured");
		}
		return checkData;
	}
}
