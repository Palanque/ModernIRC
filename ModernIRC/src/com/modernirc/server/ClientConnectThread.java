package com.modernirc.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.DefaultListModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import com.modernirc.irc.IntfClientServerProtocol;

/**
 * <p align = "center">
 * 	<b><u>ClientConnectThread</u></b>
 * </p>
 * <p align = "left">
 * 	<b><u>Attributs :</b></u><br />
 * 	- -<b>StyledDocument</b>model<br />
 *	- -<b>DefaultListModel<String></b>clientListModel<br />
 *	- -<b>boolean</b>canStop<br />
 *	- -<b>ServerSocket</b>server<br />
 * </p>
 * @author SPAL/DDBE
 * <p align = "left">
 * 	<b><u>Méthode :</b></u><br />
 * 	- - void printMsg(String msg)<br />
 *  - + ClientConnectThread(int port, StyledDocument model, DefaultListModel<String> clientListModel)<br />
 *  - + void run()<br />
 *	- - void acceptClient(Socket socket) throws IOException, InterruptedException<br />
 *  - - boolean authentication(User newUser)<br />
 *  - + void open() throws IOException<br />
 *  - + void close() throws IOException<br />
 * </p>
 */
public class ClientConnectThread extends Thread implements IntfClientServerProtocol {
	private StyledDocument model=null;
	private DefaultListModel<String> clientListModel;		
	private boolean canStop=false;
	private ServerSocket server = null;

	/***
	 * Méthode printMsg
	 * @param msg
	 */
	private void printMsg(String msg){
		try {
			if(model!=null){
				model.insertString(model.getLength(), msg+"\n", null);
			}
			System.out.println(msg);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Méthode ClientConnectThread
	 * @param port
	 * @param model
	 * @param clientListModel
	 */
	public ClientConnectThread(int port, StyledDocument model, DefaultListModel<String> clientListModel) {
		try {
			this.model=model;
			this.clientListModel=clientListModel;
			printMsg("Binding to port " + port + ", please wait  ...");
			server = new ServerSocket(port);
			printMsg("Server started: " + server);
		} 
		catch (IOException ioe) {
			System.out.println(ioe);
		}
	}
	
	/**
	 * Méthode run <br />
	 * Lancement du thread d'attente de client.
	 */
	@Override
	public void run() {
		while(!canStop){
			printMsg("Waiting for a client ...");
			Socket socket;
			try {
				socket = server.accept();
				printMsg("Client accepted: " + socket);

				// Accept new client or close the socket
				acceptClient(socket);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Méthode acceptClient <br />
	 * Méthode pour accepter le client si user ok.
	 * @param socket
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void acceptClient(Socket socket) throws IOException, InterruptedException {
		// Read user login and pwd
		DataInputStream dis=new DataInputStream(socket.getInputStream());
		DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
		dos.writeUTF(LOGIN_PWD);
		while(dis.available()<=0){
			Thread.sleep(100);
		}
		String reponse=dis.readUTF();
		String[] userPwd=reponse.split(SEPARATOR);
		String login=userPwd[1];
		String pwd=userPwd[2];
		User newUser=new User(login, pwd);
		boolean isUserOK=authentication(newUser);
		if(isUserOK){
			ServerToClientThread client=new ServerToClientThread(newUser, socket);
			dos.writeUTF(OK);

			if(BroadcastThread.addClient(newUser, client)){
				client.start();			
				clientListModel.addElement(newUser.getLogin());
				dos.writeUTF(ADD+login);
			}
		}
		else{
			System.out.println("socket.close()");
			dos.writeUTF(KO);
			dos.close();
			socket.close();
		}
	}
	
	/**
	 * Méthode authentication
	 * @param newUser
	 * @return
	 */
	private boolean authentication(User newUser){
		return BroadcastThread.accept(newUser);
	}

	/**
	 * Méthode open
	 * @throws IOException
	 */
	public void open() throws IOException {

	}
	
	/**
	 * Méthode close
	 * @throws IOException
	 */
	public void close() throws IOException {
		System.err.println("server:close()");
		if (server != null)
			server.close();
	}
}
