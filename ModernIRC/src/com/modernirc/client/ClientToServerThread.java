package com.modernirc.client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.DefaultListModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;

import com.modernirc.irc.IntfClientServerProtocol;
import com.modernirc.ui.SimpleChatClientApp;

/**
 * <p align = "center">
 * 	<b><u>ClientToServerThread</u></b>
 * </p>
 * <p align = "left">
 * 	<b><u>Attributs :</b></u><br />
 * 	- -<b>Socket</b> socket = null<br />
 *	- -<b>DataOutputStream</b> streamOut = null<br />
 *	- -<b>DataInputStream</b> streamIn = null<br />
 *	- -<b>BufferedReader</b> console = null<br />
 *	- -<b>String</b> login<br />
 *	- -<b>String</b> pwd<br />
 *	- -<b>DefaultListModel<String></b> clientListModel<br />
 *	- -<b>StyledDocument</b> documentModel<br />
 *  - -<b>String</b> msgToSend=null<br />
 *  - -<b>boolean</b> done<br />
 * </p>
 * @author SPAL/DDBE
 * <p align = "left">
 * 	<b><u>Méthode :</b></u><br />
 * 	- + void open() throws IOException<br />
 *  - + void close() throws IOException<br />
 *  - + receiveMessage(String user, String line)<br />
 *  - + void receiveMessage(String user, String line, Style styleBI,
 *			Style styleGP)<br />
 *	- void readMsg() throws IOException<br />
 *  - + void setMsgToSend(String msgToSend)<br />
 *  - - boolean sendMsg() throws IOException<br />
 *  - + void quitServer() throws IOException<br />
 *  - + void run()<br />
 *  - - boolean authentification()<br />
 * </p>
 */
public class ClientToServerThread extends Thread implements IntfSenderModel{
	private Socket socket = null;
	private DataOutputStream streamOut = null;
	private DataInputStream streamIn = null;
	private BufferedReader console = null;
	private String login;
	private String pwd;
	private DefaultListModel<String> clientListModel;
	private StyledDocument documentModel;
	private String msgToSend=null;
	private boolean done;
	
	/**
	 * Constructeur ClientToServerThread
	 * @param documentModel
	 * @param clientListModel
	 * @param socket
	 * @param login
	 * @param pwd
	 */
	public ClientToServerThread(StyledDocument documentModel, DefaultListModel<String> clientListModel, Socket socket, String login, String pwd) {
		super();
		this.documentModel=documentModel;
		this.clientListModel=clientListModel;
		this.socket = socket;
		this.login=login;
		this.pwd=pwd;
	}

	/**
	 * Méthode open<br />
	 * Déclaration des buffers pour afficher ou écrire des messages.
	 * @throws IOException
	 */
	public void open() throws IOException {
		setConsole(new BufferedReader(new InputStreamReader(System.in)));
		streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		streamOut = new DataOutputStream(socket.getOutputStream());
	}

	/**
	 * Méthode close<br />
	 * Fermeture du socket et des buffers.
	 * @throws IOException
	 */
	public void close() throws IOException {
		if (socket != null){
			socket.close();
		}
		if (streamIn != null){
			streamIn.close();
		}
		if (streamOut != null){
			streamOut.close();
		}
	}

	/**
	 * Méthode receiveMessage
	 * @param user
	 * @param line
	 */
	public void receiveMessage(String user, String line){
		Style styleBI = ((StyledDocument)documentModel).getStyle(SimpleChatClientApp.BOLD_ITALIC);
		Style styleGP = ((StyledDocument)documentModel).getStyle(SimpleChatClientApp.GRAY_PLAIN);
		receiveMessage(user, line, styleBI, styleGP);
	}
	
	/**
	 * Méthode receiveMessage<br />
	 * Réception d'un messgae envoyé au serveur. 
	 * @param user
	 * @param line
	 * @param styleBI
	 * @param styleGP
	 */
	public void receiveMessage(String user, String line, Style styleBI,
			Style styleGP) {
		try {        	
			documentModel.insertString(documentModel.getLength(), user+" : ", styleBI);
			documentModel.insertString(documentModel.getLength(), line+"\n", styleGP);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}				        	
	}
	
	/**
	 * Méthode readMsg<br />
	 * Lecture d'un message du serveur.
	 * @throws IOException
	 */
	void readMsg() throws IOException{
		String line = streamIn.readUTF();
		System.out.println(line);

		if(line.startsWith(IntfClientServerProtocol.ADD)){
			String newUser=line.substring(IntfClientServerProtocol.ADD.length());
			if(!clientListModel.contains(newUser)){
				clientListModel.addElement(newUser);
				receiveMessage(newUser, " entre dans le salon...");
			}
		}
		else if(line.startsWith(IntfClientServerProtocol.DEL)){
			String delUser=line.substring(IntfClientServerProtocol.DEL.length());
			if(clientListModel.contains(delUser)){
				clientListModel.removeElement(delUser);
				receiveMessage(delUser, " quite le salon !");
			}
		}
		else{
			String[] userMsg=line.split(IntfClientServerProtocol.SEPARATOR);
			String user=userMsg[1];
			receiveMessage(user, userMsg[2]);
		}
	}
	
	/**
	 * Méthode sendMsg au serveur<br />
	 * Envoi d'un message
	 * @return
	 * @throws IOException
	 */
	private boolean sendMsg() throws IOException{
		boolean res=false;
		if(msgToSend!=null){
			streamOut.writeUTF("#"+login+"#"+msgToSend);
			msgToSend=null;
			streamOut.flush();
			res=true;
		}
		return res;
	}
	
	/**
	 * Méthode quitServer
	 *  @throws IOException
	 */
	public void quitServer() throws IOException{
		streamOut.writeUTF(IntfClientServerProtocol.DEL+login);
		streamOut.flush();
		done=true;
	}
	
	/**
	 * Méthode run<br />
	 * Démarrage du thread
	 */
	@Override
	public void run() {
		try {
			open();
			done = !authentification();
			while (!done) {
				try {
					if(streamIn.available()>0){
						readMsg();
					}

					if(!sendMsg()){
						Thread.sleep(100);
					}
				} 
				catch (IOException | InterruptedException ioe) {
					ioe.printStackTrace();
					done = true;
				}
			}
			close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Méthode authentification<br />
	 * Authentification de l'utilisateur.
	 * @return
	 */
	private boolean authentification() {
		boolean res=false;
		String loginPwdQ;
		try {
			while(streamIn.available()<=0){
				Thread.sleep(100);
			}
			loginPwdQ = streamIn.readUTF();
			if(loginPwdQ.equals(IntfClientServerProtocol.LOGIN_PWD)){
				streamOut.writeUTF(IntfClientServerProtocol.SEPARATOR+this.login+IntfClientServerProtocol.SEPARATOR+this.pwd);
			}
			while(streamIn.available()<=0){
				Thread.sleep(100);
			}
			String acq=streamIn.readUTF();
			if(acq.equals(IntfClientServerProtocol.OK)){
				res=true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res=false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;		
	}
	
	/*
	 * Setters & Getters
	 * (non-Javadoc)
	 * @see com.modernirc.client.IfSenderModel#setMsgToSend(java.lang.String)
	 */
	@Override
	public void setMsgToSend(String msgToSend) {
		this.msgToSend = msgToSend;
	}

	public BufferedReader getConsole() {
		return console;
	}

	public void setConsole(BufferedReader console) {
		this.console = console;
	}

}

