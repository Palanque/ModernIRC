package com.modernirc.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import com.modernirc.irc.IntfClientServerProtocol;

/**
 * <p align = "center">
 * 	<b><u>ServerToClientThread</u></b>
 * </p>
 * <p align = "left">
 * 	<b><u>Attributs :</b></u><br />
 * 	- -<b>User</b>user<br />
 *	- -<b>Socket</b>socket<br />
 *	- -<b>DataInputStream</b>streamIn<br />
 *	- -<b>DataOutputStream</b>streamOut<br />
 *  - -<b>List<String></b>msgToPost<br />
 * </p>
 * <p align = "left">
 * 	<b><u>Méthode :</b></u><br />
 * 	- + synchronized void post()<br />
 *  - - synchronized void doPost()<br />
 *  - + void open() throws IOException<br />
 *  - + void close() throws IOException<br />
 *  - + void run()<br />
 * </p>
 * @author SPAL/DDBE
 */
public class ServerToClientThread extends Thread{
	private User user;
	private Socket socket = null;
	private DataInputStream streamIn = null;
	private DataOutputStream streamOut = null;
	List<String> msgToPost=new ArrayList<String>();
	
	/**
	 * Constructeur ServerToClientThread
	 * @param user
	 * @param socket
	 */
	public ServerToClientThread(User user, Socket socket) {
		super();
		this.user=user;
		this.socket = socket;
	}
	
	/**
	 * Méthode post <br />
	 * Post d'un message
	 * @param msg
	 */
	public synchronized void post(String msg){
		msgToPost.add(msg);
	}
	
	/**
	 * Méthode doPost()
	 */
	private synchronized void doPost(){
		try {
			for (String msg : msgToPost) {
					streamOut.writeUTF(msg);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			msgToPost.clear();
		}
	}
	
	/**
	 * Méthode open() <br />
	 * Méthode pour ouvrir les buffers pour l'insertion ou l'affichage des messages.
	 * @throws IOException
	 */
	public void open() throws IOException {
		streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		streamOut = new DataOutputStream(socket.getOutputStream());
	}
	
	/**
	 * Méthode close() <br />
	 * Méthode pour fermer le socket et les buffers d'entrée et sortie.
	 * @throws IOException
	 */
	public void close() throws IOException {
		if (socket != null)
			socket.close();
		if (streamIn != null)
			streamIn.close();
		if (streamOut != null)
			streamOut.close();
	}
	
	/**
	 * Méthode run<br />
	 * Lancement du thread.
	 */
	@Override
	public void run() {
		try {
			open();
			boolean done = false;
			while (!done) {
				try {
					if(streamIn.available()>0){
						String line = streamIn.readUTF();
						String[] userMsg=line.split(IntfClientServerProtocol.SEPARATOR);
						String login=userMsg[1];
						String msg=userMsg[2];
						done = msg.equals(".bye");
						if(!done){
							if(login.equals(user)){
								System.err.println("ServerToClientThread::run(), login!=user"+login);
							}
							BroadcastThread.sendMessage(user,msg);
						}
					}
					else{
						doPost();
					}
				} 
				catch (IOException ioe) {
					done = true;
				}
			}
			close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
