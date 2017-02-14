//@author Mukesh
//G00973428
import java.rmi.RemoteException;

public class Mukesh {
	public static void main(String[] args) {
		if (args.length > 0) {
			String role = args[0];
			switch (role) {
			case "server":
				if (args.length == 3) {
					Server localServer = null;
					try {
						localServer = new Server();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						localServer.beginServer(args[1], args[2]);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					System.out.println("not a valid command for server. use commands in the format : server start <port number>");
				}
				break;
			case "client":
				if ((args.length == 3) || (args.length == 4)) {
					String operation = args[1];
					String fileName = args[2];
					String destinationFileName = null;
					if (args.length == 4) {
						destinationFileName = args[3];
					}
					Client localClient = new Client();
					localClient.beginClient(operation, fileName, destinationFileName);
				} 
				else if(args.length==2){
					String operation = args[1];
					Client localClient = new Client();
					localClient.beginClient(operation, null, null);
				}
				else {
					System.out.println("not a valid command passed for client. use commands in the format : client <operation> <arg1> <arg2>");
				}
				break;
			default:
				System.out.println("command must start with either server or client");
			}
		}
	}
}
