package message;

import java.io.*;
import java.net.*;
import java.util.*;


public class serverHelper implements Runnable{
	Scanner sc = new Scanner(System.in);
	private String name = "";
	private final DataOutputStream out;
	private final DataInputStream in;
	Socket s;
	private boolean isLoggedin;
	public serverHelper(Socket s, String name, DataInputStream in, DataOutputStream out ) {
		this.name = name;
		this.in = in;
		this.out = out;
		this.s = s;
		this.isLoggedin = true;
	}
	@Override
	public void run() {
		String received="";
		while(true) {
			try {
				received = in.readUTF();
				if(received.equals("logout")) {
					this.s.close();
					this.isLoggedin = false;
					System.out.println(this.name+ " have logged out");
					break;
				}
				
				StringTokenizer t = new StringTokenizer(received,"#");
				String message  = t.nextToken();
				String receipent = t.nextToken();
				
				for(serverHelper helper : server.clients) {
					if(helper.name.equals(receipent) && helper.isLoggedin == true) {
						helper.out.writeUTF(this.name+" : "+message);
						break;
					}
				}
			}
			catch(IOException e){
				//System.out.println("unable to read/write message");
			}
		}
		try {
			this.in.close();
			this.out.close();
		}
		catch(IOException e) {
			System.out.println("port was not closed");
		}
	}
}
