package com.modernirc.server;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import com.modernirc.client.IntfSenderModel;
import com.modernirc.irc.IntfClientServerProtocol;

/**
 * <p align = "center">
 * 	<b><u>BroadcastThread</u></b>
 * </p>
 * <p align = "left">
 * 	<b><u>Attributs :</b></u><br />
 * 	- -<b>static HashMap<User, ServerToClientThread></b> clientTreadsMap<br />
 * </p>
 * <p align = "left">
 * 	<b><u>Méthode :</b></u><br />
 * 	- + static boolean addClient(User user, ServerToClientThread serverToClientThread)<br />
 *  - + static void sendMessage(User sender, String msg)<br />
 *  - + static void removeClient(User user)<br />
 *	- + static boolean accept(User user)<br />
 *  - + void setMsgToSend(String msgToSend)<br />
 *  - + static boolean accept(User user)<br />
 * </p>
 * @author SPAL/DDBE
 */
public class BroadcastThread extends Thread {
	
	public static HashMap<User, ServerToClientThread> clientTreadsMap=new HashMap<User, ServerToClientThread>();
	static{
		Collections.synchronizedMap(clientTreadsMap);
	}
	
	
	/**
	 * Méthode statique addClient
	 * Ajout d'un nouveau client lors d'une entrée d'un utilisateur
	 * @param user
	 * @param serverToClientThread
	 * @return
	 */
	public static boolean addClient(User user, ServerToClientThread serverToClientThread){
		boolean res=true;
		if(clientTreadsMap.containsKey(user)){
			res=false;
		}
		else{
			clientTreadsMap.put(user, serverToClientThread);
			
			loadUser(user);
			loadUser(serverToClientThread);
			
		}
		return res;
	}
	
	/**
	 * On informe l'utilisateur qui vient de se connecter de la liste des autres connectés.
	 * @param serverToClientThread
	 */
	private static void loadUser(ServerToClientThread serverToClientThread){
		//Remplissage de la list des utilisateurs des clients
		Collection<User> clientTreadConnect=clientTreadsMap.keySet();
		Iterator<User> iteratorConnect=clientTreadConnect.iterator();
		while (iteratorConnect.hasNext()) {
			
			User clientThread = (User) iteratorConnect.next();
			
			serverToClientThread.post(IntfClientServerProtocol.ADD + clientThread.getLogin());			
		}
	}
	
	private static void loadUser(User user){
		//Remplissage de la list des utilisateurs des clients
		Collection<ServerToClientThread> clientTreads=clientTreadsMap.values();
		Iterator<ServerToClientThread> receiverClientThreadIterator=clientTreads.iterator();
		while (receiverClientThreadIterator.hasNext()) {
			ServerToClientThread clientThread = (ServerToClientThread) receiverClientThreadIterator.next();
			clientThread.post(IntfClientServerProtocol.ADD + user.getLogin());			
		}
	}
	
	
	/**
	 * Méthode statique sendMessage
	 * envoi d'un message.
	 * @param sender
	 * @param msg
	 */
	public static void sendMessage(User sender, String msg){
		Collection<ServerToClientThread> clientTreads=clientTreadsMap.values();
		Iterator<ServerToClientThread> receiverClientThreadIterator=clientTreads.iterator();
		while (receiverClientThreadIterator.hasNext()) {
			ServerToClientThread clientThread = (ServerToClientThread) receiverClientThreadIterator.next();
			clientThread.post("#"+sender.getLogin()+"#"+msg);			
			System.out.println("sendMessage : "+"#"+sender.getLogin()+"#"+msg);
		}
	}
	
	/**
	 * Méthode removeClient
	 * Lorsqu'un client se déconnecte on supprime le client.
	 * @param user
	 */
	public static void removeClient(User user){
		clientTreadsMap.remove(user);
	}
	
	/**
	 * Méthode statique accept
	 * Si l'utilisateur existe on retourne true sinon false.
	 * @param user
	 * @return
	 */
	public static boolean accept(User user){
		boolean res=true;
		if(clientTreadsMap.containsKey(user)){
			res= false;
		}
		return res;
	}
	
	public static HashMap<User, ServerToClientThread> getUserList(){
		return BroadcastThread.clientTreadsMap;
	}
}
