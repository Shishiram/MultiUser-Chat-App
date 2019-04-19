package message;

import java.io.*;
import java.util.*;
import java.net.*;

public class client {
	final static int ServerPort =  5000;
	public static void main(String[] args) throws UnknownHostException, IOException{
				Socket s = new Socket("localhost",ServerPort);
				DataOutputStream out = new DataOutputStream(s.getOutputStream());
				DataInputStream in = new DataInputStream(s.getInputStream());
				
				Thread sendMessage = new Thread(new Runnable() {
					@Override
					public void run() {
						while(true) {
							Scanner sc = new Scanner(System.in);
							String msg = sc.nextLine();
							try {
								out.writeUTF(msg);
							}
							catch(IOException e){
								System.out.println("Error while sending message");
							}
						}
					}
				});
				Thread readMessage = new Thread(new Runnable() {
					@Override
					public void run() {
						while(true) {
							try {
								String msg = in.readUTF();
								System.out.println(msg);
							}
							catch(IOException e){
								//System.out.println("Error while receiving message");
							}
						}
					}
				});
				sendMessage.start();
				readMessage.start();
	}
}
