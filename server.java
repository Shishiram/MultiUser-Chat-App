package message;
 
import java.io.*;
import java.net.*;
import java.util.*;

public class server{
	static Vector<serverHelper> clients = new Vector<>();
	static int clientCount = 0;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ServerSocket ss= new ServerSocket(5000); 
		Socket s = null; 
		while(true) {
			s = ss.accept();
			
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			DataInputStream in = new DataInputStream(s.getInputStream());
			
			serverHelper c = new serverHelper(s,"client"+clientCount,in,out);
			
			Thread t = new Thread(c);
			//System.out.println("client"+clientCount+"is online");
			clients.add(c);
			t.start();
			clientCount++;
		}
	}

}
