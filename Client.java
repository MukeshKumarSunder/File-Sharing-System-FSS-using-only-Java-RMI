//@author Mukesh
//G00973428
import java.rmi.*;
import java.rmi.registry.*;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class Client {

	String serverIP;
	String serverportRaw;

	boolean parseEnvVar(String environmentVar) {
		String[] envVar = environmentVar.split(":");
		if (envVar.length == 2) {
			this.serverIP = envVar[0];
			this.serverportRaw = envVar[1];
			return true;
		}
		return false;
	}

	public void beginClient(String cmd, String source, String dest) {
		CommonInterface rmiServer;
		Registry registry;
		String environmentVar = System.getenv("PA1_SERVER");
		if (parseEnvVar(environmentVar)) {
			String serverAddress = serverIP;
			String serverPort = serverportRaw;
			// String dest=null,source=null;
			// String cmd = args[0];
			// if(args.length>=2)
			// source = args[1];
			// if(args.length==3)
			// dest = args[2];
			System.out.println("Command Entered: " + cmd);
			try {
				registry = LocateRegistry.getRegistry(serverAddress, (new Integer(serverPort)).intValue());
				rmiServer = (CommonInterface) registry.lookup("rmiServer");
				int maxp = 100;
				rmiServer.operation(cmd);
				if ("download".equals(cmd)) {
					try {
						byte[] filedata = rmiServer.downloadFile(source);
						File file = new File(dest);
						BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file.getName()));
						System.out.print("Starting Download......");
						for (int i = 0; i <= maxp; ++i) {
							int prcntg = (int) ((i * 100.0f) / maxp);
							if (i < 101) {
								if (prcntg < 10)
									System.out.print("\b\b" + prcntg + "%");
								else
									System.out.print("\b\b\b" + prcntg + "%");
							}
						}
						output.write(filedata, 0, filedata.length);
						System.out.print("\nDownload completed");
						output.flush();
						output.close();
					} catch (Exception e) {
						System.err.println("download should be in format: RmiClient download <source> <destination>");
						e.printStackTrace();
					}
				} else if ("rm".equals(cmd)) {
					File file = new File(source);
					if (file.isFile()) {
						if (file.delete())
							System.out.println("file was successfully deleted\n");
					} else
						System.out.println("Failed to delete the file. The file name you entered is invalid.\n");

				} else if ("mkdir".equals(cmd)) {
					File file = new File(source);
					if (!file.exists()) {
						if (file.mkdirs())
							System.out.println("directory was successfully created");
					} else
						System.out.println("could not create directory because it already exists\n");
				} else if ("rmdir".equals(cmd)) {
					File file = new File(source);
					if (file.isDirectory()) {
						if (file.delete())
							System.out.println("directory was successfully deleted");
					} else
						System.out.println("could not delete directory because an invalid directory was passed\n");
				} else if ("upload".equals(cmd)) {
					try {
						byte[] filedata = rmiServer.uploadFile(dest);
						File file = new File(source);
						BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file.getName()));
						System.out.print("Starting Upload.....");
						for (int i = 0; i <= maxp; ++i) {
							int prcntg = (int) ((i * 100.0f) / maxp);
							if (i < 101) {
								if (prcntg < 10)
									System.out.print("\b\b" + prcntg + "%");
								else
									System.out.print("\b\b\b" + prcntg + "%");
							}
						}
						System.out.println("\nCompleted Upload");
						output.write(filedata, 0, filedata.length);
						output.flush();
						output.close();
					} catch (Exception e) {
						System.err.println("Upload should be in format: RmiClient upload <destination> <source>");
						e.printStackTrace();
					}
				} else if ("dir".equals(cmd)) {
					File directory = new File(source);

					String[] directoryContents = directory.list();
					if (directoryContents == null) {
						System.out.println("No contents in the directory\n");
					} else {
						System.out.println("The directory has " + directoryContents.length + "files/folders :");
						for (String dirContentName : directoryContents) {
							System.out.println(dirContentName);
						}
					}
				} else if ("shutdown".equals(cmd)) {
					
					try {
						registry.unbind("rmiServer");
						System.exit(0);
						System.out.println("Client shutdown successful");
					} 
					catch (RemoteException e) {
						e.printStackTrace();
					}
					
				}
				else{
					System.out.println("The client command should be either upload, download, mkdir, rmdir, dir, rm, shutdown");
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
				System.err.println(e);
			}
		} else {
			System.out.println("could not find system variable or might be entered in incorrect format");
			System.out.println(
					"for mac : export PA1_SERVER=<computername:portnumber>\n for windows : set PA1_SERVER=<computername:portnumber>");
		}
	}

}
