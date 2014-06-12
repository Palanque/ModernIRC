package com.modernirc.server;

/**
 * <p align = "center">
 * 	<b><u>User</u></b>
 * </p>
 * <p align = "left">
 * 	<b><u>Attributs :</b></u><br />
 * 	- -<b>String</b>login<br />
 *	- -<b>String</b>pwd<br />
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
public class User {

	private String login;
	private String pwd;
	
	/**
	 * Constructeur User
	 * @param login
	 * @param pwd
	 */
	public User(String login, String pwd) {
		super();
		this.login = login;
		this.pwd = pwd;
	}	
	
	/*
	 * Getters & Setters
	 */
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	
}
