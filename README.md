# File-Sharing-System-FSS-using-only-Java-RMI
The Mukesh.java file needs to be executed on a terminal for both client and server commands
java Mukesh server start 8052 would start a server on a terminal with port number 8052

the clients can be executed by providing commands that start with "java Mukesh client" and so on followed by the other arguments
the possible commandsfor the client are upload, download, mkdir, rmdir, dir, rm, shutdown

COMMANDS TO RUN .jar FILE :-

java -jar "Mukesh.jar" server start 8052

set PA1_SERVER=127.0.0.1:8052

java -jar "Mukesh.jar" client download test.txt test1.txt

java -jar "Mukesh.jar" client upload test2.txt test.txt

java -jar "Mukesh.jar" client mkdir "F:\Mukesh\Workspace\622-2 FileSharing\src\test"

java -jar "Mukesh.jar" client rmdir "F:\Mukesh\Workspace\622-2 FileSharing\src\test"

java -jar "Mukesh.jar" client dir "F:\Mukesh\Workspace\622-2 FileSharing\src"

java -jar "Mukesh.jar" client rm "F:\Mukesh\Workspace\622-2 FileSharing\src\test1.txt"

java -jar "Mukesh.jar" client shutdown

COMMANDS TO RUN .java FILE :-

set PA1_SERVER=127.0.0.1:8052

javac Mukesh.java

java Mukesh server start 8052

java Mukesh client upload test2.txt test.txt

java Mukesh client download test.txt test1.txt

java Mukesh client mkdir "F:\Mukesh\Workspace\622-2 FileSharing\src\test"

java Mukesh client rmdir "F:\Mukesh\Workspace\622-2 FileSharing\src\test"

java Mukesh client dir "F:\Mukesh\Workspace\622-2 FileSharing\src"

java Mukesh client rm "F:\Mukesh\Workspace\622-2 FileSharing\src\test1.txt"

java Mukesh client shutdown
