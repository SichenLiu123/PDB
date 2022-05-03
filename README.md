## Run the application
The back end of the project is implemented by Springboot, the server must run in the Linux system. The linux system needs to install JDK 8+, and has an idle port 8080.
1.	Use the mvn package command to package the project into jar format. For example, pdb-0.0.1-SNAPSHOT.jar.
2.	Put the pdb-0.0.1-SNAPSHOT.jar and the dnapro file in the same directory.
3.	Start the server. Run command：java -jar pdb-0.0.1-SNAPSHOT.jar
4.	Visit the address http://localhost:8080/ in the browser to enter the project home page.

**There is already a build completed pdb-0.0.1-SNAPSHOT.jar under the root directory of repository that can be used directly.**

When the project starts, a folder called PDB is automatically generated in the jar file’s working directory to store all downloaded PDB files。
